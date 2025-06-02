package com.maxdlr.p13.dto;

public record MessageRecordInput(
    String content,
    Integer userId,
    Integer conversationId) {
}
