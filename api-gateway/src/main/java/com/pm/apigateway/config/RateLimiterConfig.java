package com.pm.apigateway.config;

import java.net.InetSocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

  private static final String UNKNOWN_CLIENT = "unknown-client";

  @Bean
  public KeyResolver clientIpKeyResolver() {
    return exchange -> Mono.just(resolveClientIp(exchange.getRequest()
        .getHeaders()
        .getFirst("X-Forwarded-For"), exchange.getRequest().getRemoteAddress()));
  }

  @Bean
  @Primary
  public RedisRateLimiter businessRouteRateLimiter(
      @Value("${rate-limit.default.replenish-rate}") int replenishRate,
      @Value("${rate-limit.default.burst-capacity}") int burstCapacity,
      @Value("${rate-limit.default.requested-tokens}") int requestedTokens) {
    return new RedisRateLimiter(replenishRate, burstCapacity, requestedTokens);
  }

  @Bean
  public RedisRateLimiter authRouteRateLimiter(
      @Value("${rate-limit.auth.replenish-rate}") int replenishRate,
      @Value("${rate-limit.auth.burst-capacity}") int burstCapacity,
      @Value("${rate-limit.auth.requested-tokens}") int requestedTokens) {
    return new RedisRateLimiter(replenishRate, burstCapacity, requestedTokens);
  }

  private String resolveClientIp(String forwardedFor,
      InetSocketAddress remoteAddress) {
    if (forwardedFor != null && !forwardedFor.isBlank()) {
      return forwardedFor.split(",")[0].trim();
    }

    if (remoteAddress != null && remoteAddress.getAddress() != null) {
      return remoteAddress.getAddress().getHostAddress();
    }

    return UNKNOWN_CLIENT;
  }
}
