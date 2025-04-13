package model;

import enumeration.CardRank;
import enumeration.CardSuit;

import java.util.Comparator;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Card implements Comparable<Card> {

  private CardSuit cardSuit;
  private CardRank cardRank;
  private Image cardImage;  // Image to represent the card's visual

  // Coordinates for rendering the card on the panel
  private int x, y;

  /**
   * Constructs a new Card with the specified suit and rank, and loads its image.
   *
   * @param cardSuit the suit of the card
   * @param cardRank the rank of the card
   */
  public Card(CardSuit cardSuit, CardRank cardRank, int x, int y) {
    this.cardSuit = cardSuit;
    this.cardRank = cardRank;
    this.x = x;
    this.y = y;

    // Load the card image based on the suit and rank
    this.cardImage = loadCardImage(cardSuit, cardRank);
  }
  public Card(CardSuit suit, CardRank rank) {
    this.cardSuit = suit;
    this.cardRank = rank;
    this.cardImage = loadCardImage(cardSuit, cardRank);
    this.x = 0; // default value
    this.y = 0;
  }

  /**
   * Loads the image for the card based on its suit and rank.
   *
   * @param cardSuit the suit of the card
   * @param cardRank the rank of the card
   * @return the image representing the card
   */
  private Image loadCardImage(CardSuit cardSuit, CardRank cardRank) {
    String imagePath = "/pokers/" + cardRank.toString() + "_of_" + cardSuit.getName().toLowerCase() + ".png";
    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
    return icon.getImage();  // Return the card image
  }

  /**
   * @return the suit of this card
   */
  public CardSuit getCardSuit() {
    return cardSuit;
  }

  /**
   * @return the rank of this card
   */
  public CardRank getCardRank() {
    return cardRank;
  }

  /**
   * @return the x coordinate of this card for rendering
   */
  public int getX() {
    return x;
  }

  /**
   * @return the y coordinate of this card for rendering
   */
  public int getY() {
    return y;
  }

  /**
   * Renders the card image at the specified x and y coordinates.
   *
   * @param g the Graphics object used for drawing
   */
  public void render(Graphics g) {
    g.drawImage(cardImage, x, y, null);  // Draw the card at its position
  }

  /**
   * Compares this card with the specified card for order.
   *
   * @param otherCard the card to be compared
   * @return a negative integer, zero, or a positive integer as this card is less than, equal to,
   *         or greater than the specified card
   */
  @Override
  public int compareTo(Card otherCard) {
    return Comparator.comparing(Card::getCardRank)
        .thenComparing(Card::getCardSuit)
        .compare(this, otherCard);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare
   * @return true if this card is equal to the provided object; false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return cardSuit == card.cardSuit && cardRank == card.cardRank;
  }

  /**
   * Returns a hash code value for this card based on its suit and rank.
   *
   * @return a hash code value for this card
   */
  @Override
  public int hashCode() {
    return Objects.hash(cardSuit, cardRank);
  }

  /**
   * Returns a string representation of this card in the format "SuitRank".
   *
   * @return a string representation of this card
   */
  @Override
  public String toString() {
    return cardSuit + "" + cardRank;
  }
}




