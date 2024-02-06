package com.github.avo0303.messenger.model.impl;

import com.github.avo0303.messenger.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;

public class UserImpl implements User {
  @NotBlank(message = "username required")
  private String username;

  @Min(value = 6, message = "password too short")
  private String password;

  public UserImpl(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public Map<String, ?> props() {
    return Map.of("username", username, "password", password);
  }
}
