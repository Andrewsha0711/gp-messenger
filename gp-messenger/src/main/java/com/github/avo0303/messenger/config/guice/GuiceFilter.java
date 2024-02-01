package com.github.avo0303.messenger.config.guice;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*", filterName = "guiceFilter")
public class GuiceFilter extends com.google.inject.servlet.GuiceFilter {
}
