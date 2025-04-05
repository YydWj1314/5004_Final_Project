package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ClientReceiveThread;
import thread.ClientSendThread;
import utils.ClientMessageBuffer;

import java.io.IOException;
import java.net.Socket;


/**
 * ClientController is responsible for handling message
 * passing from backend, and informing updating UI
 */
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private static final String SEVER_IP = "127.0.0.1";

    private static final int SERVER_PORT = 10087;

    private Socket socket;

    private String message;


    /**
     * Constructor of ClientController
     *
     * @param message message transmit from login frame
     */
    public ClientController(String message) {
        this.message = message;
        createSocket();

        startClientSendThread();

        startClientReceiveThread();

        ClientMessageBuffer.addMessage(socket, message);  //test

        startMessageThread();
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
     * Create thread receiving message to backend
     * and start the thread
     */
    private void startClientReceiveThread() {
        ClientReceiveThread clientReceiveThread = new ClientReceiveThread(socket);
        clientReceiveThread.start();
        log.info("ClientReceiveThread Started Successfully");
    }

    /**
     * Create thread sending message to backend
     * and start the thread
     */
    private void startClientSendThread() {
        // Start ClientSendThread
        ClientSendThread clientSendThread = new ClientSendThread(socket, message);
        clientSendThread.start();
        log.info("ClientSendThread Started Successfully");
    }


    /**
     * Create new thread handing message receiving from backend
     * and start the thread
     */
    private void startMessageThread() {
        // Starting Message thread and taking messages
        new Thread(() -> {
            log.info("ClientController Listening for Messages...");
            while (true) {
                ClientMessageBuffer.MessageEntry entry = ClientMessageBuffer.takeMessage();
                if (entry != null) {
                    log.info("CC has taken message: {}", entry.message);
                    // Handling messages
                    handleMessage(entry.message);
                }
            }
        }).start();
    }

    /**
     * @param message message taken from messageBuffer
     *                send from backend server
     */
    private void handleMessage(String message) {
        System.out.println("======= handleMessage =======");

        log.info("CC starts processing message: {}", message);

    }


}
