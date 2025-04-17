package model.handtype;

import enumeration.CardRank;
import enumeration.HandRank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Card;
import model.Hand;

/**
 * Represents a Pair: exactly two cards of the same rank
 * The strength of the hand is determined by the rank of the paired cards
 */
public class Pair extends Hand {
  protected List<Card> cards;

  public Pair(List<Card> cards) {
    super(cards, HandRank.PAIR);
  }

  /**
   * Returns the HandRank enumeration for this hand type
   * @return HandRank.PAIR
   */
  @Override
  public HandRank getHandName() {
    return handRank;
  }

  /**
   * Returns the numeric strength value associated with this hand type
   * @return integer value of HandRank.PAIR
   */
  @Override
  public int getHandValue() {
    return handRank.getRankValue();
  }

  /**
   * Returns the two cards that form the pair
   * @return list containing exactly the paired cards
   */
  @Override
  public List<Card> getMainCards() {
    CardRank pairRank = this.getPairRank();
    List<Card> mainCards = new ArrayList<>();
    for (Card card : cards) {
      if (card.getRank() == pairRank) {
        mainCards.add(card);
      }
    }
    return mainCards;
  }

  /**
   * Determines and returns the rank that appears exactly twice in this hand
   * @return the CardRank of the pair, or null if no pair is found
   */
  public CardRank getPairRank() {
    // Count occurrences of each rank
    Map<CardRank, Integer> rankCount = new HashMap<>();
    for (Card card : this.cards) {
      CardRank rank = card.getRank();
      rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
    }
    // Find the rank with exactly two occurrences
    for (Map.Entry<CardRank, Integer> entry : rankCount.entrySet()) {
      if (entry.getValue() == 2) {
        return entry.getKey();
      }
    }
    return null;
  }

  /**
   * Retrieves the one card that is not part of the pair
   * @return the non-paired Card, or null if not found
   */
  public Card getNonPairCard() {
    CardRank pairRank = getPairRank();
    // Return the first card whose rank differs from the pair's rank
    for (Card card : cards) {
      if (pairRank != null && card.getRank() != pairRank) {
        return card;
      }
    }
    return null;
  }
}
