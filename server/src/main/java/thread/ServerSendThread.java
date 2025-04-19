package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ServerReceiveMQ;
import utils.ServerSendMQ;
import utils.SocketHandler;

import java.net.Socket;
/**
 * Thread for sending messages to clients.
 * Takes messages from the queue and sends them to the appropriate client.
 */
public class ServerSendThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerSendThread.class);

    private SocketHandler socketHandler;

    private ServerSendMQ serverSendMQ;


    /**
     * Creates a new thread for sending messages to clients.
     *
     * @param serverSendMQ The message queue containing messages to be sent
     */
    public ServerSendThread(ServerSendMQ serverSendMQ) {
        this.serverSendMQ = serverSendMQ;
        log.info("ServerSendThread Initialized Successfully");
    }


    /**
     * Override run() method for this thread
     * Taking msg from MQ and send msg by socketHandler
     */
    @Override
    public void run() {
        System.out.println("======== ServerSendThread info ========");

        log.info("ServerSendThread: Starts Sending Msg...");

        while (true) {  // busy waiting
            ServerSendMQ.MessageEntry entry = serverSendMQ.takeMessage();
            socketHandler = new SocketHandler(entry.socket);
            log.info("ServerSendThread took msg successfully: {}", entry.message);
            socketHandler.sendMessage(entry.message);
        }
    }
}