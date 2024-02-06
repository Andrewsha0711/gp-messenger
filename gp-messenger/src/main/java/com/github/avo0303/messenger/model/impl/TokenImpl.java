package com.github.avo0303.messenger.model.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

import com.github.avo0303.messenger.model.Token;

public class TokenImpl implements Token {
  private final SecureRandom secureRandom = new SecureRandom(); // threadsafe
  private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

  private final String tokenStr;
  private final LocalDateTime expiredDate;

  public TokenImpl() {
    this.tokenStr = generateNewToken();
    this.expiredDate = LocalDateTime.now().plusDays(1);
  }

  private String generateNewToken() {
    byte[] randomBytes = new byte[24];
    secureRandom.nextBytes(randomBytes);
    return base64Encoder.encodeToString(randomBytes);
  }

  public boolean expired() {
    return expiredDate.isBefore(LocalDateTime.now());
  }

  @Override
  public String toString() {
    return tokenStr;
  }
}
