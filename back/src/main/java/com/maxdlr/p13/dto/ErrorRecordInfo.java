package com.maxdlr.p13.dto;

import org.springframework.http.HttpStatus;

public record ErrorRecordInfo(
    String message,
    HttpStatus status) {
  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
