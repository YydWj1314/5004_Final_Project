package utils;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ClientReceiveMQ provides a thread-safe message queue for receiving messages.
 * <p>
 * This class implements a blocking queue-based message buffer that allows
 * threads to safely add and retrieve messages in a thread-safe manner.
 * It is primarily used for receiving messages from the server and making
 * them available to the client application.
 */
public class ClientReceiveMQ {

    /** Logger for this class */
    private static final Logger log = LoggerFactory.getLogger(ClientReceiveMQ.class);

    /** Thread-safe blocking queue to store messages */
    private final BlockingQueue<String> messageQueue = new LinkedBlockingDeque<>();

    /**
     * Adds a message to the message queue.
     * <p>
     * This method blocks if the queue is full until space becomes available.
     * If the thread is interrupted while waiting, it sets the interrupt flag
     * and logs an error.
     *
     * @param message the message to add to the queue
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
     * Takes a message from the message queue.
     * <p>
     * This method blocks if the queue is empty until a message becomes available.
     * If the thread is interrupted while waiting, it sets the interrupt flag,
     * logs an error, and returns null.
     *
     * @return the message from the queue, or null if interrupted
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
