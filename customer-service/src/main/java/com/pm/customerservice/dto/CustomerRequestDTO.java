package com.pm.customerservice.dto;

import com.pm.customerservice.dto.validators.CreateCustomerValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRequestDTO {

  @NotBlank(message = "Name is required")
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  private String fullName;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Shipping address is required")
  private String shippingAddress;

  @NotBlank(message = "Preferred drop date is required")
  private String preferredDropDate;

  @NotBlank(groups = CreateCustomerValidationGroup.class, message =
      "Member since is required")
  private String memberSince;

  public @NotBlank(message = "Name is required") @Size(max = 200, message = "Name cannot exceed 200 characters") String getFullName() {
    return fullName;
  }

  public void setFullName(
      @NotBlank(message = "Name is required") @Size(max = 200, message = "Name cannot exceed 200 characters") String fullName) {
    this.fullName = fullName;
  }

  public @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String getEmail() {
    return email;
  }

  public void setEmail(
      @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email) {
    this.email = email;
  }

  public @NotBlank(message = "A shipping address is required") String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(
      @NotBlank(message = "A shipping address is required") String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public @NotBlank(message = "A preferred drop date is required") String getPreferredDropDate() {
    return preferredDropDate;
  }

  public void setPreferredDropDate(
      @NotBlank(message = "A preferred drop date is required") String preferredDropDate) {
    this.preferredDropDate = preferredDropDate;
  }

  public String getMemberSince() {
    return memberSince;
  }

  public void setMemberSince(String memberSince) {
    this.memberSince = memberSince;
  }

}
