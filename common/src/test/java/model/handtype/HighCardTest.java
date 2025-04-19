package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.JavaBean.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class HighCardTest {

  private HighCard highCardTest1;

  /**
   * Prepare a HighCard hand of [♥8, ♠Q, ♦K] before each test
   */
  @BeforeEach
  void setUp() {
    List<Card> highCard1 = new ArrayList<>(List.of(
        new Card(CardSuit.HEARTS, CardRank.EIGHT),
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.DIAMONDS, CardRank.KING)
    ));
    System.out.println(highCard1);
    highCardTest1 = new HighCard(highCard1);
  }

  /**
   * The hand name should be HandRank.HIGH_CARD
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(
        HandRank.HIGH_CARD,
        highCardTest1.getHandName(),
        "HighCard.getHandName() should return HandRank.HIGH_CARD"
    );
  }

  /**
   * The numeric value for HIGH_CARD is 1.
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(
        1,
        highCardTest1.getHandValue(),
        "HighCard.getHandValue() should return 1"
    );
  }

  /**
   * getMainCards() should return a singleton list containing only the highest card (♦K)
   */
  @Test
  void getMainCards() {
    List<Card> expected = List.of(
        new Card(CardSuit.DIAMONDS, CardRank.KING)
    );
    Assertions.assertEquals(
        expected,
        highCardTest1.getMainCards(),
        "getMainCards should return only the Diamond King as the highest card"
    );
  }
}
