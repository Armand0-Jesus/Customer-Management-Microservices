package com.pm.customerservice.grpc;

import billing.PaymentProfileRequest;
import billing.PaymentProfileResponse;
import billing.PaymentProfileServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentProfileServiceGrpcClient {

  private static final Logger log = LoggerFactory.getLogger(
      PaymentProfileServiceGrpcClient.class);
  private final PaymentProfileServiceGrpc.PaymentProfileServiceBlockingStub blockingStub;

  public PaymentProfileServiceGrpcClient(
      @Value("${billing.service.address:localhost}") String serverAddress,
      @Value("${billing.service.grpc.port:9001}") int serverPort) {

    log.info("Connecting to Payment Profile Service GRPC service at {}:{}",
        serverAddress, serverPort);

    ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
        serverPort).usePlaintext().build();

    blockingStub = PaymentProfileServiceGrpc.newBlockingStub(channel);
  }

  public PaymentProfileResponse createPaymentProfile(String customerId,
      String fullName, String email) {

    PaymentProfileRequest request = PaymentProfileRequest.newBuilder()
        .setCustomerId(customerId)
        .setFullName(fullName)
        .setEmail(email)
        .build();

    PaymentProfileResponse response = blockingStub.createPaymentProfile(request);
    log.info("Received response from payment profile service via GRPC: {}",
        response);
    return response;
  }
}
