package ru.avogp.messenger.ui.auth;

import java.awt.Color;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.avogp.messenger.ui.auth.AuthService.User;

public class RegForm extends JPanel {
  private JTextField usernameField;
  private JPasswordField passwordField;
  private JPasswordField password2Field;

  private final Logger logger = LogManager.getLogger(RegForm.class);

  public RegForm(Map<Event, Runnable> callbacks, AuthService service) {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createLineBorder(Color.WHITE));
    JLabel userLabel = new JLabel("Имя пользователя:");
    userLabel.setBounds(10, 20, 120, 25);
    add(userLabel);

    usernameField = new JTextField(20);
    usernameField.setBounds(140, 20, 140, 25);
    add(usernameField);

    JLabel passwordLabel = new JLabel("Пароль:");
    passwordLabel.setBounds(10, 50, 120, 25);
    add(passwordLabel);

    passwordField = new JPasswordField(20);
    passwordField.setBounds(140, 50, 140, 25);
    add(passwordField);

    JLabel password2Label = new JLabel("Повторите пароль:");
    password2Label.setBounds(10, 50, 120, 25);
    add(password2Label);

    password2Field = new JPasswordField(20);
    password2Field.setBounds(140, 50, 140, 25);
    add(password2Field);

    JButton regButton = new JButton("Зарегистрироваться");
    regButton.setBounds(100, 90, 100, 25);
    add(regButton);

    regButton.addActionListener(e -> {
      User user = new User(usernameField.getText(),
          new String(passwordField.getPassword()));
      service.onRegister(user);
    });
    setVisible(true);
  }
}
