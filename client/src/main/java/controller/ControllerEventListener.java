package controller;

import model.CardVO;
import view.MainFrame;

import java.util.List;

public class ControllerEventListener implements EventListener {
    private final MainFrame mainFrame;

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

}
