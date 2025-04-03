package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MessageBuffer {
    private static final Logger log = LoggerFactory.getLogger(MessageBuffer.class);

    private static final BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();


    public static void addMessage(Socket socket, String message){
        try {
            // 方法一
            messageQueue.put(new MessageEntry(socket, message));  // 方法一
            log.info("MessageBuffer Add Message: [{} from {}]", message, socket);
        } catch (InterruptedException e) {
            log.error("MessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    public static MessageEntry takeMessage(){
        try {
            // 方法二
            return messageQueue.take();
        } catch (InterruptedException e) {
            log.error("MessageBuffer Take Error, Thread Interrupted ", e);
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
