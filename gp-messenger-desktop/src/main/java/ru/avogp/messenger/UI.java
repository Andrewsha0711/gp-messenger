package ru.avogp.messenger;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.UIManager;
import ru.avogp.messenger.components.MainFrame;

public class UI {
  private final GraphicsConfiguration gc;

  private final String FRAME_TITLE = "gp-messenger";
  private final Rectangle FRAME_INITIAL_BOUNDS = new Rectangle(720, 720);

  public enum ColorScheme {
    ACCENT
  }

  public static final Map<ColorScheme, Color> COLOR_SCHEME = Map.of(
      ColorScheme.ACCENT, UIManager.getColor("TextField.selectionForeground"));

  private final MainFrame frame;

  public UI(GraphicsConfiguration gc, Consumer<Service> reg) {
    this.gc = gc;
    frame = new MainFrame(FRAME_TITLE, center(), gc, reg);
  }

  private Rectangle center() {
    int offsetX = (int) (gc.getBounds().getWidth() - FRAME_INITIAL_BOUNDS.getWidth()) / 2;
    int offsetY = (int) (gc.getBounds().getHeight() - FRAME_INITIAL_BOUNDS.getHeight()) /
        2;
    return new Rectangle(offsetX, offsetY, (int) FRAME_INITIAL_BOUNDS.getWidth(),
        (int) FRAME_INITIAL_BOUNDS.getHeight());
  }

  private void colors() {
  }
}
