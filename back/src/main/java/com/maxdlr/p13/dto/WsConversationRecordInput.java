package com.maxdlr.p13.dto;

public record WsConversationRecordInput(
    Integer id,
    Integer userId) {

  public Integer getId() {
    return id;
  }

  public Integer getUser() {
    return userId;
  }
}
