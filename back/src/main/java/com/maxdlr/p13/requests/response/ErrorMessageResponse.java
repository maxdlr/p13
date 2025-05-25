package com.maxdlr.p13.requests.response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorMessageResponse {
  private String message;
  private HttpStatus httpStatus;

  public ErrorMessageResponse(String message, HttpStatus httpStatus) {
    this.message = message;
    this.httpStatus = httpStatus;
  }
}
