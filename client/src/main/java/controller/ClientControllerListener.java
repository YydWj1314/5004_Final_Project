package controller;

//import model.CardVO;
//import model.Player;

import java.util.List;

public interface ClientControllerListener {
//  void onCardVOUpdated(List<CardVO> cardVOList);
  void onTextAreaUpdated(String message, Object... args);
}
