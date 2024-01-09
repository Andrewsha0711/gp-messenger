package ru.avogp;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value = "/chat")
public class ChatEndpoint {
  private Session session;
  private Logger logger;

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    logger = LogManager.getLogger("session:" + session.getId());
    logger.info("session" + session.getId() + " opened");
  }

  @OnMessage
  public void onMessage(String message) {
    if (session != null && session.isOpen()) {
      logger.info(message);
      try {
        session.getBasicRemote().sendText(message);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @OnClose
  public void onClose(CloseReason reason) {
    logger.info("session closed");
    try {
      this.session.close(reason);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @OnError
  public void onError(Throwable t) {
    logger.error(t.getMessage(), t);
  }
}
