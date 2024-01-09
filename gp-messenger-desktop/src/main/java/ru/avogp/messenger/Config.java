package ru.avogp.messenger;

import java.util.Map;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Config {
  private final String CURRENT_OS = System.getProperty("os.name");
  private final Map<String, String> LAF_MAP = Map.of("Linux", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel", "Windows",
      "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
  private final String DEFAULT_LAF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

  private String laf() {
    return LAF_MAP.getOrDefault(CURRENT_OS, DEFAULT_LAF);
  }

  public void apply() {
    try {
      UIManager.setLookAndFeel(laf());
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
