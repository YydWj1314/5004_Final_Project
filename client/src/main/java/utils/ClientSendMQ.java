package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ClientSendMQ {
    private static final Logger log = LoggerFactory.getLogger(ClientSendMQ.class);

    private BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();


    /**
     * @param socket
     * @param message
     */
    public void addMessage(Socket socket, String message){
        try {
            messageQueue.put(new MessageEntry(socket, message));
            log.info("ClientSendMQ put: message{}, socket {}", message, socket);
        } catch (InterruptedException e) {
            log.error("MessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    /**
     * @return
     */
    public MessageEntry takeMessage(){
        try {
            MessageEntry entry = messageQueue.take();
            log.info("ClientSendMQ took: message:{}, socket{}", entry.message, entry.socket);
            return entry;
        } catch (InterruptedException e) {
            log.error("MessageBuffer Take Error, Thread Interrupted ", e);
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
