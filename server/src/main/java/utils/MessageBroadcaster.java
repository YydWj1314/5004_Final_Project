package utils;

import model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageBroadcaster {
  private static final Logger log = LoggerFactory.getLogger(MessageBroadcaster.class);

  /**
   * BroadCast to all players
   *
   * @param playerList
   * @param message
   */
  public static void broadcastMessage(List<Player> playerList, String message) {
    for (Player player : playerList) {
      player.sendMessage(message);
    }
    log.info("📢 Broadcasting Message: {}", message);
  }
}
