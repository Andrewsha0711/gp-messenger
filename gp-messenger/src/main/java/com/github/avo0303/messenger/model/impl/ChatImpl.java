package com.github.avo0303.messenger.model.impl;

import com.github.avo0303.messenger.model.Chat;
import java.util.HashSet;
import java.util.Set;

public class ChatImpl implements Chat {
  // Set of token
  private final Set<String> tokens;

  public ChatImpl() {
    this.tokens = new HashSet<>();
  }

  public void addUser(String token) {
    tokens.add(token);
  }

  public void onMessage(String token, String msg) {
    // TODO: put to history
  }
}
