package com.pm.customerservice.repository;

import com.pm.customerservice.model.Customer;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
  boolean existsByEmail(String email);
  boolean existsByEmailAndIdNot(String email, UUID id);
  List<Customer> findByFullNameContainsIgnoreCaseOrEmailContainingIgnoreCaseOrShippingAddressContainingIgnoreCase(
      String fullName, String email, String shippingAddress);
}
