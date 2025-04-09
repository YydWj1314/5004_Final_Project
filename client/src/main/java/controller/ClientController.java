package controller;

import enumeration.CommandType;
import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;
import utils.ClientReceiveMQ;
import utils.ClientSendMQ;
import utils.CommandBuilder;

import java.io.IOException;
import java.net.Socket;


/**
 * ClientController is responsible for handling message
 * passing from backend, and informing updating UI
 */
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private static final String SEVER_IP = "127.0.0.1"; // IP address listened by server

    private static final int SERVER_PORT = 10087;   // port listened by server

    private Socket socket;

    private String playerName;  // player name from login frame

    private ClientSendMQ clientSendMQ;

    private ClientReceiveMQ clientReceiveMQ;

    private ClientControllerListener listener;

    private Player localPlayer;


    /**
     * Constructor of ClientController
     *
     * @param playerName player name input in login frame
     *                   received from login frame
     */
    public ClientController(String playerName, ClientControllerListener listener) {
        this.playerName = playerName;
        this.listener = listener;
        this.clientSendMQ = new ClientSendMQ();
        this.clientReceiveMQ = new ClientReceiveMQ();
        this.localPlayer = new Player(playerName);

        createSocket();

        startClientSendThread();

        startClientReceiveThread();

        startHandlingMsg();

        // send player name received from loginFrame to server
        sendPlayerName();
    }

    /**
     * Make connection with backend game server
     * by socket with target IP and target port number
     */
    private void createSocket() {
        try {
            this.socket = new Socket(SEVER_IP, SERVER_PORT);
        } catch (IOException ex) {
            log.error("Open socket failed");
            throw new RuntimeException(ex);
        }

        log.info("Try to connect server: IP: {}, Port: {}", SEVER_IP, SERVER_PORT);
    }


    /**
     * Create thread sending message to backend
     * and start the thread
     */
    private void startClientSendThread() {
        // Start ClientSendThread
        ClientSendThread clientSendThread = new ClientSendThread(socket, clientSendMQ);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }

    /**
     * Create thread receiving message to backend
     * and start the thread
     */
    private void startClientReceiveThread() {
        ClientReceiveThread clientReceiveThread = new ClientReceiveThread(socket, clientReceiveMQ);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");
    }

    /**
     * Start new thread taking msg from MQ,
     * and start handing message
     */
    private void startHandlingMsg() {
        // Starting Message thread and taking messages
        new Thread(() -> {
            log.info("ClientController Listening for Messages...");
            while (true) {
                ClientReceiveMQ.MessageEntry entry = clientReceiveMQ.takeMessage();
                if (entry != null) {
                    log.info("CC has taken message: {}", entry.message);
                    // Handling messages
                    handleMessage(entry.message);
                }
            }
        }).start();
    }

    /**
     *
     */
    private void sendPlayerName() {
        // Encapsulate player name to JOIN command
        // eg: JOIN yyd
        String joinCommand = CommandBuilder.buildCommand(CommandType.JOIN, playerName);
        // Add command to sendMQ
        clientSendMQ.addMessage(socket, joinCommand);
        log.info("JoinCommand ==> clientSendMQ: {}", joinCommand);
    }

    /**
     * Handle message taken from messageBuffer
     *
     * @param message message taken from messageBuffer
     *                send from backend server
     */
    private void handleMessage(String message) {
        System.out.println("========== Client handleMessage info==========");

        String[] parts = message.split(" ");
        String commandType = parts[0];
        String commandArgs = message.substring(commandType.length()).trim();

        switch (commandType) {
            case "WELCOME" -> {
                // eg: WELCOME (name)daniel (id)1 (player number)1 (socket)127.0.0.1
                System.out.println("----- WELCOME -----");
                if (listener != null) {
                    String welcomeString = String.format(
                            "%s Welcome! Online player number: %s.", parts[1], parts[3]
                    );

                    // Getting socket send by server
                    String receivedSocketAddress = parts[4];
                    // Getting client local socket
                    String localSocketAddress = socket.getLocalSocketAddress().toString();
                    // Setting id for player
                    if (receivedSocketAddress.equals(localSocketAddress)) {
                        this.localPlayer.setId(Integer.parseInt(parts[2]));
                        log.info("Set ID for current player: {}", this.localPlayer.getId());
                    }

                    listener.onTextAreaUpdated(welcomeString);
                }
            }
        }
    }
}
