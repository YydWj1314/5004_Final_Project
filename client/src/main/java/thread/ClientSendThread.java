package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClientSendMQ;
import utils.SocketHandler;

import java.net.Socket;

/**
 * ClientSendThread is responsible for sending messages from the client to the server.
 * <p>
 * This thread continuously monitors the ClientSendMQ for new messages and
 * sends them to the server using the SocketHandler. It runs as a background
 * thread to handle the asynchronous sending of messages without blocking
 * the main application thread.
 */
public class ClientSendThread extends Thread {

    /** Logger for this class */
    private static final Logger log = LoggerFactory.getLogger(ClientSendThread.class);

    /** Socket connection to the server */
    private Socket socket;

    /** Handler for sending messages through the socket */
    private SocketHandler socketHandler;

    /** Message queue containing messages to be sent to the server */
    private ClientSendMQ clientSendMQ;

    /**
     * Constructor of client send thread.
     * <p>
     * This thread is used to take messages from ClientSendMQ
     * and use SocketHandler to send messages from frontend to backend.
     *
     * @param socket socket with target IP and port
     * @param clientSendMQ message queue of this thread
     */
    public ClientSendThread(Socket socket, ClientSendMQ clientSendMQ) {
        this.socket = socket;
        this.clientSendMQ = clientSendMQ;
        this.socketHandler = new SocketHandler(socket);
        log.info("ClientSendThread Initialized Successfully, Socket:{}", socket);
    }

    /**
     * Override run() method for this thread.
     * <p>
     * Takes messages from the message queue and sends them using socketHandler.
     * This method runs in an infinite loop, continuously monitoring the queue
     * for new messages to send.
     */
    @Override
    public void run() {
        System.out.println("======== ClientSendThread info ========");
        socketHandler = new SocketHandler(socket);
        log.info("ClientSendThread: Starts Sending Msg...");

        while (true) {
            // busy waiting
            String message = clientSendMQ.takeMessage();
            log.info("ClientSendThread took msg successfully: {}", message);
            socketHandler.sendMessage(message);
        }
    }
}