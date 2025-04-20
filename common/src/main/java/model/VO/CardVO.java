package model.VO;
import enumeration.CardRank;
import enumeration.CardSuit;

import javax.swing.*;
import java.awt.*;

/**
 * CardVO (View Object) represents a visual card component in the GUI.
 * It extends JLabel and can display either the front or back image of a card
 * depending on its state (face-up or face-down).
 */
public class CardVO extends JLabel {
    private CardSuit suit;       // The suit of the card (e.g., Spades, Hearts)
    private CardRank rank;       // The rank of the card (e.g., Ace, King)
    private boolean isUp;        // Whether the card is currently face-up
    private boolean isSelected;  // Whether the card is currently selected (e.g., for user interaction)
    private int originalY;       // Stores original Y position (used for animations like card selection movement)

    public CardVO(){
        this.setSize(105, 150);
    }

    /**
     * Constructor to create a card with suit, rank, and face-up status.
     *
     * @param suit the card's suit
     * @param rank the card's rank
     * @param isUp whether the card is face-up
     */
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

    // Getters and Setters
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    /**
     * Save the original Y-coordinate of the card's position.
     * This can be used for UI animations like raising/lowering selected cards.
     *
     * @param y the Y position to save
     */
    public void saveOriginalY(int y) {
        this.originalY = y;
    }

    public int getOriginalY() {
        return originalY;
    }


    /**
     * Turns the card face-up by setting the icon to its front image.
     * Loads the image dynamically based on rank and suit.
     */
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

    /**
     * Turns the card face-down by setting the icon to the card back image.
     */
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
