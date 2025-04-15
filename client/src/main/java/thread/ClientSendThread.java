package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClientSendMQ;
import utils.SocketHandler;

import java.net.Socket;

public class ClientSendThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ClientSendThread.class);

    private Socket socket;

    private SocketHandler socketHandler;

    private ClientSendMQ clientSendMQ;


    /**
     * Constructor of thread
     *
     * @param socket socket with target IP and port
     * @param clientSendMQ
     */
    public ClientSendThread(Socket socket, ClientSendMQ clientSendMQ) {
        this.socket = socket;
        this.clientSendMQ = clientSendMQ;
        this.socketHandler = new SocketHandler(socket);
        log.info("ClientSendThread Initialized Successfully, Socket:{}", socket);
    }

    /**
     * @param message
     */
    public void sendMessage(String message) {
        if (message != null) {
            this.clientSendMQ.addMessage(message);
            log.info("Message send successfully: {}", message);
        }
    }

    /**
     * Override run() method for this thread
     * Taking msg from MQ and send msg by socketHandler
     */
    @Override
    public void run() {
        System.out.println("======== ClientSendThread info ========");
        socketHandler = new SocketHandler(socket);
        log.info("ClientSendThread: Starts Sending Msg...");

        while (true) {  // busy waiting
            String message = clientSendMQ.takeMessage();
            log.info("ClientSendThread took msg successfully: {}", message);
            socketHandler.sendMessage(message);
        }
    }
}