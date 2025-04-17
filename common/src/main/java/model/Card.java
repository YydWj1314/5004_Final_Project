package model;

import enumeration.CardRank;
import enumeration.CardSuit;

import java.util.Comparator;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Card {

  private CardSuit suit;
  private CardRank rank;


  /**
   * Constructor of cards
   *
   * @param cardSuit card suit
   * @param cardRank card rank
   */
  public Card(CardSuit cardSuit, CardRank cardRank) {
    this.suit = cardSuit;
    this.rank = cardRank;
  }

  /**
   * @return the suit of this card
   */
  public CardSuit getSuit() {
    return suit;
  }

  /**
   * @return the rank of this card
   */
  public CardRank getRank() {
    return rank;
  }

  public void setSuit(CardSuit suit) {
    this.suit = suit;
  }

  public void setRank(CardRank rank) {
    this.rank = rank;
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
    return suit == card.suit && rank == card.rank;
  }

  /**
   * Returns a hash code value for this card based on its suit and rank.
   *
   * @return a hash code value for this card
   */
  @Override
  public int hashCode() {
    return Objects.hash(suit, rank);
  }

  /**
   * Returns a string representation of this card in the format "SuitRank".
   *
   * @return a string representation of this card
   */
  @Override
  public String toString() {
    return suit + "" + rank;
  }
}




