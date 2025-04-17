package model;

import enumeration.HandRank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abstract base class representing a poker hand
 * Implements Comparable to allow comparing hands by their strength
 */
public abstract class Hand implements Comparable<Hand> {
  // cards making up this hand
  protected List<Card> cards;
  // types of cards
  protected HandRank handRank;

  /**
   * Constructs a Hand with the given cards and hand rank
   * Subclasses should call this to initialize their card list and rank
   * @param cards    list of Card objects that form this hand
   * @param handRank the HandRank enum describing the type of hand
   */
  protected Hand(List<Card> cards, HandRank handRank) {
    this.cards = cards;
    this.handRank = handRank;
  }


  /**
   * @return the list of all cards in hand
   */
  public List<Card> getCards() {
    return Collections.unmodifiableList(cards);
  }

  /**
   * Returns the HandRank enum value for this hand type(Straight, Pair...)
   * Subclasses must implement to return their specific rank
   * @return the HandRank of this hand
   */
  public abstract HandRank getHandName();

  /**
   * Returns the numeric strength value of this hand
   * Often derived from HandRank.getRankValue()
   * @return integer representing this hand's strength
   */
  public abstract int getHandValue();

  /**
   * Returns the "main" cards that define this hand type
   * For example, a Pair returns the two cards of the same rank
   * @return a List of Card objects that are key to this hand
   */
  public abstract List<Card> getMainCards();

  /**
   * Compares this hand to another by their numerical hand values
   * @param other the Hand to compare against
   * @return negative if this is weaker, positive if stronger, zero if equal
   */
  @Override
  public int compareTo(Hand other) {
    // Simple compare by handValue
    return Integer.compare(this.getHandValue(), other.getHandValue());
  }

  /**
   * Two hands are equal if their card lists are equal (same cards)
   * @param o the object to compare with
   * @return true if both are Hand instances with identical cards
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Hand hand = (Hand) o;
    return Objects.equals(getCards(), hand.getCards());
  }

  /**
   * Hash code based on the cards in the hand
   * @return hash code of the card list
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(getCards());
  }

/**
 * Returns a simple string representation showing the cards
 */
  @Override
  public String toString() {
    return "Hands:" + cards;
  }
}
