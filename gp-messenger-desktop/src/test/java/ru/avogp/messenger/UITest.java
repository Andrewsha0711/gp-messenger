package ru.avogp.messenger;

import java.awt.Color;
import java.util.Set;
import javax.swing.UIManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class UITest {
  private static final Logger logger = LoggerFactory.getLogger(UITest.class);

  @Test
  public void testLoadSystemTheme() {
    Color bg = UIManager.getColor("Panel.background");
    logger.info(() -> bg.toString());
    Assertions.assertTrue(bg != null);

    Set<Object> set = UIManager.getDefaults().keySet();
    // for (Object k : set) {
    // logger.info(() -> k.toString());
    // }
    Assertions.assertTrue(set.size() > 10);
  }
}
