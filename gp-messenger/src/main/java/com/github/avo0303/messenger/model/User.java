package com.github.avo0303.messenger.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class User {
  @NotBlank(message = "username required")
  private String username;

  @Min(value = 6, message = "password too short")
  private char[] password;

  public User(String username, char[] password) {
    this.username = username;
    this.password = password;
  }
}
