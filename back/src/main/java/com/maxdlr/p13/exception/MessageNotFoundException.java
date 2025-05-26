package com.maxdlr.p13.exception;

public class MessageNotFoundException extends RuntimeException {
  public MessageNotFoundException(String message) {
    super(message);
  }
}
