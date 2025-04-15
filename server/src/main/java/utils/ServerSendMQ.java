package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerSendMQ {
    private static final Logger log = LoggerFactory.getLogger(ServerSendMQ.class);

    // A thread-safe queue for storing messages to be sent to clients
    private final BlockingQueue<MessageEntry> messageQueue;


    public ServerSendMQ() {
        this.messageQueue = new LinkedBlockingDeque<>();
    }

    /**
     * Adds a message to the message queue for sending.
     *
     * This method stores the message and the corresponding socket in a MessageEntry
     * and puts it into the message queue to be processed by the sender thread.
     *
     * @param socket The socket of the client that will receive the message.
     * @param message The message to be sent to the client.
     */
    public void addMessage(Socket socket, String message) {
        try {
            // Put the new message entry into the message queue
            messageQueue.put(new MessageEntry(socket, message));
            log.info("Message has been put: message{}, socket {}", message, socket);
        } catch (InterruptedException e) {
            // Log error if the thread is interrupted while adding a message
            log.error("ServerSendMQ Put Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // Restore the interrupted status
        }
    }

    /**
     * Takes a message from the queue to be sent.
     *
     * This method retrieves and removes the head of the message queue. If the queue is empty,
     * the thread will block until a message is available to send.
     *
     * @return The MessageEntry containing the socket and message, or null if interrupted.
     */
    public MessageEntry takeMessage() {
        try {
            // Take a message entry from the queue
            MessageEntry entry = messageQueue.take();
            log.info("Message has been taken: message:{}, socket{}", entry.message, entry.socket);
            return entry;
        } catch (InterruptedException e) {
            // Log error if the thread is interrupted while taking a message
            log.error("ServerSendMQ Take Error, Thread Interrupted ", e);
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return null;
        }
    }

    /**
     * A container class for storing a message and its associated socket.
     */
    public static class MessageEntry {
        public final Socket socket;  // The socket associated with the message
        public final String message; // The message content

        /**
         * Constructor to initialize a new MessageEntry.
         *
         * @param socket The socket that will receive the message.
         * @param message The message content to be sent.
         */
        public MessageEntry(Socket socket, String message) {
            this.socket = socket;
            this.message = message;
        }
    }
}

