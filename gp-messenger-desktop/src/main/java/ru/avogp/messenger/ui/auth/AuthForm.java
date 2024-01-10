package ru.avogp.messenger.ui.auth;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.avogp.messenger.ui.auth.AuthService.User;

public class AuthForm extends JPanel {
  private JTextField usernameField;
  private JPasswordField passwordField;

  // UI configuration
  private int fieldCounter = 0;
  private final GridBagConstraints gbc = new GridBagConstraints();

  private final Logger logger = LogManager.getLogger(AuthForm.class);

  private void decorateField(JLabel label, JTextField field) {
    label.setSize(new Dimension(400, 60));
    gbc.gridx = 0;
    gbc.weightx = 100;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(4, 4, 4, 4);

    gbc.gridy = fieldCounter;
    fieldCounter += 1;
    gbc.weighty = 0.8;
    add(label, gbc);

    gbc.gridy = fieldCounter;
    fieldCounter += 1;
    gbc.weighty = 1.6;
    add(field, gbc);

    gbc.gridy = fieldCounter;
  }

  public AuthForm(Map<Event, Runnable> callbacks, AuthService service) {
    super();
    setSize(new Dimension(600, 600));
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
    setLayout(new GridBagLayout());

    decorateField(new JLabel("Username"), new JTextField(20));
    decorateField(new JLabel("Password"), new JPasswordField(20));

    JButton loginButton = new JButton("Войти");
    loginButton.setMargin(new Insets(4, 4, 4, 4));
    loginButton.setMaximumSize(new Dimension(
        Integer.MAX_VALUE, loginButton.getPreferredSize().height));
    gbc.weighty += 1;
    add(loginButton, gbc);

    loginButton.addActionListener(e -> {
      User user = new User(usernameField.getText(),
          new String(passwordField.getPassword()));
      service.onAuth(user);
    });
    setVisible(true);
  }
}
