package ru.avogp.messenger;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ClientEndpoint
public class Endpoint {
  private Session session;
  private final Logger logger = LogManager.getLogger(Endpoint.class);
  private final Set<Subscriber<Serializable>> subscribers = new HashSet<>();
  private final SubmissionPublisher<Serializable> publisher = new SubmissionPublisher<>();

  public void listen(Service publisher) {
    Subscriber<Serializable> subscriber = new SubscriberImpl<>(msg -> send(msg));
    subscribers.add(subscriber);
    publisher.subscribe(subscriber);
  }

  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    logger.info("Connected to server");
  }

  @OnMessage
  public void onMessage(String message, Session session) {
    publisher.submit(message);
    logger.info("Message received from server:" + message);
  }

  public void send(Serializable msg) {
    session.getAsyncRemote().sendObject(msg);
  }

  @OnClose
  public void onClose(CloseReason reason, Session session) {
    publisher.close();
    logger.info("Closing a WebSocket due to " + reason.getReasonPhrase());
  }
}
