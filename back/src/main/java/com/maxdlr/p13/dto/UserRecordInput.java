package com.maxdlr.p13.dto;

public record UserRecordInput(
    Integer id,
    String email,
    String password,
    String firstname,
    String lastname,
    String phoneNumber,
    Boolean isActive,
    Integer roleId) {
}
