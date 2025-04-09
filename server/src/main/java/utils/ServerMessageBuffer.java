package utils;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerMessageBuffer {
    private static final Logger log = LoggerFactory.getLogger(ServerMessageBuffer.class);

    private static final BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();


    public static void addMessage(Socket socket, String message){
        try {

            messageQueue.put(new MessageEntry(socket, message));
            log.info("ServerMessageBuffer Add Message: [{} from {}]", message, socket);
        } catch (InterruptedException e) {
            log.error("ServerMessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    public static MessageEntry takeMessage(){
        try {

            return messageQueue.take();
        } catch (InterruptedException e) {
            log.error("ServerMessageBuffer Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public static class MessageEntry {
        public final Socket socket;
        public final String message;

        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }

}
