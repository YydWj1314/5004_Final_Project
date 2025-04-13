package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * MessageBuffer is a thread-safe utility class used to buffer incoming messages from multiple clients.
 * <p>
 * It stores {@link MessageEntry} objects in a {@link BlockingQueue}, allowing producer-consumer patterns
 * for message handling between threads in a multiplayer networked environment.
 * </p>
 */
public class MessageBuffer {
    private static final Logger log = LoggerFactory.getLogger(MessageBuffer.class);

    // Thread-safe message queue for storing messages with associated sockets
    private static final BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();

    /**
     * Adds a new message entry to the buffer from the specified socket.
     *
     * @param socket  the client's socket from which the message is received
     * @param message the message content
     */
    public static void addMessage(Socket socket, String message) {
        try {
            messageQueue.put(new MessageEntry(socket, message));
            log.info("MessageBuffer Add Message: [{} from {}]", message, socket);
        } catch (InterruptedException e) {
            log.error("MessageBuffer Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // set interrupt flag again
        }
    }

    /**
     * Retrieves and removes the next message entry from the buffer, waiting if necessary
     * until an element becomes available.
     *
     * @return the next {@link MessageEntry} from the queue, or null if interrupted
     */
    public static MessageEntry takeMessage() {
        try {
            return messageQueue.take();
        } catch (InterruptedException e) {
            log.error("MessageBuffer Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    /**
     * A simple data class that represents a message along with its source socket.
     * <p>
     * This is used internally to pair incoming messages with the client they originated from.
     * </p>
     */
    public static class MessageEntry {
        public final Socket socket;
        public final String message;

        /**
         * Constructs a new message entry.
         *
         * @param socket  the socket from which the message originated
         * @param message the message content
         */
        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }
}

