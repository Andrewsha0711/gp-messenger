package com.github.avo0303.messenger.service;

import com.github.avo0303.messenger.model.User;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServiceImpl implements AuthService {
  private final Logger logger;
  private final String USERNAME = "username";
  private final String PASSWORD = "password";

  public AuthServiceImpl() {
    logger = LogManager.getLogger(AuthService.class);
  }

  @Override
  public String register(@Valid User user) throws AuthServiceException {
    return "reg-token";
  }

  @Override
  public String auth(@Valid User user) throws AuthServiceException {
    return "auth-token";
  }
}
