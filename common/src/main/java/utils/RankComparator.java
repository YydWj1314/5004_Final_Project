package utils;

import enumeration.CardRank;
import enumeration.ExceptionMessage;
import enumeration.HandRank;
import java.util.Comparator;
import java.util.List;
import model.Card;
import model.Hand;
import model.exception.CardNotFoundException;
import model.exception.HandRankNotFoundException;
import model.handtype.Pair;

/**
 * Comparator for two player with 3‑card hands represented as List<Card>
 * First orders by HandRank, then tiebreaks within equal rank
 */

public class RankComparator implements Comparator<List<Card>> {
  @Override
  public int compare(List<Card> o1, List<Card> o2) {
    Hand hand1 = HandEvaluator.getResult(o1);
    Hand hand2 = HandEvaluator.getResult(o2);

    if (hand1 == null || hand2 == null) {
      throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
    }

    // Different ranks: higher HandValue wins
    int handValue1 = hand1.getHandValue();
    System.out.println("handValue1:" + hand1 + " " + hand1.getHandName() + " " + handValue1);
    int handValue2 = hand2.getHandValue();
    System.out.println("handValue2:" + hand2 + " " + hand2.getHandName() + " " + handValue2);

    if (handValue1 != handValue2) {
      // descending: stronger hand first
      return Integer.compare(handValue2, handValue1);
    }

    // Same rank: delegate to detailed breaker
    return compareSameRank(o1, o2);

  }


/**
 * Tiebreak logic when both hands share the same HandRank
 */
  private int compareSameRank(List<Card> o1, List<Card> o2) {
    Hand hand1 = HandEvaluator.getResult(o1);
    Hand hand2 = HandEvaluator.getResult(o2);
    HandRank rank = hand1.getHandName();

    switch (rank) {
      case STRAIGHT_FLUSH, THREE_OF_A_KIND, FLUSH, STRAIGHT -> {
        // 1. StraightFLush, ThreeOfKind, Flush, Straight:
        //    sort by value in descending order and compare the highest card only
        HandEvaluator.sortHandByValue(hand1.getCards());
        HandEvaluator.sortHandByValue(hand2.getCards());

        CardRank highestRank1 = hand1.getCards().get(0).getRank();
        CardRank highestRank2 = hand2.getCards().get(0).getRank();
        return highestRank2.compareTo(highestRank1);
      }

      case PAIR -> {
        // 2. Pair:
        //    (1)compare the rank of the pair,
        //    (2)if not the same
        //    (3)if same, then compare the rank of the rest card
        Pair pairHand1 = (Pair) hand1;
        Pair pairHand2 = (Pair) hand2;

        // compare the pair ranks first
        int pairCmp = pairHand2.getPairRank().compareTo(pairHand1.getPairRank());
        if (pairCmp != 0) return pairCmp;

        // then compare the single non‑pair card
        Card non1 = pairHand1.getNonPairCard();
        Card non2 = pairHand2.getNonPairCard();
        if (non1 == null || non2 == null) {
          throw new CardNotFoundException(ExceptionMessage.CARD_NOT_FOUND.getMessage());
        }
        return non2.getRank().compareTo(non1.getRank());
      }

      case HIGH_CARD -> {
        // 3. HighCard: sort the cards and compare each card in descending order
        List<Card> cards1 = hand1.getCards();
        List<Card> cards2 = hand2.getCards();

        HandEvaluator.sortHandByValue(cards1);
        HandEvaluator.sortHandByValue(cards2);

        for (int i = 0; i < cards2.size(); i++) {
          int diff = cards2.get(i).getRank()
              .compareTo(cards1.get(i).getRank());
          if (diff != 0) {
            return diff;
          }
        }
        return 0;
      }
      default -> throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
    }
  }
}

