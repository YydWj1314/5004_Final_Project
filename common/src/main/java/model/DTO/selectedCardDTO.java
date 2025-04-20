package model.DTO;


import enumeration.CardRank;
import enumeration.CardSuit;

public class selectedCardDTO {
    private int playerId;
    private CardSuit suit;
    private CardRank rank;

    public selectedCardDTO(int playerId, CardSuit suit, CardRank rank) {
        this.playerId = playerId;
        this.suit = suit;
        this.rank = rank;
    }

    // Getters and Setters

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "selectedCardDTO{" +
                "playerId=" + playerId +
                ", suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}

