package com.pm.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import customer.events.CustomerEvent;

@Service
public class KafkaConsumer {

  private static final Logger log = LoggerFactory.getLogger(
      KafkaConsumer.class);

  @KafkaListener(topics="customer", groupId = "analytics-service")
  public void consumeEvent(byte[] event) {
    try {
      CustomerEvent customerEvent = CustomerEvent.parseFrom(event);

      log.info("Received Customer Event: [CustomerId={},CustomerFullName={},CustomerEmail={}]",
            customerEvent.getCustomerId(),
            customerEvent.getFullName(),
            customerEvent.getEmail());
    } catch (InvalidProtocolBufferException e) {
      log.error("Error deserializing event {}", e.getMessage());
    }
  }
}
