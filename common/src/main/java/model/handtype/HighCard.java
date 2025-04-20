package model.handtype;

import enumeration.HandRank;
import java.util.List;
import model.JavaBean.Card;
import model.JavaBean.Hand;
import utils.HandEvaluator;

/**
 * Represents a High Card: hand’s strength is determined solely by its highest card
 */
public class HighCard extends Hand {

  public HighCard(List<Card> cards) {
    super(cards);
  }

  /**
   * Returns the type of this hand
   *
   * @return HandRank.HIGH_CARD
   */
  @Override
  public HandRank getHandName() {
    return HandRank.HIGH_CARD;
  }

  /**
   * Returns the numeric strength value of this hand type
   *
   * @return integer value associated with HandRank.HIGH_CARD
   */
  @Override
  public int getHandValue() {
    return HandRank.HIGH_CARD.getRankValue();
  }


  /**
   * Identifies the “main” card of hand cards, the single highest card The cards list is first
   * sorted by rank, then the top card is returned
   *
   * @return a singleton list containing the highest‑ranked card
   */
  @Override
  public List<Card> getMainCards() {
    // Sort cards so the highest‑value card ends up at index 0
    HandEvaluator.sortHandByValue(this.cards);
    // Return only the highest card in a new List
    return List.of(this.cards.get(0));
  }
}
