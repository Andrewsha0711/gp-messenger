package com.github.avo0303.messenger.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.avo0303.messenger.service.AuthService;
import jakarta.inject.Inject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/chat/{chatId}", configurator = ChatEndpointConfigurator.class)
public class ChatEndpoint {
  // private Session session;
  private Logger logger;
  private ObjectMapper mapper;
  @Inject
  private AuthService authService;

  class SessionException extends Exception {
    public SessionException(String msg) {
      super(msg);
    }
  }

  @OnOpen
  public void onOpen(final Session session, @PathParam("chatId") String chatId)
      throws IOException {
    // this.session = session;
    String token = (String) session.getUserProperties().get("token");
    if (token == null) {
      onError(session, new SessionException("no token provided"));
    }
    mapper = new ObjectMapper();
    logger = LoggerFactory.getLogger("session:" + session.getId());
    logger.info("session " + session.getId() + " opened");
    session.getAsyncRemote().sendText("Opened");
  }

  @OnMessage
  public void onMessage(Session session, String msg) {
    session.getAsyncRemote().sendText(msg);
  }

  @OnMessage
  public void onMessage(Session session, ByteBuffer bb) {
    logger.info("ON_MESSAGE");
    session.getAsyncRemote().sendBinary(bb);
  }

  @OnClose
  public void onClose(Session session, CloseReason reason) {
    logger.info("session closed");
  }

  @OnError
  public void onError(Session session, Throwable t) {
    try {
      session.close();
    } catch (IOException e) {
      logger.error("cannot close session " + session.getId());
    }
    logger.error(t.getMessage(), t);
  }
}
