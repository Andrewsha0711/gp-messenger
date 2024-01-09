package ru.avogp.messenger.ui.auth;

import java.util.Map;
import java.util.function.Consumer;
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

  // UI callbacks
  private final Logger logger = LogManager.getLogger(AuthForm.class);

  public AuthForm(Map<Event, Runnable> callbacks, AuthService service) {
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

    JButton loginButton = new JButton("Войти");
    loginButton.setBounds(100, 90, 100, 25);
    add(loginButton);

    loginButton.addActionListener(e -> {
      Map<String, String> user = Map.of("username", usernameField.getText(), "password",
          new String(passwordField.getPassword()));

      // Здесь можете добавить проверку имени пользователя и пароля
      // Например, можно сравнивать с предопределенными значениями
      service.onAuth(user);
      // callback(Event.ON_AUTH);
      //
      // if (username.equals("admin") && password.equals("12345")) {
      // // JOptionPane.showMessageDialog(null, "Вход выполнен успешно!");
      // callback(Event.ON_AUTH_SUCCESS);
      // } else {
      // JOptionPane.showMessageDialog(
      // null,
      // "Ошибка входа. Пожалуйста, проверьте имя пользователя и
      // пароль.");
      // }
    });
    setVisible(true);
  }
}
