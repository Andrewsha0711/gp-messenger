package com.github.avo0303.messenger.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/chat", configurator = ChatEndpointConfigurator.class)
public class ChatEndpoint {
  // private Session session;
  private Logger logger;
  private ObjectMapper mapper;

  @OnOpen
  public void onOpen(final Session session) {
    // this.session = session;
    mapper = new ObjectMapper();
    logger = LoggerFactory.getLogger("session:" + session.getId());
    logger.info("session " + session.getId() + " opened");
    session.getAsyncRemote().sendText("Opened");
  }

  @OnMessage
  public void onMessage(Session session, String msg) {
    logger.info(msg);
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
  public void onError(Throwable t) {
    logger.error(t.getMessage(), t);
  }
}
