package controller;

//import model.VO.CardVO;
//import model.JavaBean.Player;

import model.DTO.PlayerRankDTO;
import model.VO.CardVO;

import java.util.ArrayList;
import java.util.List;

public interface EventListener {

  /**
   * Updates the system text message area with the given message
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
   * This method will be called when the Card UI needs to be displayed or updated
   *
   * @param CardVOList
   * @param args
   */
  void onCardAreaUpdated(List<CardVO> CardVOList, Object... args);


  /**
   * @param message
   * @param args
   */
  void onPlayerResultUpdated(String message, Object... args);
}