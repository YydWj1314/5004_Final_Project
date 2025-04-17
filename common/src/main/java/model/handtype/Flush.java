package model.handtype;

import enumeration.CardSuit;
import enumeration.HandRank;
import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.Hand;

/**
 * Represents a Flush: three cards of the same suit, not consecutive
 */
public class Flush extends Hand {

  /** cards that make up this flush hand */
  protected List<Card> cards;

  public Flush(List<Card> cards) {
    super(cards, HandRank.FLUSH);
  }

  /**
   * Returns the HandRank enumeration value for this flush
   * @return HandRank.FLUSH
   */
  @Override
  public HandRank getHandName() {
    return handRank;
  }

  /**
   * Returns the numeric strength value of this hand type
   * @return the integer value associated with HandRank.FLUSH
   */
  @Override
  public int getHandValue() {
    return handRank.getRankValue();
  }

  /**
   * @return the cards that define this flush
   */
  @Override
  public List<Card> getMainCards() {
    return new ArrayList<>(this.cards);
  }

  /**
   * @return common suit for all cards in this flush hand
   */
  public CardSuit getFlushSuit() {
    return this.cards.get(0).getSuit();
  }
}

