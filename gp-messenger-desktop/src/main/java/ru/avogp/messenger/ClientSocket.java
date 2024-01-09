package ru.avogp.messenger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.avogp.messenger.ui.auth.AuthService;

@ClientEndpoint
public class ClientSocket extends ServiceSubscriber<Serializable> {
  private Session session;
  private final Logger logger = LogManager.getLogger(ClientSocket.class);

  // private final SubmissionPublisher<String> publisher = new
  // SubmissionPublisher<>();
  //

  private List<AuthService> publishers = new ArrayList<>();

  public void addPublisher(Service publisher) {
    publisher.subscribe(this);
  }

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    // session.getAsyncRemote().sendText("PING");
    // String authData = "{\"username\": \"your_username\", \"password\":
    // \"your_password\"}"; session.getAsyncRemote().sendText(authData);
    logger.info("Connected to server");
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    // publisher.submit(message);
    logger.info("Message received from server:" + message);
  }

  public void send(Serializable msg) {
    session.getAsyncRemote().sendObject(msg);
  }

  @OnClose
  public void onClose(CloseReason reason, Session session) {
    // publisher.close();
    logger.info("Closing a WebSocket due to " + reason.getReasonPhrase());
  }

  @Override
  public void onNext(Serializable item) {
    super.onNext(item);
    send(item);
  }
}
