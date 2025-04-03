package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.SocketHandler;

import java.net.Socket;

public class ClientReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ClientReceiveThread.class);
    private Socket socket;
    private SocketHandler socketHandler;

    /**
     * Constructor
     *
     * @param socket
     */
    public ClientReceiveThread(Socket socket) {
        this.socket = socket;
        log.info("ClientReceiveThread Initialized Successfully, Socket:{}", socket);
    }

    @Override
    public void run(){
        socketHandler = new SocketHandler(socket);
        log.info("ClientReceiveThread: Starts Receiving Msg...");

        while(true){
            String message = socketHandler.receiveMessage();
            log.info("ClientReceiveThread: receive msg successfully: {}", message);

        }
    }

}
