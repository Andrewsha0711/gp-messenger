package com.github.avo0303.messenger.service;

import com.github.avo0303.messenger.model.User;
import jakarta.validation.Valid;

public interface AuthService {

  class AuthServiceException extends Exception {
    public AuthServiceException(String msg) {
      super(msg);
    }
  }

  String auth(@Valid User user) throws AuthServiceException;

  String register(@Valid User user) throws AuthServiceException;
}
