package com.maxdlr.p13.dto;

public record MessageRecordInput(
    String content,
    Integer userId,
    Integer conversationId) {

  public String getContent() {
    return content;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getConversationId() {
    return conversationId;
  }
}
