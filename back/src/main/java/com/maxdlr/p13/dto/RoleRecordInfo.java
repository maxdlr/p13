package com.maxdlr.p13.dto;

public record RoleRecordInfo(
    Integer id,
    String name) {
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
