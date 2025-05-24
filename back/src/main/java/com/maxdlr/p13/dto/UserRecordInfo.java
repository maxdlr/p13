package com.maxdlr.p13.dto;

import java.util.List;

public record UserRecordInfo(
    Integer id,
    String email,
    String firstname,
    String lastname,
    String phoneNumber,
    Boolean isActive,
    List<RoleRecordInfo> roles) {

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public List<RoleRecordInfo> getRoles() {
    return roles;
  }
}
