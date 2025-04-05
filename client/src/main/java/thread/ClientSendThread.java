package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;

public class ClientSendThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ClientSendThread.class);
    private Socket socket;
    private String message;
    private SocketHandler socketHandler;


    /**
     * Constructor of thread
     *
     * @param socket socket with target IP and port
     * @param message message to the server
     */
    public ClientSendThread(Socket socket, String message) {
        this.socket = socket;
        this.message = message;
        log.info("ClientSendThread Initialized Successfully, Socket:{}", socket);
    }

//    public void sendMessage(String newMessage) {
//        if (newMessage != null) {
//            socketHandler.sendMessage(newMessage);
//            log.info("Message send successfully: {}", newMessage);
//        }
//    }

    /**
     * Override run() method for this thread
     */
    @Override
    public void run() {
        socketHandler = new SocketHandler(socket);
        log.info("ClientSendThread: Starts Sending Msg...");

        while (true) {
            if (this.message != null) {
                socketHandler.sendMessage(message);
                log.info("ClientSendThread: send msg successfully: {}", message);
                message = null;
            }
        }
    }
}