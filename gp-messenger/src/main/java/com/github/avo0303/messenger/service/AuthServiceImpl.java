package com.github.avo0303.messenger.service;

import com.github.avo0303.messenger.model.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthServiceImpl implements AuthService {
  private final Logger logger;

  public AuthServiceImpl() {
    logger = LoggerFactory.getLogger(AuthService.class);
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
