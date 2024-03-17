package com.mindit.feedback.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class EncryptionService {
  private EncryptionService() {}

  private static String salt = "mindit123systems456salt789";

  public static String encrypt(String stringToEncrypt)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    KeySpec spec = new PBEKeySpec(stringToEncrypt.toCharArray(), salt.getBytes(), 65536, 128);
    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = factory.generateSecret(spec).getEncoded();
    Base64.Encoder enc = Base64.getEncoder();
    return enc.encodeToString(hash);
  }
}
