package com.lockerdelivery.delivery.utils;

import java.security.SecureRandom;

public class AccessCodeGenerator {

  private static final SecureRandom RANDOM = new SecureRandom();

  private AccessCodeGenerator() {
    throw new IllegalArgumentException("AccessCodeGenerator is a utility class");
  }

  public static String generateAccessCode() {
    int code = 100000 + RANDOM.nextInt(900000);
    return String.valueOf(code);
  }

}
