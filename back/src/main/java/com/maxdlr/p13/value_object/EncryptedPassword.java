package com.maxdlr.p13.value_object;

/**
 * EncryptedPassword
 */
public class EncryptedPassword {
  public static String generate(String plainPassword) {
    return plainPassword + "-encrypted";
  }
}
