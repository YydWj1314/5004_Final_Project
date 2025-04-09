package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerSendMQ {
    private static final Logger log = LoggerFactory.getLogger(ServerSendMQ.class);

    private static final BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();


    public void addMessage(Socket socket, String message){
        try {
            messageQueue.put(new MessageEntry(socket, message));
            log.info("ServerSendMQ put: message{}, socket {}", message, socket);
        } catch (InterruptedException e) {
            log.error("ServerSendMQ Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    public MessageEntry takeMessage(){
        try {
            ServerSendMQ.MessageEntry entry = messageQueue.take();
            log.info("ServerSendMQ took: message:{}, socket{}", entry.message, entry.socket);
            return entry;
        } catch (InterruptedException e) {
            log.error("ServerSendMQ Take Error, Thread Interrupted ", e);
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
