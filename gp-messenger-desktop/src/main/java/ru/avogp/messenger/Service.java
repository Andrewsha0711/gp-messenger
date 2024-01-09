package ru.avogp.messenger;

import java.io.Serializable;
import java.util.function.Consumer;

public interface Service {
  public void subscribe(ServiceSubscriber<Serializable> subscriber);

  public void register(Consumer<Service> register);
}
