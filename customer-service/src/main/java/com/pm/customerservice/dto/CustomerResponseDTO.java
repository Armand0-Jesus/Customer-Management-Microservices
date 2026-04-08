package com.pm.customerservice.dto;

import java.io.Serial;
import java.io.Serializable;

public class CustomerResponseDTO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private String id;
  private String fullName;
  private String email;
  private String shippingAddress;
  private String memberSince;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public String getmemberSince() {
    return memberSince;
  }

  public void setMemberSince(String memberSince ) {
    this.memberSince = memberSince;
  }


}
