package com.pm.customerservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @NotNull
  private String fullName;

  @NotNull
  @Email
  @Column(unique = true)
  private String email;

  @NotNull
  private String shippingAddress;

  @NotNull
  private LocalDate memberSince;

  @NotNull
  private LocalDate preferredDropDate;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public @NotNull String getFullName() {
    return fullName;
  }

  public void setFullName(@NotNull String fullName) {
    this.fullName = fullName;
  }

  public @NotNull @Email String getEmail() {
    return email;
  }

  public void setEmail(@NotNull @Email String email) {
    this.email = email;
  }

  public @NotNull String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(@NotNull String shippingAddress) { this.shippingAddress = shippingAddress; }

  public @NotNull LocalDate getMemberSince() {
    return memberSince;
  }

  public void setMemberSince(@NotNull LocalDate memberSince) {
    this.memberSince = memberSince;
  }

  public @NotNull LocalDate getPreferredDropDate() {
    return preferredDropDate;
  }

  public void setPreferredDropDate(@NotNull LocalDate preferredDropDate) {
    this.preferredDropDate = preferredDropDate;
  }

}
