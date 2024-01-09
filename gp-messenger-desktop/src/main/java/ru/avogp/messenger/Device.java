package ru.avogp.messenger;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

public class Device {
  private Rectangle virtualBounds;
  private GraphicsEnvironment ge;
  private GraphicsDevice[] gs;

  private void init() {
    virtualBounds = new Rectangle();
    ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    gs = ge.getScreenDevices();
    for (int j = 0; j < gs.length; j++) {
      GraphicsDevice gd = gs[j];
      GraphicsConfiguration[] gc = gd.getConfigurations();
      for (int i = 0; i < gc.length; i++) {
        virtualBounds = virtualBounds.union(gc[i].getBounds());
      }
    }
  }

  public Device() {
    init();
  }

  public GraphicsConfiguration getConfig() {
    return gs[0].getConfigurations()[0];
  }

  private StringBuilder gsInfo() {
    StringBuilder stringBuilder = new StringBuilder("devices:\n");
    for (GraphicsDevice dev : gs) {
      stringBuilder.append(dev.toString());
      for (GraphicsConfiguration conf : dev.getConfigurations()) {
        stringBuilder.append("\t").append(conf.getColorModel().toString());
      }
    }
    return stringBuilder;
  }

  @Override
  public String toString() {
    return virtualBounds.toString() + ge.toString();
  }
}
