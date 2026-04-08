package com.pm.apigateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RateLimitExceededResponseFilter implements GlobalFilter, Ordered {

  private final ObjectMapper objectMapper;
  private final String message;

  public RateLimitExceededResponseFilter(ObjectMapper objectMapper,
      @Value("${rate-limit.error-message}") String message) {
    this.objectMapper = objectMapper;
    this.message = message;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpResponseDecorator decoratedResponse =
        new ServerHttpResponseDecorator(exchange.getResponse()) {
          @Override
          public Mono<Void> setComplete() {
            if (!HttpStatus.TOO_MANY_REQUESTS.equals(getStatusCode())
                || isCommitted()) {
              return super.setComplete();
            }

            byte[] payload = buildPayload(exchange);
            getHeaders().setContentType(MediaType.APPLICATION_JSON);
            getHeaders().set(HttpHeaders.CACHE_CONTROL, "no-store");
            getHeaders().set(HttpHeaders.RETRY_AFTER, "1");
            getHeaders().setContentLength(payload.length);

            DataBuffer buffer = bufferFactory().wrap(payload);
            return writeWith(Mono.just(buffer));
          }
        };

    return chain.filter(exchange.mutate().response(decoratedResponse).build());
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  private byte[] buildPayload(ServerWebExchange exchange) {
    Map<String, Object> body = Map.of(
        "status", HttpStatus.TOO_MANY_REQUESTS.value(),
        "error", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase(),
        "message", message,
        "path", exchange.getRequest().getPath().value()
    );

    try {
      return objectMapper.writeValueAsBytes(body);
    } catch (JsonProcessingException ex) {
      return ("{\"status\":429,\"error\":\"Too Many Requests\",\"message\":\""
          + message + "\",\"path\":\"" + exchange.getRequest().getPath().value()
          + "\"}").getBytes(StandardCharsets.UTF_8);
    }
  }
}
