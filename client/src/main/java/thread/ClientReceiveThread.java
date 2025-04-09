package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ClientReceiveMQ;
import utils.SocketHandler;

import java.net.Socket;

public class ClientReceiveThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ClientReceiveThread.class);

    private Socket socket;

    private SocketHandler socketHandler;

    private ClientReceiveMQ clientReceiveMQ;

    /**
     * Constructor of thread
     *
     * @param socket socket with target IP and port
     */
    public ClientReceiveThread(Socket socket, ClientReceiveMQ clientReceiveMQ) {
        this.socket = socket;
        this.clientReceiveMQ = clientReceiveMQ;
        log.info("ClientReceiveThread Initialized Successfully, Socket:{}", socket);
    }

    /**
     * Override run() method for this thread
     * Take message from MQ and send msg by socketHandler
     */
    @Override
    public void run() {
        System.out.println("======== ClientReceiveThread info ========");
        socketHandler = new SocketHandler(socket);
        log.info("ClientReceiveThread: Starts Receiving Msg...");
        while (true) {
            String message = socketHandler.receiveMessage();
            log.info("ClientReceiveThread received msg successfully: {}", message);
            clientReceiveMQ.addMessage(socket, message);
        }
    }

}
