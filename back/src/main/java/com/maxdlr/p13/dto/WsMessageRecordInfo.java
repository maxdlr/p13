package com.maxdlr.p13.dto;

public record WsMessageRecordInfo(
    Integer id,
    String content,
    UserRecordInfo user,
    WsConversationRecordInfo conversation,
    String status) {

  public Integer getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public UserRecordInfo getUser() {
    return user;
  }

  public WsConversationRecordInfo getConversation() {
    return conversation;
  }

  public String getStatus() {
    return status;
  }
}
