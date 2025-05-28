package com.maxdlr.p13.dto;

public record ConversationRecordInfo(
    Integer id,
    String wsTopic,
    UserRecordInfo user,
    String status) {
  public Integer getId() {
    return id;
  }

  public String getWsTopic() {
    return wsTopic;
  }

  public UserRecordInfo getUser() {
    return user;
  }

  public String getStatus() {
    return status;
  }
}
