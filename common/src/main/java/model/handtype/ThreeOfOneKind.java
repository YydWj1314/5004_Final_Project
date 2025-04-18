package model.handtype;

import enumeration.CardRank;
import enumeration.HandRank;
import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.Hand;

/**
 * Represents Three of a Kind: exactly three cards of same rank
 */
public class ThreeOfOneKind extends Hand {

  public ThreeOfOneKind(List<Card> cards) {
    super(cards);

  }

  /**
   * Returns the HandRank enumeration for this hand
   * @return HandRank.THREE_OF_A_KIND
   */
  @Override
  public HandRank getHandName() {
    return HandRank.THREE_OF_A_KIND;
  }


  /**
   * Returns the numeric strength value associated with this hand type
   * @return integer value of HandRank.THREE_OF_A_KIND
   */
  @Override
  public int getHandValue() {
    return HandRank.THREE_OF_A_KIND.getRankValue();
  }


  /**
   * @return all cards comprising this straight flush
   */
  @Override
  public List<Card> getMainCards() {
    return new ArrayList<>(this.cards);
  }


  /**
   * Retrieves the rank shared by all three cards in this hand
   * @return the CardRank that appears three times
   */
  public CardRank getThreeOfOneKindRank() {
    return this.cards.get(0).getRank();
  }

}
