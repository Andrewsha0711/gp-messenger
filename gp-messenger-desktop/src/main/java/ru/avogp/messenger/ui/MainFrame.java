package ru.avogp.messenger.ui;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.util.function.Consumer;
import javax.swing.JFrame;
import ru.avogp.messenger.Service;
import ru.avogp.messenger.ui.auth.AuthPanel;
import ru.avogp.messenger.ui.auth.AuthService;
import ru.avogp.messenger.ui.auth.AuthServiceImpl;

public class MainFrame extends JFrame {
  private MessengerLayout messengerLayout;
  private AuthPanel authPanel;

  public MainFrame(String title, Rectangle bounds, GraphicsConfiguration gc,
      Consumer<Service> reg) {
    super(gc);
    setTitle(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    AuthService authService = new AuthServiceImpl();
    authService.register(reg);

    authPanel = new AuthPanel(authService, () -> {
      authPanel.setVisible(false);
      showMessengerLayout();
    });

    add(authPanel);

    // showMessengerLayout();
    setLocationRelativeTo(null);
    setBounds(bounds);
    setVisible(true);
  }

  public void showMessengerLayout() {
    messengerLayout = new MessengerLayout();
    add(messengerLayout);
  }
}
