package ru.avogp.messenger.ui.auth;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AuthPanel extends JPanel {
  private JPanel authForm, regForm;
  private JButton switchButton;

  public AuthPanel(AuthService authService, Runnable onClose) {
    setLayout(new FlowLayout(FlowLayout.CENTER));
    // Панели для переключения
    authForm = new AuthForm(
        Map.of(Event.ON_AUTH_SUCCESS, () -> {
          onClose.run();
        }), authService);

    regForm = new RegForm(
        Map.of(Event.ON_AUTH_SUCCESS, () -> {
          onClose.run();
        }), authService);

    // Кнопка для переключения
    switchButton = new JButton("Switch");
    switchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Переключение между панелями
        if (authForm.isVisible()) {
          authForm.setVisible(false);
          regForm.setVisible(true);
        } else {
          authForm.setVisible(true);
          regForm.setVisible(false);
        }
      }
    });

    regForm.setVisible(false);

    add(authForm);
    add(regForm);
    add(switchButton);
  }
}
