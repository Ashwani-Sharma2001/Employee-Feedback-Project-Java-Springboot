package com.mindit.feedback.utils;

public class EmailUtil {
  public static boolean isValidEmail(String email) {
    return email != null && email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^. -]+@[a-zA-Z0-9. -]+$");
  }
}
