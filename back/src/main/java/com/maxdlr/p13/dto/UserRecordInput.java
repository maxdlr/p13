package com.maxdlr.p13.dto;

import java.util.List;

public record UserRecordInput(
    Integer id,
    String email,
    String password,
    String firstname,
    String lastname,
    String phoneNumber,
    Boolean isActive,
    List<Integer> roleIds) {

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
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

  public List<Integer> getRoleIds() {
    return roleIds;
  }
}
