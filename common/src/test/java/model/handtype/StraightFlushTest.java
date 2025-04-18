package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StraightFlushTest {

  private StraightFlush straightFlushTest1;

  /**
   * Prepare a StraightFlush of [♥A, ♥Q, ♥K] before each test
   */
  @BeforeEach
  void setUp() {
    // [♥A, ♥Q, ♥K]
    List<Card> straightFlush1 = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
        new Card(CardSuit.HEARTS, CardRank.QUEEN),
        new Card(CardSuit.HEARTS, CardRank.KING));

    System.out.println(straightFlush1);
    straightFlushTest1 = new StraightFlush(straightFlush1);

  }

  /**
   * The hand name must be HandRank.STRAIGHT_FLUSH.
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(
        HandRank.STRAIGHT_FLUSH,
        straightFlushTest1.getHandName(),
        "StraightFlush.getHandName() should return HandRank.STRAIGHT_FLUSH"
    );
  }

  /**
   * The numeric value for STRAIGHT_FLUSH is 6.
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(
        6,
        straightFlushTest1.getHandValue(),
        "StraightFlush.getHandValue() should return 6"
    );
  }

  /**
   * getMainCards() should return exactly the three cards forming the straight flush.
   */
  @Test
  void getMainCards() {
    List<Card> expected = List.of(
        new Card(CardSuit.HEARTS, CardRank.ACE),
        new Card(CardSuit.HEARTS, CardRank.QUEEN),
        new Card(CardSuit.HEARTS, CardRank.KING)
    );
    Assertions.assertEquals(
        expected,
        straightFlushTest1.getMainCards(),
        "getMainCards() should return the heart cards [A, Q, K]"
    );
  }

  /**
   * getStraightFlushSuit() should return the common suit: HEARTS.
   */
  @Test
  void getStraightFlushSuit() {
    Assertions.assertEquals(
        CardSuit.HEARTS,
        straightFlushTest1.getStraightFlushSuit(),
        "getStraightFlushSuit() should return HEARTS"
    );
  }
}