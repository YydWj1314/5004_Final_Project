package utils;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientReceiveMQ {

    private static final Logger log = LoggerFactory.getLogger(ClientReceiveMQ.class);

    private final BlockingQueue<String> messageQueue = new LinkedBlockingDeque<>();

    /**
     * @param message
     */
    public void addMessage(String message) {
        try {
            messageQueue.put(message);
            log.info("Message has been put: {}", message);
        } catch (InterruptedException e) {
            log.error("MessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // setting interruption
        }
    }

    /**
     * @return
     */
    public String takeMessage() {
        try {
            String message = messageQueue.take();
            log.info("Message has been taken: {}", message);
            return message;
        } catch (InterruptedException e) {
            log.error("MessageBuffer Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }


}
