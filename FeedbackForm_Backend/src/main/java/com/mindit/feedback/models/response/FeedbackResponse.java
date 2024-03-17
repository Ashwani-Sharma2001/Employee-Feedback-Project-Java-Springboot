package com.mindit.feedback.models.response;

public class FeedbackResponse {

  private final boolean success;
  private final String message;

  public FeedbackResponse(boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getMessage() {
    return message;
  }
}
