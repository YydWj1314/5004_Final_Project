package utils;

import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageBroadcaster {
  private static final Logger log = LoggerFactory.getLogger(MessageBroadcaster.class);
  private ServerSendMQ serverSendMQ;

  public MessageBroadcaster(ServerSendMQ serverSendMQ) {
    this.serverSendMQ = serverSendMQ;
  }

  /**
   * BroadCast to all clients
   *
   * @param playerList
   * @param message
   */
  public void broadcastMessage(List<Player> playerList, String message) {
    for (Player player : playerList) {
        serverSendMQ.addMessage(player.getSocket(), message);
    }
    log.info("📢 Broadcasting Message: {}", message);
  }
}
