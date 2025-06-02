package com.maxdlr.p13.dto;

public record UserRecordInfo(
    Integer id,
    String email,
    String firstname,
    String lastname,
    String phoneNumber,
    Boolean isActive,
    RoleRecordInfo role) {
}
