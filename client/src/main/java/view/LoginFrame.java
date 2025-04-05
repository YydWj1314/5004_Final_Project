package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClientMessageBuffer;

/**
 * The {@code LoginFrame} class represents the login window for the game.
 * It allows users to enter their username and proceed to the main game interface.
 */
public class LoginFrame extends JFrame {
  private static final String SEVER_IP = "127.0.0.1";

  private static final int SERVER_PORT = 10087;
  /**
   * Constructs a {@code LoginFrame} window with input fields for username entry.
   */
  private static final Logger log = LoggerFactory.getLogger(
      LoginFrame.class);

  private JLabel unameJLabel;
  private JTextField unameJTextField;
  private JButton jButton;

  public LoginFrame() {
    // Creating component objects
    this.unameJLabel = new JLabel("UserName");
    this.unameJTextField = new JTextField();
    this.jButton = new JButton("Login");

    // Setting attributes
    this.setSize(400, 300);
    this.setVisible(true);
    this.setLocationRelativeTo(null);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new GridLayout(2, 2));

    // Adding components to the window
    this.add(unameJLabel);
    this.add(unameJTextField);
    this.add(jButton);
    this.pack();  // auto layout

    // Binding event listener
    MyEvent myEvent = new MyEvent();
    this.jButton.addActionListener(myEvent);
  }
  /**
   * Inner class to handle the login button click event.
   * Transitions from {@code LoginFrame} to {@code MainFrame}.
   */
  class MyEvent implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      // 1. Creating socket connecting server
      Socket socket = null;
      try {
        socket = new Socket(SEVER_IP, SERVER_PORT);
      } catch (IOException ex) {
        log.error("Open socket failed");
        throw new RuntimeException(ex);
      }

      log.info("Try to connect server: IP: {}, Port: {}", SEVER_IP, SERVER_PORT);
      // 2. Getting username
      String username = unameJTextField.getText();
      // 3. Encapsulating command
//      String joinMessage = CommandBuilder.buildCommand(CommandType.JOIN, username);
      // 4. Jumping to main frame with command
      try {
        ClientMessageBuffer.addMessage(socket, username);
        new MainFrame(socket, username);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
      dispose();
    }
  }

}
