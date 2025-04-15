package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ServerReceiveMQ;
import utils.SocketHandler;

import java.net.Socket;

public class ServerReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);
    private Socket socket;
    private SocketHandler socketHandler;
    private ServerReceiveMQ serverReceiveMQ;

    /**
     * Constructor
     *
     * @param socket
     * @param serverReceiveMQ
     */
    public ServerReceiveThread(Socket socket, ServerReceiveMQ serverReceiveMQ) {
        this.socket = socket;
        this.serverReceiveMQ = serverReceiveMQ;
        log.info("ClientReceiveThread Initialized Successfully");
    }

    /**
     *
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
