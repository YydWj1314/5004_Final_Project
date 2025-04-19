package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.JavaBean.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ThreeOfOneKindTest {

  private ThreeOfOneKind threeTest1;

  /**
   * Prepare a ThreeOfOneKind hand of [♥6, ♦6, ♠6] before each test
   */
  @BeforeEach
  void setUp() {
    List<Card> three1 = List.of(new Card(CardSuit.HEARTS, CardRank.SIX),
        new Card(CardSuit.DIAMONDS, CardRank.SIX),
        new Card(CardSuit.SPADES, CardRank.SIX)
    );
    System.out.println(three1);
    threeTest1 = new ThreeOfOneKind(three1);
  }


  /**
   * getHandName() must return HandRank.THREE_OF_A_KIND
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(
        HandRank.THREE_OF_A_KIND,
        threeTest1.getHandName(),
        "ThreeOfOneKind.getHandName() should return THREE_OF_A_KIND"
    );
  }

  /**
   * getHandValue() must return the numeric rank value (5 for three-of-a-kind)
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(
        5,
        threeTest1.getHandValue(),
        "ThreeOfOneKind.getHandValue() should return 5"
    );
  }

  /**
   * getMainCards() should return all three cards forming the set. After sorting by rank then suit,
   * expected order is [♦6, ♥6, ♠6]
   */
  @Test
  void getMainCards() {
    List<Card> expected = List.of(
        new Card(CardSuit.HEARTS, CardRank.SIX),
        new Card(CardSuit.DIAMONDS, CardRank.SIX),
        new Card(CardSuit.SPADES, CardRank.SIX)
    );
    Assertions.assertEquals(
        expected,
        threeTest1.getMainCards(),
        "getMainCards() should return the three sixes"
    );
  }

  /**
   * getThreeOfOneKindRank() should return the rank that appears three times: SIX.
   */
  @Test
  void getThreeOfOneKindRank() {
    Assertions.assertEquals(
        CardRank.SIX,
        threeTest1.getThreeOfOneKindRank(),
        "getThreeOfOneKindRank() should return SIX"
    );
  }
}