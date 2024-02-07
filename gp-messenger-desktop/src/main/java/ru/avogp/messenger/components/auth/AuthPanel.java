package ru.avogp.messenger.components.auth;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AuthPanel extends JPanel {
  private JPanel authForm, regForm;
  private JButton switchButton;

  private final GridBagConstraints gbc = new GridBagConstraints();

  public AuthPanel(AuthService authService, Runnable onClose) {

    setLayout(new GridBagLayout());

    gbc.gridx = 0;
    gbc.weightx = 1;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.CENTER;
    gbc.insets = new Insets(4, 4, 4, 4);
    gbc.gridy = 0;

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
    switchButton.setMaximumSize(new Dimension(
        Integer.MAX_VALUE, switchButton.getPreferredSize().height));
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

    add(authForm, gbc);
    add(regForm, gbc);
    gbc.gridy += 1;
    add(switchButton, gbc);
  }
}
