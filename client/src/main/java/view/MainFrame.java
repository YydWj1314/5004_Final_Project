package view;

import controller.ClientController;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;

import java.net.Socket;

/**
 * The {@code MainFrame} class represents the main game window.
 * It contains a background panel, a system message area, and a play button.
 */
public class MainFrame extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(MainFrame.class);

    private CardPanel cardPanel;
    private JTextArea systemMessageArea;
    private JButton playButton;
    private ClientSendThread clientSendThread;
    private ClientReceiveThread clientReceiveThread;


    private String message;
    private Socket socket;

    private ClientController clientController;
//  private ClientSendThread clientSendThread;
//  private ClientReceiveThread clientReceiveThread;
//  private Player currentPlayer = new Player();
//  private List<CardVO> cardVOList = new ArrayList<>();
//  private List<CardVO> selectedCardVOList = new ArrayList<>();

    /**
     * Constructs the {@code MainFrame} and initializes UI components.
     */
    public MainFrame(Socket socket, String message) throws IOException {

        this.socket = socket;
        this.message = message;

        log.info("Initializing MainFrame with socket: [{}] and message: [{}]",
                (socket != null ? "Connected to " + socket.getRemoteSocketAddress() : "null"),
                message);

        // Setting window attributes
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
//    log.debug("Window properties set: Size=1200x700, Centered, ExitOnClose");

        // add CardPanel
        cardPanel = new CardPanel();
        cardPanel.setBounds(0, 0, 1200, 700);
        cardPanel.setLayout(null);
        this.add(cardPanel);
//    log.debug("CardPanel initialized and added to main frame");


        // Adding System message area
        systemMessageArea = new JTextArea();
        systemMessageArea.setEditable(false); // read only
        systemMessageArea.setLineWrap(true);  //wrap text
        JScrollPane scrollPane = new JScrollPane(
                systemMessageArea);
        scrollPane.setBounds(600, 475, 500, 150);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cardPanel.add(scrollPane);
//    log.debug("System message area initialized at 600,475 (500x150)");


        // Initializing `playButton`
        playButton = new JButton("Play");
        playButton.setBounds(200, 400, 100, 50);
        cardPanel.add(playButton);
//    log.info("UI components initialized. Setting frame visible...");

        this.setVisible(true);
//    log.info("MainFrame displayed successfully");


        // Start ClientSendThread
        clientSendThread = new ClientSendThread(socket, message);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");

        clientReceiveThread = new ClientReceiveThread(socket);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");

        clientController = new ClientController(message, clientSendThread);


    }
}
