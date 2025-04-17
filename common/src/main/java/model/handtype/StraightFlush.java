package model.handtype;

import enumeration.CardSuit;
import enumeration.HandRank;
import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.Hand;

/**
 * Represents a Straight Flush: three consecutive cards of the same suit
 * This is the strongest hand in this three‑card case
 */
public class StraightFlush extends Hand {
  protected List<Card> cards;

  public StraightFlush(List<Card> cards) {
    super(cards, HandRank.STRAIGHT_FLUSH);
  }

  /**
   * Returns the HandRank enumeration for this hand type
   * @return HandRank.STRAIGHT_FLUSH
   */
  @Override
  public HandRank getHandName() {
    return handRank;
  }

  /**
   * Returns the numeric strength value associated with this hand type
   * @return integer value of HandRank.STRAIGHT_FLUSH
   */
  @Override
  public int getHandValue() {
    return handRank.getRankValue();
  }

  /**
   * @return all cards comprising this straight flush
   */
  @Override
  public List<Card> getMainCards() {
    return new ArrayList<>(this.cards);
  }

  /**
   * get the suit common to all cards in this straight flush
   * @return the CardSuit shared by each card
   */
  public CardSuit getStraightFlushSuit() {
    return cards.get(0).getSuit();
  }

}
