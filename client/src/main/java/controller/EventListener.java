package controller;

//import model.CardVO;
//import model.Player;

import model.CardVO;

import java.util.List;

public interface EventListener {

  /**
   * Updates the system text message area with the given message.
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
   * Updates the Card display message area with Image
   * This method will be called when the Card UI needs to be displayed of updated
   *
   * @param CardVOList
   * @param args
   */
  void onCardAreaUpdated(List<CardVO> CardVOList, Object... args);


}