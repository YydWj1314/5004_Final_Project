import controller.GameController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple game server that listens for client connections.
 * This server runs on localhost and waits for clients to connect.
 */
public class GameServer {

  /** The IP address where the server will run (localhost) */
  private static final String SERVER_IP = "127.0.0.1";

  /** The port number where the server will listen for connections */
  private static final int SERVER_PORT = 10087;

  /** Logger for recording server events and errors */
  private static final Logger log = LoggerFactory.getLogger(GameServer.class);

  /**
   * Creates a new game server that listens for client connections.
   * The server will keep running until it's manually stopped.
   */
  public GameServer() {
    // Create ServerSocket
    try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
      log.info("Server started on port {}", SERVER_PORT);

      // Continuously accept client connections
      while (true) {
        // Wait for a client to connect
        Socket clientSocket = serverSocket.accept();
        log.info("New client connected: {}", clientSocket.getInetAddress());

      }
    } catch (IOException e) {
      log.error("Server error: ", e);
    }
  }
}