package model.DTO;

import model.JavaBean.Card;

import java.util.List;


/**
 * Data Transfer Object (DTO) for player information in the game.
 * Used to transfer player data (such as ID, name, and current hand)
 * between server and client without exposing internal game logic.
 */
public class PlayerDTO {

    // Unique identifier for the player
    private int playerId;

    // Name of the player
    private String playerName;

    // List of cards currently held by the player (their hand)
    private List<Card> playerHand;

    /**
     * Constructor to initialize the player DTO with ID, name, and hand.
     *
     * @param playerId   Unique player ID
     * @param playerName Player's display name
     * @param playerHand List of cards in the player's hand
     */
    public PlayerDTO(int playerId, String playerName, List<Card> playerHand) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerHand = playerHand;
    }


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(List<Card> playerHand) {
        this.playerHand = playerHand;
    }

}

