package controller;

//import model.CardVO;
//import model.Player;

import java.util.List;

public interface ClientControllerListener {

  /**
   * Updates the system message area with the given message.
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
   * This method is called when the game is triggered to start. It allows the listener (typically
   * the UI) to take action when the game begins, such as enabling game controls or displaying a
   * "game started" message.
   */
  void onGameStart();
}