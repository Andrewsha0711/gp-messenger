package ru.avogp.messenger;

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

@WebServlet(urlPatterns = { "auth" })
public class AuthServlet extends HttpServlet {
  private final Logger logger = LogManager.getLogger(AuthServlet.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    resp.getWriter().write("ping");
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    logger.info(req.toString());
    logger.info(req.getParameterMap().toString());
    String authHeader = req.getHeader(HttpHeader.AUTHORIZATION.toString());
    if (authHeader != null) {
      StringTokenizer st = new StringTokenizer(authHeader);
      if (st.hasMoreTokens()) {
        String basic = st.nextToken();

        if (basic.equalsIgnoreCase("Basic")) {
          try {
            String credentials = new String(Base64.getDecoder().decode(st.nextToken()), "UTF-8");
            logger.debug("Credentials: " + credentials);
            int p = credentials.indexOf(":");
            if (p != -1) {
              String login = credentials.substring(0, p).trim();
              // TODO: fix string password
              String password = credentials.substring(p + 1).trim();

              // new Credentials(login, password);
            } else {
              logger.error("Invalid authentication token");
            }
          } catch (UnsupportedEncodingException e) {
            logger.warn("Couldn't retrieve authentication", e);
          }
        }
      }
    }
    resp.getWriter().write("TOKEN");
  }
}
