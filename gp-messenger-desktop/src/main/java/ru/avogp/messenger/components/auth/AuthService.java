package ru.avogp.messenger.components.auth;

import ru.avogp.messenger.Service;
import ru.avogp.messenger.components.auth.Auth.User;

public interface AuthService extends Service {
  public void onRegister(String username, String password);

  public void onAuth(String username, String password);

  public void onAuthSuccess();

  public void onAuthFailed();
}
