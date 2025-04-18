package utils;

import enumeration.CardRank;
import enumeration.CardSuit;
import model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RankComparatorTest {

  private RankComparator rankComparator;

  /**
   * Initialize a fresh comparator before each test
   */
  @BeforeEach
  void setUp() {
    rankComparator = new RankComparator();
  }

  /**
   * Straight flush should outrank a high card. Since o1 is a StraightFlush and o2 is a HighCard,
   * compare(o1,o2) < 0.
   */
  @Test
  void testDifferentHandRank() {
    List<Card> straightFlushCards = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.KING),
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.SPADES, CardRank.JACK)
    );
    List<Card> highCardCards = Arrays.asList(
        new Card(CardSuit.HEARTS, CardRank.ACE),
        new Card(CardSuit.SPADES, CardRank.NINE),
        new Card(CardSuit.DIAMONDS, CardRank.THREE)
    );
    int result = rankComparator.compare(straightFlushCards, highCardCards);
    assertTrue(result < 0, "Straight flush should outrank high card → negative result");
  }

  /**
   * Compare two pairs: Jacks vs Queens. Pair of Queens is stronger, so compare(pairJacks,
   * pairQueens) > 0.
   */
  @Test
  void testSameHandRank_PairComparison() {
    List<Card> pairJacks = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.JACK),
        new Card(CardSuit.DIAMONDS, CardRank.JACK),
        new Card(CardSuit.CLUBS, CardRank.NINE)
    );
    List<Card> pairQueens = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.DIAMONDS, CardRank.QUEEN),
        new Card(CardSuit.CLUBS, CardRank.EIGHT)
    );
    int result = rankComparator.compare(pairJacks, pairQueens);
    assertTrue(result > 0, "Pair of Queens should outrank pair of Jacks → positive result");
  }

  /**
   * Two identical pair hands (both Q-Q-9) should tie exactly → compare() == 0.
   */
  @Test
  void testAllSameHandRank_PairComparison() {
    List<Card> pair1 = Arrays.asList(
        new Card(CardSuit.CLUBS, CardRank.QUEEN),
        new Card(CardSuit.HEARTS, CardRank.QUEEN),
        new Card(CardSuit.HEARTS, CardRank.NINE)
    );
    List<Card> pair2 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.QUEEN),
        new Card(CardSuit.DIAMONDS, CardRank.QUEEN),
        new Card(CardSuit.CLUBS, CardRank.NINE)
    );
    int result = rankComparator.compare(pair1, pair2);
    assertEquals(0, result, "Identical pairs should tie → zero result");
  }

  /**
   * Compare two high‐card hands: Ace-high vs King-high. Ace-high wins, so compare(aceHigh,
   * kingHigh) < 0.
   */
  @Test
  void testSameHandRank_HighCardComparison() {
    List<Card> aceHigh = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.ACE),
        new Card(CardSuit.DIAMONDS, CardRank.TEN),
        new Card(CardSuit.CLUBS, CardRank.EIGHT)
    );
    List<Card> kingHigh = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.KING),
        new Card(CardSuit.DIAMONDS, CardRank.NINE),
        new Card(CardSuit.CLUBS, CardRank.SEVEN)
    );
    int result = rankComparator.compare(aceHigh, kingHigh);
    assertTrue(result < 0, "Ace-high should outrank King-high → negative result");
  }

  /**
   * Exactly the same 3-card hand should tie → compare() == 0.
   */
  @Test
  void testSameHandRank_ExactSameCards() {
    List<Card> hand1 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.JACK),
        new Card(CardSuit.DIAMONDS, CardRank.NINE),
        new Card(CardSuit.CLUBS, CardRank.SEVEN)
    );
    List<Card> hand2 = Arrays.asList(
        new Card(CardSuit.SPADES, CardRank.JACK),
        new Card(CardSuit.DIAMONDS, CardRank.NINE),
        new Card(CardSuit.CLUBS, CardRank.SEVEN)
    );
    int result = rankComparator.compare(hand1, hand2);
    assertEquals(0, result, "Identical hands should tie → zero result");
  }
}
