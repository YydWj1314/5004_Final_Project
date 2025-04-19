package controller;

import model.DTO.PlayerRankDTO;
import model.VO.CardVO;
import view.MainFrame;

import java.util.ArrayList;
import java.util.List;

/**
 * ControllerEventListener implements the EventListener interface
 * to handle communication between the controller and view components.
 * <p>
 * This class receives events from the controller and updates
 * the appropriate UI components in the MainFrame.
 */
public class ControllerEventListener implements EventListener {

    /** The main frame of the game that will be updated */
    private final MainFrame mainFrame;

    /**
     * Constructor of the ControllerEventListener.
     * <p>
     * Initializes the listener with a reference to the main frame
     * that will be updated in response to events.
     *
     * @param mainFrame main frame of the game to be updated
     */
    public ControllerEventListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Updates the text area in the main frame with a message.
     * <p>
     * This method is called when there is a text message that
     * needs to be displayed to the user.
     *
     * @param message the message to be displayed
     * @param args optional additional arguments that might be used for formatting
     */
    @Override
    public void onTextAreaUpdated(String message, Object... args) {
        mainFrame.updateTextField(message);
    }

    /**
     * Updates the card area in the main frame with a list of cards.
     * <p>
     * This method is called when the player's cards need to be displayed
     * or updated in the UI.
     *
     * @param CardVOList list of CardVO objects to be displayed
     * @param args optional additional arguments that might be used for formatting
     */
    @Override
    public void onCardAreaUpdated(List<CardVO> CardVOList, Object... args) {
        mainFrame.updateCardArea(CardVOList);
    }

    /**
     * Shows the game result in a dialog.
     * <p>
     * This method is called when the game ends and the results
     * need to be displayed to the player.
     *
     * @param message the result message to be displayed
     * @param args optional additional arguments that might be used for formatting
     */
    @Override
    public void onPlayerResultUpdated(String message, Object... args) {
        mainFrame.showGameResult(this.mainFrame, message);
    }
}