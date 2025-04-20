package utils;

import enumeration.CardSuit;
import enumeration.ExceptionMessage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import model.JavaBean.Card;
import model.exception.EMPTY_HAND;
import model.exception.EmptyDeckException;
import model.exception.HandRankNotFoundException;
import model.exception.InvalidCardsCountException;
import model.handtype.Flush;
import model.JavaBean.Hand;
import model.handtype.HighCard;
import model.handtype.Pair;
import model.handtype.Straight;
import model.handtype.StraightFlush;
import model.handtype.ThreeOfOneKind;

/**
 * Utility class to evaluate a list of 3 cards into the correct hand type
 */
public class HandEvaluator {

  /**
   * Sorting cards by card value in descending order
   *
   * @param cards list of unsorted cards
   */
  public static void sortHandByValue(List<Card> cards) {
    cards.sort(Comparator.comparingInt((Card c) -> c.getRank().getRankValue()).reversed());
  }

  /**
   * Determines which poker hand type matches the given cards, returning the corresponding Hand
   * instance
   *
   * @param handCards a list of exactly 3 cards on hand
   * @return the strongest-matching hand type
   * @throws EmptyDeckException         if handCards is null or empty
   * @throws InvalidCardsCountException if handCards.size() != 3
   * @throws HandRankNotFoundException  if no ranking matches (should be unreachable)
   */
  public static Hand getResult(List<Card> handCards) {
    if (handCards == null || handCards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }
    if (handCards.size() != 3) {
      throw new InvalidCardsCountException(ExceptionMessage.INVALID_CARDS_COUNT.getMessage());
    }

    if (HandEvaluator.isStraightFlush(handCards)) {
      return new StraightFlush(handCards);
    }
    if (HandEvaluator.isThreeOfOneKind(handCards)) {
      return new ThreeOfOneKind(handCards);
    }
    if (HandEvaluator.isFlush(handCards)) {
      return new Flush(handCards);
    }
    if (HandEvaluator.isStraight(handCards)) {
      return new Straight(handCards);
    }
    if (HandEvaluator.isPair(handCards)) {
      return new Pair(handCards);
    }
    if (HandEvaluator.isHighCard(handCards)) {
      return new HighCard(handCards);
    }

    // Defensive: every 3‑card hand must fit one of the above
    throw new HandRankNotFoundException(ExceptionMessage.HAND_RANK_NOT_FOUND.getMessage());
  }


  /**
   * @return true if cards form a sequence (including special A‑3‑2 wheel)
   */
  public static boolean isStraight(List<Card> cards) {
    // check empty
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }
    // check the number of cards
    if (cards.size() != 3) {
      return false;
    }

    // get a list of card sorting in reverse order
    List<Integer> cardsValue = cards.stream()
        .map(card -> card.getRank().getRankValue())
        .sorted(Comparator.reverseOrder())
        .collect(Collectors.toList());

    // wheel: A-3-2 case
    Set<Integer> set = new HashSet<>(cardsValue);
    if (set.containsAll(Arrays.asList(14, 3, 2))) {
      return true;
    }

    // normal: each next is exactly one lower
    return cardsValue.get(0) - cardsValue.get(1) == 1
        && cardsValue.get(1) - cardsValue.get(2) == 1;
  }

  /**
   * @return true if all cards share the same suit
   */
  public static boolean isFlush(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }
    // check the number of cards
    if (cards.size() != 3) {
      return false;
    }

    // map to the set of card suit
    Set<CardSuit> cardSuitSet = cards.stream()
        .map(card -> card.getSuit())
        .collect(Collectors.toSet());

    // check number of elements in the set: = 1 -> is flush, > 1 -> not flush
    if (cardSuitSet.size() > 1) {
      return false;
    }
    return true;
  }

  /**
   * @return true if both straight and flush simultaneously
   */
  public static boolean isStraightFlush(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }
    // order matters—flush & straight each checks size and null
    return isStraight(cards) && isFlush(cards);
  }


  /**
   * @return true if all three cards are the same rank
   */
  public static boolean isThreeOfOneKind(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }
    // check the number of cards
    if (cards.size() != 3) {
      return false;
    }

    // check if there is only one rank
    return cards.stream()
        .map(c -> c.getRank().getRankValue())
        .distinct()
        .count() == 1;
  }

  /**
   * @return true if exactly two cards of one rank (and one of another hand type)
   */
  public static boolean isPair(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }

    if (cards.size() != 3) {
      return false;
    }

    return cards.stream()
        .map(c -> c.getRank().getRankValue())
        .distinct()
        .count() == 2;
  }

  /**
   * @return true if none of the other patterns match
   */
  public static boolean isHighCard(List<Card> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new EMPTY_HAND(ExceptionMessage.EMPTY_HAND.getMessage());
    }

    if (cards.size() != 3) {
      return false;
    }

    return !isStraight(cards)
        && !isFlush(cards)
        && !isThreeOfOneKind(cards)
        && !isPair(cards);
  }

  /**
   * Sorting cards by card value in descending order
   *
   * @param cards list of unsorted cards
   */
  public static void sortCardsByValue(List<Card> cards) {
    cards.sort(new Comparator<Card>() {
      @Override
      public int compare(Card o1, Card o2) {
        return Integer.compare(o2.getRank().getRankValue(), o1.getRank().getRankValue());
      }
    });
  }
}
