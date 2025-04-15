package model;


import enumeration.CardRank;
import enumeration.CardSuit;

import javax.swing.*;
import java.awt.*;

public class CardVO extends JLabel {
    private CardSuit suit;
    private CardRank rank;
    private boolean isUp;

    public CardVO(){
        this.setSize(105, 150);
    }

    public CardVO(CardSuit suit, CardRank rank, boolean isUp){
        this.suit = suit;
        this.rank = rank;
        this.setOpaque(false);
        this.setVisible(true);
        if(isUp)
            turnUp();
        else
            turnDown();
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public CardRank getRank() {
        return rank;
    }

    public void setRank(CardRank rank) {
        this.rank = rank;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }

    public void turnUp() {
        String cardURL = "pokers/" + rank.getSymbol() + "_of_" + suit.getName() + ".png";
        System.out.println("Loading image from: " + cardURL);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(cardURL));
        if (icon.getIconWidth() == -1) {
            System.err.println("Failed to load card image: " + cardURL);
        } else {
            System.out.println("Card Image loaded successfully!");
        }
        this.setIcon(new ImageIcon(icon.getImage().getScaledInstance(105, 150, Image.SCALE_SMOOTH)));
    }

    public void turnDown() {
        String cardBackURL = "card_back_black.png";
        System.out.println("Loading image from: " + cardBackURL);
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(cardBackURL));
        if (icon.getIconWidth() == -1) {
            System.err.println("Failed to load card back image");
        } else {
            System.out.println("Card back image loaded successfully!");
        }
        this.setIcon(new ImageIcon(icon.getImage().getScaledInstance(105, 150, Image.SCALE_SMOOTH)));
    }

    @Override
    public String toString() {
        return "CardVO{" +
                "suit=" + suit +
                ", rank=" + rank +
                ", isUp=" + isUp +
                '}';
    }
}
