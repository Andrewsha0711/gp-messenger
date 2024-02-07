package ru.avogp.messenger.components.auth;

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
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(4, 4, 4, 4);

    gbc.gridy = fieldCounter;
    fieldCounter += 1;
    add(label, gbc);

    gbc.gridy = fieldCounter;
    fieldCounter += 1;
    add(field, gbc);

    gbc.gridy = fieldCounter;
  }

  public AuthForm(Map<Event, Runnable> callbacks, AuthService service) {
    super();
    setSize(new Dimension(600, 600));
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
    setLayout(new GridBagLayout());

    usernameField = new JTextField(20);
    passwordField = new JPasswordField(32);
    decorateField(new JLabel("Username"), usernameField);
    decorateField(new JLabel("Password"), passwordField);

    JButton loginButton = new JButton("Войти");
    loginButton.setMargin(new Insets(4, 4, 4, 4));
    loginButton.setMaximumSize(new Dimension(
        Integer.MAX_VALUE, loginButton.getPreferredSize().height));
    gbc.weighty = 0;
    add(loginButton, gbc);

    loginButton.addActionListener(e -> {
      service.onAuth(usernameField.getText(),
          new String(passwordField.getPassword()));
    });
    setVisible(true);
  }
}
