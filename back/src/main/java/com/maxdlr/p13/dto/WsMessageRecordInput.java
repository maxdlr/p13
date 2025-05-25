package com.maxdlr.p13.dto;

public record WsMessageRecordInput(
    Integer id,
    String content,
    Integer userId,
    Integer conversationId,
    String status) {

  public Integer getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public Integer getUserId() {
    return userId;
  }

  public Integer getConversation() {
    return conversationId;
  }

  public String getStatus() {
    return status;
  }
}
