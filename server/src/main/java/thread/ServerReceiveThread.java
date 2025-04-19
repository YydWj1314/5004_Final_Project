package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ServerReceiveMQ;
import utils.SocketHandler;

import java.net.Socket;
/**
 * Thread for receiving messages from a client.
 * Listens for incoming messages and adds them to the message queue.
 */
public class ServerReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);
    private Socket socket;
    private SocketHandler socketHandler;
    private ServerReceiveMQ serverReceiveMQ;

    /**
     * Creates a new thread for receiving messages from a client.
     *
     * @param socket The socket connected to the client
     * @param serverReceiveMQ The message queue for storing received messages
     */
    public ServerReceiveThread(Socket socket, ServerReceiveMQ serverReceiveMQ) {
        this.socket = socket;
        this.serverReceiveMQ = serverReceiveMQ;
        log.info("ClientReceiveThread Initialized Successfully");
    }

    /**
     * Main execution method of the thread.
     * Continuously listens for messages from the client and adds them to the queue.
     */
    @Override
    public void run(){
        System.out.println("======== ServerReceiveThread info ========");
        log.info("ServerReceiveThread Starts Receiving Msg...");
        socketHandler = new SocketHandler(socket);
        while(true){
            String message = socketHandler.receiveMessage();
            log.info("ClientReceiveThread received msg successfully: {}", message);
            serverReceiveMQ.addMessage(socket, message);
        }
    }

}
