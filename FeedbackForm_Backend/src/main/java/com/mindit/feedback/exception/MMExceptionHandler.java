package com.mindit.feedback.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MMExceptionHandler {
  @ExceptionHandler(MMException.class)
  public ResponseEntity<ErrorResponse> handlingError(MMException ex) {
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return new ResponseEntity<>(errorResponse, ex.getErrorCode().getHttpStatus());
  }
}
