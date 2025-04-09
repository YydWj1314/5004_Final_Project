package utils;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerReceiveMQ {
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveMQ.class);

    private BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();

    private ServerReceiveMQ serverReceiveMQ;

    /**
     * @param socket
     * @param message
     */
    public void addMessage(Socket socket, String message){
        try {
            messageQueue.put(new MessageEntry(socket, message));
            log.info("ServerReceiveMQ put: message{}, socket {}", message, socket);
        } catch (InterruptedException e) {
            log.error("ServerReceiveMQ Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    /**
     * @return
     */
    public MessageEntry takeMessage(){
        try {
            MessageEntry entry = messageQueue.take();
            log.info("ServerReceiveMQ took: {}", entry);
            return entry;
        } catch (InterruptedException e) {
            log.error("ServerReceiveMQ Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     *
     */
    public static class MessageEntry {
        public final Socket socket;
        public final String message;

        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }

}
