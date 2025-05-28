package com.maxdlr.p13.dto;

public record MessageRecordInfo(
    Integer id,
    String content,
    UserRecordInfo user,
    ConversationRecordInfo conversation,
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

  public ConversationRecordInfo getConversation() {
    return conversation;
  }

  public String getStatus() {
    return status;
  }
}
