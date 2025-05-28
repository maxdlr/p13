package com.maxdlr.p13.exception;

public class ConversationUserNotFoundException extends RuntimeException {
  public ConversationUserNotFoundException(String message) {
    super(message);
  }
}
