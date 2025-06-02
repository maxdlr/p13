package com.maxdlr.p13.dto;

public record MessageRecordInfo(
    Integer id,
    String content,
    UserRecordInfo user,
    ConversationRecordInfo conversation,
    String status) {
}
