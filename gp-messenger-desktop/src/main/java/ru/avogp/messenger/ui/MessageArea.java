package ru.avogp.messenger.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageArea extends JComponent {
  private final JTextArea textArea;
  private final JScrollPane scrollPane;
  private final Component parent;
  private final Logger logger;

  private Consumer<String> action;

  private final Rectangle BOUNDS_DEFAULT = new Rectangle(0, 0, 128, 72);

  public MessageArea() {
    logger = LogManager.getLogger(MessageArea.class);
    textArea = new JTextArea();

    scrollPane = new JScrollPane(textArea);

    textArea.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        return;
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (action == null) {
          return;
        }
        if (e.getKeyCode() != KeyEvent.VK_ENTER) {
          return;
        }
        action.accept(textArea.getText());
      }

      @Override
      public void keyReleased(KeyEvent e) {
        return;
      }
    });

    parent = getParent();

    setBounds(BOUNDS_DEFAULT);

    decorateTextArea();
    decorateScroll();

    add(scrollPane);
  }

  public MessageArea(Consumer<String> action) {
    this();
    this.action = action;
  }

  private void decorateTextArea() {
    textArea.setBounds(getX(), getY(), getWidth(), getHeight());
    textArea.setLineWrap(true);
    textArea.setBorder(BorderFactory.createLineBorder(
        new Color(12, 12, 12))); // TODO: Global config
  }

  private void decorateScroll() {
    scrollPane.setVerticalScrollBarPolicy(
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(12, 12, 12)));
    scrollPane.setBounds(getX(), getY(), getWidth(),
        getHeight()); // TODO: calculate
  }
}
