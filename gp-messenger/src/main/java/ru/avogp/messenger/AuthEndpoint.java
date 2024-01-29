package ru.avogp.messenger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.avogp.messenger.service.AuthService;
import ru.avogp.messenger.service.AuthServiceImpl;

@ServerEndpoint(value = "/auth")
public class AuthEndpoint {
  private Session session;
  private Logger logger;
  private ObjectMapper mapper;

  private AuthService service;

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    mapper = new ObjectMapper();
    service = new AuthServiceImpl();
    // service.handle(e -> exceptionHandler.addType(e));

    logger = LogManager.getLogger(AuthEndpoint.class);
    logger.info("session" + session.getId() + " opened");
  }

  @OnMessage
  public void onMessage(InputStream is) {
    try {
      TypeReference<HashMap<Serializable, Serializable>> tr =
          new TypeReference<HashMap<Serializable, Serializable>>() {};
      Map<Serializable, Serializable> map = mapper.readValue(is, tr);
      logger.info(map.toString());
      service.auth(new AuthService.Auth(map));
    } catch (Exception e) {
      logger.error(e.getMessage());
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
