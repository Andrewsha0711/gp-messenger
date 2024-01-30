package com.github.avo0303.messenger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avo0303.messenger.model.User;
import com.github.avo0303.messenger.service.AuthService;
import com.github.avo0303.messenger.service.AuthService.AuthServiceException;
import com.github.avo0303.messenger.service.AuthServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.StringTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpHeader;

@WebServlet(urlPatterns = { "auth", "reg" })
public class AuthServlet extends HttpServlet {
  private final Logger logger = LogManager.getLogger(AuthServlet.class);
  private final AuthService service = new AuthServiceImpl();
  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().write("ping");
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
    User user = mapper.readValue(req.getInputStream(), User.class);
    String token = service.register(user);
    resp.getWriter().write(token);
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
        String token = service.auth(new User(username, password));
        resp.getWriter().write(token);
      } else {
        logger.error("Invalid authentication token");
      }
    } catch (UnsupportedEncodingException e) {
      logger.warn("Couldn't retrieve authentication", e);
    } catch (AuthServiceException e) {
      resp.sendError(403, "bad credentials");
      logger.error(e.getMessage(), e);
    }
  }
}
