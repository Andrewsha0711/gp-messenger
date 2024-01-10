package ru.avogp.messenger;

import java.io.Serializable;
import java.util.concurrent.Flow.Subscriber;
import java.util.function.Consumer;

public interface Service {
  public void subscribe(Subscriber<Serializable> subscriber);

  public void register(Consumer<Service> register);
}
