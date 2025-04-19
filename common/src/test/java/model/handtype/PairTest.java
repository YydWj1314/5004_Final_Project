package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.JavaBean.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PairTest {

  private Pair pairTest1;

  /**
   * Prepare a Pair of [♣7, ♦5, ♠5] before each test
   */
  @BeforeEach
  void setUp() {
    List<Card> pair1 = new ArrayList<>();
    pair1.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
    pair1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    pair1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(pair1);
    this.pairTest1 = new Pair(pair1);

  }

  /**
   * The hand name must be HAND_RANK.PAIR
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(
        HandRank.PAIR,
        pairTest1.getHandName(),
        "Pair.getHandName() should return HandRank.PAIR"
    );
  }

  /**
   * The numeric value for a pair is 2
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(
        2,
        pairTest1.getHandValue(),
        "Pair.getHandValue() should return 2"
    );
  }

  /**
   * getPairRank() should identify the rank that appears twice: FIVE
   */
  @Test
  void getPairRank() {
    Assertions.assertEquals(
        CardRank.FIVE,
        pairTest1.getPairRank(),
        "getPairRank() should return CardRank.FIVE"
    );
  }

  /**
   * getNonPairCard() should return the single kicker: CLUBS SEVEN
   */
  @Test
  void getNonPairCard() {
    Card nonPairCard = pairTest1.getNonPairCard();
    System.out.println("Non-pair card: " + nonPairCard);
    Assertions.assertEquals(
        new Card(CardSuit.CLUBS, CardRank.SEVEN),
        nonPairCard,
        "getNonPairCard() should return the kicker card ♣7"
    );
  }

  /**
   * getMainCards() should return exactly the two cards forming the pair, [♦5, ♠5]
   */
  @Test
  void getMainCards() {
    List<Card> mainCards = pairTest1.getMainCards();
    List<Card> expected = Arrays.asList(
        new Card(CardSuit.DIAMONDS, CardRank.FIVE),
        new Card(CardSuit.SPADES, CardRank.FIVE)
    );
    Assertions.assertEquals(
        expected,
        mainCards,
        "getMainCards() should return the two paired cards [♦5, ♠5]"
    );
  }
}






