package controller;

import model.DTO.PlayerRankDTO;
import model.VO.CardVO;
import view.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class ControllerEventListener implements EventListener {
    private final MainFrame mainFrame;

    /**
     * Constructor of listener
     *
     * @param mainFrame main frame of the game
     */
    public ControllerEventListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void onTextAreaUpdated(String message, Object... args) {
        mainFrame.updateTextField(message);
    }

    @Override
    public void onCardAreaUpdated(List<CardVO> CardVOList, Object... args) {
        mainFrame.updateCardArea(CardVOList);
    }

    @Override
    public void onPlayerResultUpdated(String message, Object... args) {
        mainFrame.showGameResult(this.mainFrame, message);
    }



}
