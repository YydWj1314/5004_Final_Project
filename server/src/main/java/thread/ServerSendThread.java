package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ServerSendMQ;
import utils.SocketHandler;

import java.net.Socket;

public class ServerSendThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerSendThread.class);

    private Socket socket;

    private SocketHandler socketHandler;

    private ServerSendMQ serverSendMQ;


    /**
     * Constructor of thread
     *
     * @param socket socket with target IP and port
     * @param serverSendMQ
     */
    public ServerSendThread(Socket socket, ServerSendMQ serverSendMQ) {
        this.socket = socket;
        this.serverSendMQ = serverSendMQ;
        log.info("ServerSendThread Initialized Successfully, Socket:{}", socket);
    }

//    public void sendMessage(String newMessage) {
//        if (newMessage != null) {
//            socketHandler.sendMessage(newMessage);
//            log.info("Message send successfully: {}", newMessage);
//        }
//    }

    /**
     * Override run() method for this thread
     * Taking msg from MQ and send msg by socketHandler
     */
    @Override
    public void run() {
        System.out.println("======== ServerSendThread info ========");
        socketHandler = new SocketHandler(socket);
        log.info("ServerSendThread: Starts Sending Msg...");

        while (true) {  // busy waiting
            String message = serverSendMQ.takeMessage().message;
            log.info("ServerSendThread took msg successfully: {}", message);
            socketHandler.sendMessage(message);
        }
    }
}