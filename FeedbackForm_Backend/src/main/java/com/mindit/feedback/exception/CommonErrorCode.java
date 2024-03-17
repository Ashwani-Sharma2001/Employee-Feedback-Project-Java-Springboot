package com.mindit.feedback.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
  INTERNAL_SERVER_ERROR(
      100001, "INTERNAL_SERVER_ERROR", "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR),
  EMAIL_ALREADY_EXISTS(
      100002, "EMAIL_ALREADY_EXISTS", "Email id already exists", HttpStatus.CONFLICT),
  EMAIL_NOT_EXISTS(100003, "EMAIL_NOT_EXISTS", "email id not exists", HttpStatus.NOT_FOUND),
  DATA_NOT_SAVED(100004, "DATA_NOT_SAVED", "data not saved", HttpStatus.INTERNAL_SERVER_ERROR),
  DATA_NOT_FOUND(100005, "DATA_NOT_FOUND", "data not found", HttpStatus.NOT_FOUND),
  USERNAME_NOT_VALID(100006, "USERNAME_NOT_VALID", "username not valid", HttpStatus.BAD_REQUEST),

  BAD_REQUEST(100007, "BAD_REQUEST", "Wrong Data", HttpStatus.BAD_REQUEST),
  EMAIL_NOT_VALID(100008, "EMAIL_NOT_VALID", "Wrong Data", HttpStatus.BAD_REQUEST),
  FORBIDDEN_API(1000016, "FORBIDDEN_API", "Forbidden", HttpStatus.FORBIDDEN),

  ACCESS_TOKEN_NOT_FOUND(
      1000018, "ACCESS_TOKEN_NOT_FOUND", "access token not found", HttpStatus.NOT_FOUND),
  PASSWORD_NOT_VALID(1000019, "PASSWORD_INCORRECT", "password incorrect", HttpStatus.BAD_REQUEST),
  BLOCKED_STATUS_CHECK(
      1000021,
      "USER IS BLOCKED",
      "User is blocked, Please contact with System Administrator",
      HttpStatus.BAD_REQUEST),
  INACTIVE_STATUS_CHECK(
      1000021,
      "USER IS INACTIVE",
      "User is inactive, Please contact with System Administrator",
      HttpStatus.BAD_REQUEST);

  private final int errorId;
  private final String errorCode;
  private String errorMessage;
  private final HttpStatus httpStatus;

  @Override
  public int getErrorId() {
    return errorId;
  }

  @Override
  public String getErrorCode() {
    return errorCode;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
