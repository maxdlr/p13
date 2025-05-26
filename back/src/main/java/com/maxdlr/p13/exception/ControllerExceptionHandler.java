package com.maxdlr.p13.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.maxdlr.p13.requests.response.ErrorMessageResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(MessageUserNotFoundException.class)
  public ResponseEntity<Object> handleApiResourceNotFoundException(
      MessageUserNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessageResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler(MessageNotFoundException.class)
  public ResponseEntity<Object> handleApiResourceNotFoundException(
      MessageNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessageResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
  }

  @ExceptionHandler(ConversationNotFoundException.class)
  public ResponseEntity<Object> handleApiResourceNotFoundException(
      ConversationNotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessageResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
  }
}
