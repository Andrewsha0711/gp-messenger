package ru.avogp.messenger.components.auth;

import java.io.Serializable;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.avogp.messenger.Service;
import ru.avogp.messenger.components.auth.Auth.Method;
import ru.avogp.messenger.components.auth.Auth.User;

public class AuthServiceImpl implements AuthService {
  private final Logger logger;

  private SubmissionPublisher<Serializable> publisher = new SubmissionPublisher<>();
  // private ServiceSubscriber<String> subscriber;

  public AuthServiceImpl() {
    // subscriber = new ServiceSubscriber<>();
    logger = LogManager.getLogger(AuthServiceImpl.class);
  }

  @Override
  public void onAuth(String username, String password) {
    publisher.submit(new Auth(Method.AUTH, username, password));
  }

  @Override
  public void onAuthSuccess() {
    // TODO Auto-generated method stub
    logger.error("Not implemented");
  }

  @Override
  public void onAuthFailed() {
    // TODO Auto-generated method stub
    logger.error("Not implemented");
  }

  @Override
  public void subscribe(Subscriber<Serializable> subscriber) {
    publisher.subscribe(subscriber);
  }

  @Override
  public void register(Consumer<Service> register) {
    register.accept(this);
  }

  @Override
  public void onRegister(String username, String password) {
    publisher.submit(new Auth(Method.REGISTER, username, password));
  }
}
