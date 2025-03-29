import controller.GameController;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameServer {

  private static final String SERVER_IP = "127.0.0.1";
  private static final int SERVER_PORT = 10087;

  private static final Logger log = LoggerFactory.getLogger(GameServer.class);


  public GameServer() {
    // Create ServerSocket
    try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
      log.info("Server started on port {}", SERVER_PORT);

      // Continuously accept client connections
      while (true) {
        Socket clientSocket = serverSocket.accept(); // Waits for a client
        log.info("New client connected: {}", clientSocket.getInetAddress());

      }
    } catch (IOException e) {
      log.error("Server error: ", e);
    }
  }
}

