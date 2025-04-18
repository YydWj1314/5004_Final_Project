package model.handtype;

import enumeration.CardRank;
import enumeration.CardSuit;
import enumeration.HandRank;
import model.Card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class StraightTest {

  private Straight straightTest1;

  /**
   * Prepare a Straight of [2♥, A♠, 3♦] (the wheel) before each test
   */
  @BeforeEach
  void setUp() {
    List<Card> straight1 = new ArrayList<>(List.of(
        new Card(CardSuit.HEARTS, CardRank.TWO),
        new Card(CardSuit.SPADES, CardRank.ACE),
        new Card(CardSuit.DIAMONDS, CardRank.THREE)
    ));

    System.out.println(straight1);
    straightTest1 = new Straight(straight1);
  }

  /**
   * The hand name must be HandRank.STRAIGHT
   */
  @Test
  void getHandName() {
    Assertions.assertEquals(
        HandRank.STRAIGHT,
        straightTest1.getHandName(),
        "Straight.getHandName() should return HandRank.STRAIGHT"
    );
  }

  /**
   * The numeric value for STRAIGHT is 3.
   */
  @Test
  void getHandValue() {
    Assertions.assertEquals(
        3,
        straightTest1.getHandValue(),
        "Straight.getHandValue() should return 3"
    );
  }

  /**
   * getMainCards() should return all three cards of the straight
   */
  @Test
  void getMainCards() {
    List<Card> expected = List.of(
        new Card(CardSuit.HEARTS, CardRank.TWO),
        new Card(CardSuit.SPADES, CardRank.ACE),
        new Card(CardSuit.DIAMONDS, CardRank.THREE)
    );
    List<Card> actual = straightTest1.getMainCards();
    assertEquals(3, actual.size(), "Should have exactly three cards");
    assertTrue(
        actual.containsAll(expected),
        "getMainCards() should include 2♥, A♠, and 3♦"
    );
  }

  /**
   * getHighestCard() should return the '2' for the A-2-3 wheel
   */
  @Test
  void getHighestCard() {
    Card expectedCard = new Card(CardSuit.HEARTS, CardRank.TWO);
    System.out.println("Highest card: " + straightTest1.getHighestCard());
    Assertions.assertEquals(
        expectedCard,
        straightTest1.getHighestCard(),
        "In the wheel straight, 2♥ should be considered the highest card"
    );
  }
}


