package com.maxdlr.p13.dto;

public record ConversationRecordInfo(
    Integer id,
    String wsTopic,
    UserRecordInfo user,
    String status) {
}
