package ru.avogp.messenger.ui.auth;

import java.util.Map;
import ru.avogp.messenger.Service;

public interface AuthService extends Service {
  public void onAuth(Map<String, String> user);

  public void onAuthSuccess();

  public void onAuthFailed();
}
