package com.pm.customerservice.service;

import com.pm.customerservice.dto.CustomerRequestDTO;
import com.pm.customerservice.dto.CustomerResponseDTO;
import com.pm.customerservice.exception.CustomerNotFoundException;
import com.pm.customerservice.exception.EmailAlreadyExistsException;
import com.pm.customerservice.grpc.PaymentProfileServiceGrpcClient;
import com.pm.customerservice.kafka.KafkaProducer;
import com.pm.customerservice.mapper.CustomerMapper;
import com.pm.customerservice.model.Customer;
import com.pm.customerservice.repository.CustomerRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final PaymentProfileServiceGrpcClient paymentProfileServiceGrpcClient;
  private final KafkaProducer kafkaProducer;

  public CustomerService(CustomerRepository customerRepository,
      PaymentProfileServiceGrpcClient paymentProfileServiceGrpcClient,
      KafkaProducer kafkaProducer) {
    this.customerRepository = customerRepository;
    this.paymentProfileServiceGrpcClient = paymentProfileServiceGrpcClient;
    this.kafkaProducer = kafkaProducer;
  }

  public List<CustomerResponseDTO> getCustomers() {
    List<Customer> customers = customerRepository.findAll();

    return customers.stream().map(CustomerMapper::toDTO).toList();
  }

  public List<CustomerResponseDTO> searchCustomers(String query) {
    if (query == null || query.isBlank()) {
      return getCustomers();
    }

    String normalizedQuery = query.trim();
    List<Customer> customers =
        customerRepository.findByFullNameContainsIgnoreCaseOrEmailContainingIgnoreCaseOrShippingAddressContainingIgnoreCase(
            normalizedQuery, normalizedQuery, normalizedQuery);

    return customers.stream().map(CustomerMapper::toDTO).toList();
  }

  public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
    if (customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
      throw new EmailAlreadyExistsException(
          "A customer with this email " + "already exists"
              + customerRequestDTO.getEmail());
    }

    Customer newCustomer = customerRepository.save(
        CustomerMapper.toModel(customerRequestDTO));

    paymentProfileServiceGrpcClient.createPaymentProfile(newCustomer.getId().toString(),
        newCustomer.getFullName(), newCustomer.getEmail());

    kafkaProducer.sendEvent(newCustomer);

    return CustomerMapper.toDTO(newCustomer);
  }

  public CustomerResponseDTO updateCustomer(UUID id,
      CustomerRequestDTO customerRequestDTO) {

    Customer customer = customerRepository.findById(id).orElseThrow(
        () -> new CustomerNotFoundException("Customer not found with ID: " + id));

    if (customerRepository.existsByEmailAndIdNot(customerRequestDTO.getEmail(),
        id)) {
      throw new EmailAlreadyExistsException(
          "A customer with this email " + "already exists"
              + customerRequestDTO.getEmail());
    }

    customer.setFullName(customerRequestDTO.getFullName());
    customer.setShippingAddress(customerRequestDTO.getShippingAddress());
    customer.setEmail(customerRequestDTO.getEmail());
    customer.setPreferredDropDate(LocalDate.parse(customerRequestDTO.getPreferredDropDate()));

    Customer updatedCustomer = customerRepository.save(customer);
    return CustomerMapper.toDTO(updatedCustomer);
  }

  public void deleteCustomer(UUID id) {
    customerRepository.deleteById(id);
  }
}
