package com.github.avo0303.messenger.config.guice;

import com.github.avo0303.messenger.AuthServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class GuiceServletContextListenerCustom
    extends GuiceServletContextListener {

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new GuiceModuleBase(), new ServletModule() {
      @Override
      protected void configureServlets() {
        serve("/auth").with(AuthServlet.class);
      }
    });
  }
}
