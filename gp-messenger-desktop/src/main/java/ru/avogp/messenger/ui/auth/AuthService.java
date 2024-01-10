package ru.avogp.messenger.ui.auth;

import ru.avogp.messenger.Service;

public interface AuthService extends Service {
  class User {
    final String username;
    final String password;

    public User(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }

  public void onRegister(User user);

  public void onAuth(User user);

  public void onAuthSuccess();

  public void onAuthFailed();
}
