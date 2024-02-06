package com.github.avo0303.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avo0303.messenger.config.Config;
import com.github.avo0303.messenger.model.impl.UserImpl;
import com.github.avo0303.messenger.service.AuthService;
import com.github.avo0303.messenger.service.AuthService.AuthServiceException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;
import org.eclipse.jetty.http.HttpHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = { "auth", "reg" })
@Singleton
public class AuthServlet extends HttpServlet {
  private Logger logger;
  @Inject
  private AuthService service;
  private ObjectMapper mapper;
  @Inject
  private Config config;

  @Inject
  public void setConfig(Config config) {
    this.config = config;
  }

  @Override
  public void init() throws ServletException {
    logger = LoggerFactory.getLogger(AuthServlet.class);
    mapper = new ObjectMapper();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().write(config == null ? "not workin" : "w");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      if ("/auth".equals(req.getRequestURI())) {
        auth(req, resp);
      } else if ("/reg".equals(req.getRequestURI())) {
        reg(req, resp);
      } else {
        return;
      }
    } catch (AuthServiceException e) {
      resp.getWriter().write(e.getMessage());
    }
  }

  private void reg(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, AuthServiceException {

    Map<String, String[]> params = req.getParameterMap();

    // TODO:
    // String token = service.register(
    // new User(username.length == 1 ? username[0] : null,
    // password.length == 1 ? password[0].toCharArray() : null));
    // resp.getWriter().write(token);
  }

  private void auth(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String authHeader = req.getHeader(HttpHeader.AUTHORIZATION.toString());
    if (authHeader == null) {
      return;
    }
    StringTokenizer st = new StringTokenizer(authHeader);
    if (!st.hasMoreTokens()) {
      return;
    }
    String basic = st.nextToken();

    if (!"Basic".equalsIgnoreCase(basic)) {
      return;
    }
    try {
      String credentials = new String(Base64.getDecoder().decode(st.nextToken()), "UTF-8");
      logger.debug("Credentials: " + credentials);
      int p = credentials.indexOf(":");
      if (p != -1) {
        String username = credentials.substring(0, p).trim();
        char[] password = credentials.substring(p + 1).trim().toCharArray();
        // TODO
        // String token = service.auth(new UserImpl(username, password));
        // resp.getWriter().write(token);
      } else {
        logger.error("Invalid authentication token");
      }
    } catch (UnsupportedEncodingException e) {
      logger.warn("Couldn't retrieve authentication", e);
    }
  }
}
