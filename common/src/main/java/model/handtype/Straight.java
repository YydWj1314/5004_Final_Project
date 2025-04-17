package model.handtype;

import enumeration.ExceptionMessage;
import enumeration.HandRank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Card;
import model.Hand;
import model.exception.CardNotFoundException;
import utils.HandEvaluator;

/**
 * Represents a Straight: three consecutive cards of mixed suits
 */
public class Straight extends Hand {
  protected List<Card> cards;

  public Straight(List<Card> cards) {
    super(cards, HandRank.STRAIGHT);
  }

  /**
   * Returns the HandRank for this type
   * @return HandRank.STRAIGHT
   */
  @Override
  public HandRank getHandName() {
    return handRank;
  }

  /**
   * Returns the numeric strength of this hand type
   * @return integer value associated with HandRank.STRAIGHT
   */
  @Override
  public int getHandValue() {
    return handRank.getRankValue();
  }

  /**
   * Returns all cards comprising the straight
   * @return shallow copy of the cards in this straight
   */
  @Override
  public List<Card> getMainCards() {
    return new ArrayList<>(this.cards);
  }

  /**
   * Retrieves the highest‐ranked card in the straight sequence.
   * Handles the special “wheel” case (A–2–3) where the 2 is the high card
   * @return the highest card in the straight
   * @throws CardNotFoundException if the expected card cannot be found
   */
  public Card getHighestCard(){
    HandEvaluator.sortHandByValue(this.cards);

    // Extract numeric values, get the list of value: A-3-2 --> 14-3-2
    List<Integer> cardValues = cards.stream()
        .map(card -> card.getRank().getRankValue())
        .collect(Collectors.toList());

    // Special wheel case: A-3-2 should treat '2' as highest
    if (cardValues.containsAll(List.of(14, 3, 2))){
      return cards.stream()
          .filter(card -> card.getRank().getRankValue() == 2)
          .findFirst()
          .orElseThrow(() -> new CardNotFoundException(ExceptionMessage.CARD_NOT_FOUND.getMessage()));
    }
    // Normal case: first element in sorted list(descending) is highest
    return cards.get(0);
  }
}
