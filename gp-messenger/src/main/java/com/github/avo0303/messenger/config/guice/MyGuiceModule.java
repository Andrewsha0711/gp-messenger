package com.github.avo0303.messenger.config.guice;

import com.github.avo0303.messenger.config.Config;
import com.github.avo0303.messenger.service.AuthService;
import com.github.avo0303.messenger.service.AuthServiceImpl;
import com.google.inject.AbstractModule;

public class MyGuiceModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(AuthService.class).to(AuthServiceImpl.class);
  }
}
