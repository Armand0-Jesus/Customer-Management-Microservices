package com.pm.paymentprofileservice.grpc;

import billing.PaymentProfileRequest;
import billing.PaymentProfileResponse;
import billing.PaymentProfileServiceGrpc.PaymentProfileServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class PaymentProfileGrpcService extends PaymentProfileServiceImplBase {

  private static final Logger log = LoggerFactory.getLogger(
      PaymentProfileGrpcService.class);

  @Override
  public void createPaymentProfile(PaymentProfileRequest paymentProfileRequest,
      StreamObserver<PaymentProfileResponse> responseObserver) {

    log.info("createPaymentProfile request received {}",
        paymentProfileRequest.toString());

    PaymentProfileResponse response = PaymentProfileResponse.newBuilder()
        .setPaymentProfileId("12345")
        .setStatus("ACTIVE")
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
