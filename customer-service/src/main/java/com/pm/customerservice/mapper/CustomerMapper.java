package com.pm.customerservice.mapper;

import com.pm.customerservice.dto.CustomerRequestDTO;
import com.pm.customerservice.dto.CustomerResponseDTO;
import com.pm.customerservice.model.Customer;
import java.time.LocalDate;

public class CustomerMapper {
  public static CustomerResponseDTO toDTO(Customer customer) {
    CustomerResponseDTO customerDTO = new CustomerResponseDTO();
    customerDTO.setId(customer.getId().toString());
    customerDTO.setFullName(customer.getFullName());
    customerDTO.setShippingAddress(customer.getShippingAddress());
    customerDTO.setEmail(customer.getEmail());
    customerDTO.setMemberSince(customer.getMemberSince().toString());

    return customerDTO;
  }

  public static Customer toModel(CustomerRequestDTO customerRequestDTO) {
    Customer customer = new Customer();
    customer.setFullName(customerRequestDTO.getFullName());
    customer.setShippingAddress(customerRequestDTO.getShippingAddress());
    customer.setEmail(customerRequestDTO.getEmail());
    customer.setMemberSince(LocalDate.parse(customerRequestDTO.getMemberSince()));
    customer.setPreferredDropDate(LocalDate.parse(customerRequestDTO.getPreferredDropDate()));
    return customer;
  }
}
