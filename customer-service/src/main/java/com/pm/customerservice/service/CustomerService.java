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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

  @Cacheable(cacheNames = "customers", key = "'all'")
  public List<CustomerResponseDTO> getCustomers() {
    List<Customer> customers = customerRepository.findAll();
    return mapCustomers(customers);
  }

  @Cacheable(cacheNames = "customerSearch",
      key = "#query == null ? '' : #query.trim().toLowerCase()",
      condition = "#query != null && !#query.isBlank()")
  public List<CustomerResponseDTO> searchCustomers(String query) {
    if (query == null || query.isBlank()) {
      return getCustomers();
    }

    String normalizedQuery = query.trim();
    List<Customer> customers =
        customerRepository.findByFullNameContainsIgnoreCaseOrEmailContainingIgnoreCaseOrShippingAddressContainingIgnoreCase(
            normalizedQuery, normalizedQuery, normalizedQuery);

    return mapCustomers(customers);
  }

  @Caching(evict = {
      @CacheEvict(cacheNames = "customers", allEntries = true),
      @CacheEvict(cacheNames = "customerSearch", allEntries = true)
  })
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

  @Caching(evict = {
      @CacheEvict(cacheNames = "customers", allEntries = true),
      @CacheEvict(cacheNames = "customerSearch", allEntries = true)
  })
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

  @Caching(evict = {
      @CacheEvict(cacheNames = "customers", allEntries = true),
      @CacheEvict(cacheNames = "customerSearch", allEntries = true)
  })
  public void deleteCustomer(UUID id) {
    customerRepository.deleteById(id);
  }

  private List<CustomerResponseDTO> mapCustomers(List<Customer> customers) {
    return customers.stream()
        .map(CustomerMapper::toDTO)
        .collect(Collectors.toCollection(ArrayList::new));
  }
}
