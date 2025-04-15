package utils;

import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageBroadcaster {
  private static final Logger log = LoggerFactory.getLogger(MessageBroadcaster.class);
  private ServerSendMQ serverSendMQ;

  /**
   * Constructor to initialize the MessageBroadcaster with a reference to ServerSendMQ.
   *
   * @param serverSendMQ An instance of ServerSendMQ that handles sending messages to clients.
   */
  public MessageBroadcaster(ServerSendMQ serverSendMQ) {
    this.serverSendMQ = serverSendMQ;
  }

  /**
   * Broadcasts a message to all connected players (clients).
   *
   * This method iterates over the list of players and sends the provided message to each player's socket.
   * It uses the ServerSendMQ to queue the message for sending.
   *
   * @param playerList A list of players to which the message will be broadcasted.
   * @param message The message to be broadcasted to all players.
   */
  public void broadcastMessage(List<Player> playerList, String message) {
    log.info("Broadcasting Message: {}", message);
    // Iterate over all players and send the message to their respective sockets
    for (Player player : playerList) {
      serverSendMQ.addMessage(player.getSocket(), message);
    }

  }
}

