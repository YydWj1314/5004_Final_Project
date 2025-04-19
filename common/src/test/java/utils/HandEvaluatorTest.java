package utils;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.JavaBean.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HandEvaluatorTest {

  /**
   * Verifies that getResult(...) correctly identifies hand types: STRAIGHT_FLUSH, THREE_OF_A_KIND,
   * and STRAIGHT.
   */
  @Test
  void handEvaluator() {
    List<Card> straightFlush1 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.SPADES, CardRank.KING),
        new Card(CardSuit.SPADES, CardRank.ACE)
    );

    List<Card> straightFlush2 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.SPADES, CardRank.KING),
        new Card(CardSuit.SPADES, CardRank.ACE)
    );

    List<Card> threeOfAKind1 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.JACK),
        new Card(CardSuit.DIAMONDS, CardRank.JACK),
        new Card(CardSuit.CLUBS, CardRank.JACK)
    );

    List<Card> straight1 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.EIGHT),
        new Card(CardSuit.DIAMONDS, CardRank.NINE),
        new Card(CardSuit.CLUBS, CardRank.SEVEN)
    );

    // STRAIGHT_FLUSH expected
    Assertions.assertEquals(
        HandRank.STRAIGHT_FLUSH,
        HandEvaluator.getResult(straightFlush1).getHandName(),
        "Should detect a straight flush"
    );
    // THREE_OF_A_KIND expected
    Assertions.assertEquals(
        HandRank.THREE_OF_A_KIND,
        HandEvaluator.getResult(threeOfAKind1).getHandName(),
        "Should detect three of a kind"
    );
    // STRAIGHT expected
    Assertions.assertEquals(
        HandRank.STRAIGHT,
        HandEvaluator.getResult(straight1).getHandName(),
        "Should detect a straight"
    );
  }

  /**
   * Tests isStraight(...) with non-straight, wheel (A-2-3), and normal sequences
   */
  @Test
  void isStraight() {
    ArrayList<Card> testStraight1 = new ArrayList<>();
    ArrayList<Card> testStraight2 = new ArrayList<>();
    ArrayList<Card> testStraight3 = new ArrayList<>();

    // Not a straight: [5,3,8]
    testStraight1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testStraight1.add(new Card(CardSuit.CLUBS, CardRank.THREE));
    testStraight1.add(new Card(CardSuit.DIAMONDS, CardRank.EIGHT));
    System.out.println(testStraight1);
    Assertions.assertFalse(HandEvaluator.isStraight(testStraight1),
        "5-3-8 is not a straight");

    // Wheel straight: [A,2,3]
    testStraight2.add(new Card(CardSuit.CLUBS, CardRank.ACE));
    testStraight2.add(new Card(CardSuit.CLUBS, CardRank.TWO));
    testStraight2.add(new Card(CardSuit.HEARTS, CardRank.THREE));
    System.out.println(testStraight2);
    Assertions.assertTrue(HandEvaluator.isStraight(testStraight2),
        "A-2-3 should count as a straight (wheel)");

    // Normal straight: [5,6,7]
    testStraight3.add(new Card(CardSuit.CLUBS, CardRank.SIX));
    testStraight3.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testStraight3.add(new Card(CardSuit.HEARTS, CardRank.SEVEN));
    System.out.println(testStraight3);
    Assertions.assertTrue(HandEvaluator.isStraight(testStraight3),
        "5-6-7 should be a straight");

    // Another normal: [7,8,9]
    List<Card> testStraight4 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.EIGHT),
        new Card(CardSuit.DIAMONDS, CardRank.NINE),
        new Card(CardSuit.CLUBS, CardRank.SEVEN)
    );
    System.out.println(testStraight4);
    Assertions.assertTrue(HandEvaluator.isStraight(testStraight4),
        "7-8-9 should be a straight");
  }

  /**
   * Tests isFlush(...) with true and false cases.
   */
  @Test
  void isFlush() {
    ArrayList<Card> testFlush1 = new ArrayList<>();
    ArrayList<Card> testFlush2 = new ArrayList<>();
    // True flush: all clubs [♣5, ♣6, ♣J]
    testFlush1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testFlush1.add(new Card(CardSuit.CLUBS, CardRank.SIX));
    testFlush1.add(new Card(CardSuit.CLUBS, CardRank.JACK));
    System.out.println(testFlush1);
    Assertions.assertTrue(HandEvaluator.isFlush(testFlush1),
        "♣5-♣6-♣J should be a flush");

    // False flush: mixed suits [♦5, ♣6, ♣J]
    testFlush2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testFlush2.add(new Card(CardSuit.CLUBS, CardRank.SIX));
    testFlush2.add(new Card(CardSuit.CLUBS, CardRank.JACK));
    System.out.println(testFlush2);
    Assertions.assertFalse(HandEvaluator.isFlush(testFlush2),
        "Mixed suits should not be a flush");
  }

  /**
   * Tests isStraightFlush(...) with both invalid and valid sequences
   */
  @Test
  void isStraightFlush() {
    // Same suit but not consecutive → false, [♣5, ♣6, ♣J]
    ArrayList<Card> testStraightFlush1 = new ArrayList<>();
    testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.JACK));
    testStraightFlush1.add(new Card(CardSuit.CLUBS, CardRank.SIX));
    System.out.println(testStraightFlush1);
    System.out.println("isStraight: " + HandEvaluator.isStraight(testStraightFlush1));
    System.out.println("isFlush: " + HandEvaluator.isFlush(testStraightFlush1));

    Assertions.assertFalse(HandEvaluator.isStraightFlush(testStraightFlush1),
        "Non-consecutive but same suit should not be straight flush");

    // True straight flush: [5,6,7] all clubs
    ArrayList<Card> testStraightFlush2 = new ArrayList<>();
    testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.SIX));
    testStraightFlush2.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
    System.out.println(testStraightFlush2);
    Assertions.assertTrue(HandEvaluator.isStraightFlush(testStraightFlush2),
        "♣5-♣6-♣7 should be a straight flush");
  }

  /**
   * Tests isThreeOfOneKind(...) with both three-of-a-kind and non-three cases.
   */
  @Test
  void isThreeOfOneKind() {
    // [♣5, ♦5, ♠5] True
    ArrayList<Card> testThreeOfOneKind1 = new ArrayList<>();
    testThreeOfOneKind1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testThreeOfOneKind1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testThreeOfOneKind1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(testThreeOfOneKind1);
    Assertions.assertTrue(HandEvaluator.isThreeOfOneKind(testThreeOfOneKind1),
        "♣5-♦5-♠5 should be three of a kind");

    // [♣7, ♦5, ♠5] False
    ArrayList<Card> testThreeOfOneKind2 = new ArrayList<>();
    testThreeOfOneKind2.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
    testThreeOfOneKind2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testThreeOfOneKind2.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(testThreeOfOneKind2);
    Assertions.assertFalse(HandEvaluator.isThreeOfOneKind(testThreeOfOneKind2),
        "7-5-5 is not three of a kind");
  }

  /**
   * Tests isPair(...) with both pair and three-of-a-kind cases
   */
  @Test
  void isPair() {
    // [♣7, ♦5, ♠5] True pair
    ArrayList<Card> testPair1 = new ArrayList<>();
    testPair1.add(new Card(CardSuit.CLUBS, CardRank.SEVEN));
    testPair1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testPair1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(testPair1);
    Assertions.assertTrue(HandEvaluator.isPair(testPair1),
        "7-5-5 should be a pair");

    // [♣5, ♦5, ♠5] False when it's actually three-of-a-kind
    ArrayList<Card> testPair2 = new ArrayList<>();
    testPair2.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testPair2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testPair2.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(testPair2);
    Assertions.assertFalse(HandEvaluator.isPair(testPair2),
        "5-5-5 should not count as a pair");
  }

  /**
   * Tests isHighCard(...) under various scenarios
   */
  @Test
  void isHighCard() {
    // three-of-a-kind [♣5, ♦5, ♠5] False for HighCard
    ArrayList<Card> testHighCard1 = new ArrayList<>();
    testHighCard1.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testHighCard1.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testHighCard1.add(new Card(CardSuit.SPADES, CardRank.FIVE));
    System.out.println(testHighCard1);
    Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard1),
        "Three-of-a-kind should not be high card");

    // Straight [♣4, ♦5, ♠6] False for HighCard
    ArrayList<Card> testHighCard2 = new ArrayList<>();
    testHighCard2.add(new Card(CardSuit.CLUBS, CardRank.FOUR));
    testHighCard2.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testHighCard2.add(new Card(CardSuit.SPADES, CardRank.SIX));
    System.out.println(testHighCard2);
    Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard2),
        "Straight should not be high card");

    // Pair [♣5, ♦5, ♠6] False for HighCard
    ArrayList<Card> testHighCard3 = new ArrayList<>();
    testHighCard3.add(new Card(CardSuit.CLUBS, CardRank.FIVE));
    testHighCard3.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testHighCard3.add(new Card(CardSuit.SPADES, CardRank.SIX));
    System.out.println(testHighCard3);
    Assertions.assertFalse(HandEvaluator.isHighCard(testHighCard3),
        "Pair should not be high card");

    // [♣J, ♦5, ♠6] True high card
    ArrayList<Card> testHighCard4 = new ArrayList<>();
    testHighCard4.add(new Card(CardSuit.CLUBS, CardRank.JACK));
    testHighCard4.add(new Card(CardSuit.DIAMONDS, CardRank.FIVE));
    testHighCard4.add(new Card(CardSuit.SPADES, CardRank.SIX));
    System.out.println(testHighCard4);
    Assertions.assertTrue(HandEvaluator.isHighCard(testHighCard4),
        "J-6-5 should be identified as high card");
  }
}