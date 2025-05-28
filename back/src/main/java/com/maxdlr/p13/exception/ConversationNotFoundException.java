package com.maxdlr.p13.exception;

public class ConversationNotFoundException extends RuntimeException {
  public ConversationNotFoundException(String message) {
    super(message);
  }
}
