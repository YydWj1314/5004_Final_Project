package utils;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerReceiveMQ {
    private static final Logger log = LoggerFactory.getLogger(ServerReceiveMQ.class);

    // A thread-safe queue for storing incoming messages from clients
    private BlockingQueue<MessageEntry> messageQueue = new LinkedBlockingDeque<>();

    private ServerReceiveMQ serverReceiveMQ;

    /**
     * Adds a message to the message queue.
     *
     * This method stores the message and the corresponding socket in a MessageEntry
     * and puts it into the message queue for processing.
     *
     * @param socket The socket of the client sending the message.
     * @param message The message sent by the client.
     */
    public void addMessage(Socket socket, String message) {
        try {
            // Put the new message entry into the message queue
            messageQueue.put(new MessageEntry(socket, message));
            log.info("ServerReceiveMQ put: message{}, socket {}", message, socket);
        } catch (InterruptedException e) {
            // Log error if the thread is interrupted while adding a message
            log.error("ServerReceiveMQ Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /**
     * Takes a message from the queue.
     *
     * This method retrieves and removes the head of the message queue. If the queue is empty,
     * the thread will block until a message is available.
     *
     * @return The MessageEntry containing the socket and message, or null if interrupted.
     */
    public MessageEntry takeMessage() {
        try {
            // Take a message entry from the queue
            MessageEntry entry = messageQueue.take();
            log.info("ServerReceiveMQ took: {}", entry);
            return entry;
        } catch (InterruptedException e) {
            // Log error if the thread is interrupted while taking a message
            log.error("ServerReceiveMQ Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return null;
        }
    }

    /**
     * A container class for storing a message and the corresponding socket.
     */
    public static class MessageEntry {
        public final Socket socket;  // The socket associated with the message
        public final String message; // The message content

        /**
         * Constructor to initialize a new MessageEntry.
         *
         * @param socket The socket from which the message was received.
         * @param message The message content.
         */
        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }
}

