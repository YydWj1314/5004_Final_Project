package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code LoginFrame} class represents the login window for the game.
 * It allows users to enter their username and proceed to the main game interface.
 */
public class LoginFrame extends JFrame {


    private static final Logger log = LoggerFactory.getLogger(
            LoginFrame.class);

    private JLabel unameJLabel;
    private JTextField unameJTextField;
    private JButton jButton;

    /**
     * Constructs a {@code LoginFrame} window with input fields for username entry.
     */
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

            // 2. Getting username
            String username = unameJTextField.getText();
            // 3. Encapsulating command

            // 4. Jumping to main frame with command
            try {
                new MainFrame(username);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        }
    }

}
