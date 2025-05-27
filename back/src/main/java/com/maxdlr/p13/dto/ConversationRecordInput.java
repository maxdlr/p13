package com.maxdlr.p13.dto;

public record ConversationRecordInput(
    Integer userId) {
  public Integer getUserId() {
    return userId;
  }
}
