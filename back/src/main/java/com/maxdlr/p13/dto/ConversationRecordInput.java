package com.maxdlr.p13.dto;

public record ConversationRecordInput(
    Integer id,
    Integer userId) {

  public Integer getId() {
    return id;
  }

  public Integer getUserId() {
    return userId;
  }
}
