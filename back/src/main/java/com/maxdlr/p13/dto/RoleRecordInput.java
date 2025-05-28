package com.maxdlr.p13.dto;

public record RoleRecordInput(
    Integer id,
    String name) {
  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
