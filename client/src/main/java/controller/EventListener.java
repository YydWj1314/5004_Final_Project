package controller;

//import model.VO.CardVO;
//import model.JavaBean.Player;
import model.DTO.PlayerRankDTO;
import model.VO.CardVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface defining the event listener methods for handling game events.
 * <p>
 * This interface establishes the contract for handling various game events
 * such as text updates, card display updates, and game result notifications.
 * Implementations will connect these events to the appropriate UI components.
 */
public interface EventListener {

  /**
   * Updates the system text message area with the given message.
   * <p>
   * This method will be called when the server sends messages that need to be displayed in the
   * system message area, such as player actions, game status updates, or other important
   * information.
   *
   * @param message The message to be displayed in the system message area.
   * @param args Additional arguments that can be used for dynamic message formatting or other
   *             purposes (optional).
   */
  void onTextAreaUpdated(String message, Object... args);

  /**
   * Updates the Card display area with card images.
   * <p>
   * This method will be called when the Card UI needs to be displayed or updated,
   * such as when the player is dealt new cards or when cards are played.
   *
   * @param CardVOList List of CardVO objects containing card information to be displayed
   * @param args Additional arguments that can be used for formatting or other purposes (optional)
   */
  void onCardAreaUpdated(List<CardVO> CardVOList, Object... args);

  /**
   * Updates the game result display with final outcome information.
   * <p>
   * This method will be called at the end of a game to show the results,
   * including player rankings, scores, and other game outcome information.
   *
   * @param message The result message to be displayed to the player
   * @param args Additional arguments that can be used for formatting or other purposes (optional)
   */
  void onPlayerResultUpdated(String message, Object... args);
}