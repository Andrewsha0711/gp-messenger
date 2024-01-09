package ru.avogp.messenger.ui;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.JFrame;
import ru.avogp.messenger.Service;
import ru.avogp.messenger.ui.auth.AuthForm;
import ru.avogp.messenger.ui.auth.AuthService;
import ru.avogp.messenger.ui.auth.AuthServiceImpl;
import ru.avogp.messenger.ui.auth.Event;

public class MainFrame extends JFrame {
  private AuthForm authForm;
  private MessengerLayout messengerLayout;

  public MainFrame(String title, Rectangle bounds, GraphicsConfiguration gc,
      Consumer<Service> reg) {
    super(gc);
    setTitle(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    AuthService authService = new AuthServiceImpl();
    authService.register(reg);

    authForm = new AuthForm(Map.of(Event.ON_AUTH_SUCCESS, () -> {
      dropAuthForm();
      showMessengerLayout();
    }), authService);

    add(authForm);

    // showMessengerLayout();
    setLocationRelativeTo(null);
    setBounds(bounds);
    setVisible(true);
  }

  private void dropAuthForm() {
    authForm.setVisible(false);
  }

  public void showMessengerLayout() {
    messengerLayout = new MessengerLayout();
    add(messengerLayout);
  }
}
