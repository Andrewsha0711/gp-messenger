package ru.avogp.messenger.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import ru.avogp.messenger.UI;

public class MessengerLayout extends JPanel {
  private JList<String> chatList;
  private JTextArea messageArea;
  private JTextField inputField;

  public MessengerLayout() {
    setLayout(new BorderLayout());

    // Панель для бокового списка чатов
    JPanel sidePanel = new JPanel(new BorderLayout());
    sidePanel.setPreferredSize(new Dimension(200, 0));
    sidePanel.setBorder(BorderFactory.createLineBorder(
        UI.COLOR_SCHEME.get(UI.ColorScheme.ACCENT)));

    String[] chats = { "Чат 1", "Чат 2",
        "Чат 3" }; // Замените этот массив вашим списком чатов
    chatList = new JList<>(chats);
    chatList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane chatListScrollPane = new JScrollPane(chatList);

    sidePanel.add(chatListScrollPane, BorderLayout.CENTER);
    add(sidePanel, BorderLayout.WEST);

    // Панель для отображения сообщений и ввода текста
    JPanel messagePanel = new JPanel(new BorderLayout());

    messageArea = new JTextArea();
    messageArea.setEditable(false);
    JScrollPane messageScrollPane = new JScrollPane(messageArea);

    messagePanel.add(messageScrollPane, BorderLayout.CENTER);

    inputField = new JTextField();
    inputField.setCaretColor(Color.WHITE);
    JButton sendButton = new JButton("Отправить");

    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(inputField, BorderLayout.CENTER);
    inputPanel.add(sendButton, BorderLayout.EAST);

    sendButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        sendMessage();
      }
    });

    messagePanel.add(inputPanel, BorderLayout.SOUTH);
    add(messagePanel, BorderLayout.CENTER);
    setVisible(true);
  }

  private void sendMessage() {
    String message = inputField.getText();
    if (!message.isEmpty()) {
      messageArea.append("Вы: " + message + "\n");
      inputField.setText("");
      // Здесь можно добавить логику отправки сообщения в выбранный чат
    }
  }
}
