package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class FlushTest {

  private Flush flushTest1;

  /**
   * Prepare a Flush of [♥A, ♥J, ♥9] before each test
   */
  @BeforeEach
  void setUp() {
    List<Card> flush1 = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
        new Card(CardSuit.HEARTS, CardRank.JACK),
        new Card(CardSuit.HEARTS, CardRank.NINE));
    System.out.println(flush1);
    this.flushTest1 = new Flush(flush1);
  }

  /**
   * The hand name should be HandRank.FLUSH
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(HandRank.FLUSH, flushTest1.getHandName(),
        "A three‑card flush should report HandRank.FLUSH");
  }

  /**
   * The numeric hand value for a flush should equal its rankValue, which is 4 in the HandRank
   * enum.
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(4, flushTest1.getHandValue(),
        "Flush.getHandValue() should return HandRank.FLUSH.getRankValue() -> 4");
  }

  /**
   * getMainCards() should return exactly the three cards of the flush Since we used List.of(ACE,
   * JACK, NINE), the returned list should match that
   */
  @Test
  void getMainCards() {
    List<Card> expected = List.of(new Card(CardSuit.HEARTS, CardRank.ACE),
        new Card(CardSuit.HEARTS, CardRank.JACK),
        new Card(CardSuit.HEARTS, CardRank.NINE));

    Assertions.assertEquals(expected, flushTest1.getMainCards(),
        "getMainCards should return the exact heart cards forming the flush");
  }

  /**
   * getFlushSuit() should return the common suit of all cards: HEARTS.
   */
  @Test
  void getFlushSuit() {
    Assertions.assertEquals(CardSuit.HEARTS, flushTest1.getFlushSuit(),
        "getFlushSuit should return HEARTS for a hearts flush");
  }
}