package com.github.avo0303.messenger.config.guice;

import com.github.avo0303.messenger.service.AuthService;
import com.github.avo0303.messenger.service.AuthServiceImpl;
import com.google.inject.AbstractModule;

public class GuiceModuleBase extends AbstractModule {

  @Override
  protected void configure() {
    bind(AuthService.class).to(AuthServiceImpl.class);
  }
}
