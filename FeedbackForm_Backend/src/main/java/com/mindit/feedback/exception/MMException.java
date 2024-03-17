package com.mindit.feedback.exception;

public class MMException extends RuntimeException {
  private final transient ErrorCode errorCode;

  public MMException(ErrorCode errorCode) {
    super(errorCode.getErrorMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return this.errorCode;
  }
}
