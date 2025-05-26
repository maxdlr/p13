package com.maxdlr.p13.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.maxdlr.p13.dto.ErrorRecordInfo;

@ControllerAdvice
public class ControllerExceptionHandler {

  private ErrorRecordInfo sendErrorInfo(Exception exception, HttpStatus status) {
    return new ErrorRecordInfo(exception.getMessage(), status);
  }

  @ExceptionHandler(MessageUserNotFoundException.class)
  public ErrorRecordInfo handleApiResourceNotFoundException(
      MessageUserNotFoundException exception) {
    return this.sendErrorInfo(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConversationUserNotFoundException.class)
  public ErrorRecordInfo handleApiResourceNotFoundException(
      ConversationUserNotFoundException exception) {
    return this.sendErrorInfo(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MessageNotFoundException.class)
  public ErrorRecordInfo handleApiResourceNotFoundException(
      MessageNotFoundException exception) {
    return this.sendErrorInfo(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConversationNotFoundException.class)
  public ErrorRecordInfo handleApiResourceNotFoundException(
      ConversationNotFoundException exception) {
    return this.sendErrorInfo(exception, HttpStatus.NOT_FOUND);
  }
}
