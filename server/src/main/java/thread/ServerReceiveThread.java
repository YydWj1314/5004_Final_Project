package thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.MessageBuffer;
import utils.ServerMessageBuffer;
import utils.SocketHandler;

import java.net.Socket;

public class ServerReceiveThread extends Thread{
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveThread.class);
    private Socket socket;
    private SocketHandler socketHandler;

    /**
     * Constructor
     *
     * @param socket
     */
    public ServerReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        socketHandler = new SocketHandler(socket);
        log.info("Server ReceiveThread Starts Receiving Msg...");

        while(true){
            String message = socketHandler.receiveMessage();
            log.info("Message receive successfully: {}", message);
            ServerMessageBuffer.addMessage(socket, message);
            log.info("Adding message successfully to the buffer: {}", message);

        }
    }

}
