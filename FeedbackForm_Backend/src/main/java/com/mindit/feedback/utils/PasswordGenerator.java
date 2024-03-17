package com.mindit.feedback.utils;

import java.security.SecureRandom;

public class PasswordGenerator {
  private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
  private static final String DIGITS = "0123456789";

  public static String generateRandomPassword(int length) {
    SecureRandom random = new SecureRandom();
    StringBuilder password = new StringBuilder();
    password.append(UPPER.charAt(random.nextInt(UPPER.length())));
    password.append(LOWER.charAt(random.nextInt(LOWER.length())));
    password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

    String allCharacters = UPPER + LOWER + DIGITS;
    for (int i = 4; i < length; i++) {
      password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
    }

    return shuffleString(password.toString());
  }

  private static String shuffleString(String input) {
    char[] characters = input.toCharArray();
    SecureRandom random = new SecureRandom();
    for (int i = 0; i < characters.length; i++) {
      int randomIndex = random.nextInt(characters.length);
      char temp = characters[i];
      characters[i] = characters[randomIndex];
      characters[randomIndex] = temp;
    }
    return new String(characters);
  }
}
