import controller.GameController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thread.ServerReceiveThread;

/**
 * Main server class that listens for client connections.
 * Creates a new socket and game controller for each client.
 */
public class GameServer {

    /**
     * The IP address where the server will run (localhost)
     */
    private static final String SERVER_IP = "127.0.0.1";

    /**
     * The port number where the server will listen for connections
     */
    private static final int SERVER_PORT = 10087;

    /**
     * Logger for recording server events and errors
     */
    private static final Logger log = LoggerFactory.getLogger(GameServer.class);

    private GameController gameController;

    /**
     * Creates a new game server and starts listening for connections.
     * Handles new clients by creating game controllers for them.
     */
    public GameServer() {
        // Create ServerSocket
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            log.info("Server started on port {}", SERVER_PORT);

            // Initialize game controller
            gameController = new GameController();

            // Continuously accept client connections
            while (true) {
                // Wait for a client to connect
                Socket socket = serverSocket.accept();
                log.info("New client connected: {}", socket.getInetAddress());

                // handle connection
                new Thread(() -> {
                    gameController.handleNewConnection(socket);
                }).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}