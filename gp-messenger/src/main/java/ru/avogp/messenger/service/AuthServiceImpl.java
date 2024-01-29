package ru.avogp.messenger.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthServiceImpl implements AuthService {
  private final Logger logger;

  public AuthServiceImpl() { logger = LogManager.getLogger(AuthService.class); }

  @Override
  public void auth(Auth auth) throws AuthServiceException {}

  @Override
  public void register(Auth dto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'register'");
  }
}
