package com.maxdlr.p13.dto;

public record WsConversationRecordInfo(
    Integer id,
    String wstopic,
    UserRecordInfo user,
    String status) {
  public Integer getId() {
    return id;
  }

  public String getWsTopic() {
    return wstopic;
  }

  public UserRecordInfo getUser() {
    return user;
  }

  public String getStatus() {
    return status;
  }
}
