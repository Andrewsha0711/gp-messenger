package ru.avogp.messenger.components.auth;

import java.io.Serializable;

public class Auth implements Serializable {

  enum Method {
    REGISTER, AUTH
  }

  class User {
    public final String username;
    public final String password;

    public User(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }

  public final User user;
  public final Method method;

  public Auth(Method method, String username, String password) {
    this.method = method;
    this.user = new User(username, password);
  }
}
