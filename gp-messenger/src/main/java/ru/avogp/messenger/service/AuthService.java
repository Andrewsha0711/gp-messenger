package ru.avogp.messenger.service;

import java.io.Serializable;
import java.util.Map;

public interface AuthService {

  enum Method { REGISTER, AUTH }

  class Auth {
    private class User {
      private final String username;
      // Encrypted
      private final String password;

      public User(String username, String password) {
        this.username = username;
        this.password = password;
      }
    }

    private final Method method;
    private final User user;

    public Auth(Map<Serializable, Serializable> map) {
      String username = (String)map.get("username");
      String password = (String)map.get("password");
      this.method = Method.valueOf((String)map.get("method"));
      this.user = new User(username, password);
    }

    public boolean isValid() {
      if (user.username == null) {
        return false;
      }
      if (user.password == null) {
        return false;
      }
      if (method == null) {
        return false;
      }
      return true;
    }
  }

  class AuthServiceException extends Exception {
    public AuthServiceException(String msg) { super(msg); }
  }

  void auth(Auth dto) throws AuthServiceException;

  void register(Auth dto);
}
