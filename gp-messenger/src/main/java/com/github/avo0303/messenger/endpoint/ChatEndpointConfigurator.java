package com.github.avo0303.messenger.endpoint;

import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpHeader;

public class ChatEndpointConfigurator extends Configurator {
  private final Logger logger = LogManager.getLogger(ChatEndpointConfigurator.class);
  private final String TOKEN_PROP = "token";
  private final String AUTH_HEADER_TYPE = "Bearer";

  @Override
  public void modifyHandshake(ServerEndpointConfig sec,
      HandshakeRequest request,
      HandshakeResponse response) {
    List<String> authHeader = request.getHeaders().get(HttpHeader.AUTHORIZATION.toString());
    if (authHeader == null) {
      return;
    }
    String[] str = authHeader.get(0).split(" ");
    logger.info(str[0]);
    if (!AUTH_HEADER_TYPE.equals(str[0])) {
      return;
    }
    // TODO: Validate token
    sec.getUserProperties().put(TOKEN_PROP, str[1]);
  }
}
