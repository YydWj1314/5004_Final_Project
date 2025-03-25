import enumeration.CardRank;
import enumeration.CardSuit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a standard deck of 52 playing cards.
 */
public class Deck {
  private List<Card> cards;

  /**
   * Constructs a new Deck and initializes it with 52 cards in order.
   */
  public Deck() {
    cards = new ArrayList<Card>();
    initDeck(); // Initialize the deck with 52 cards in order
  }

  /**
   * Clears the deck and adds all 52 unique cards in order.
   * Populates the deck with every combination of CardSuit and CardRank.
   */
  public void initDeck() {
    cards.clear();
    for (CardSuit suit : CardSuit.values()) {
      for (CardRank rank : CardRank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
  }

  /**
   * Shuffles the deck into a random order.
   * Uses Collections#shuffle to randomize the order of the cards.
   */
  public void shuffle() {
    Collections.shuffle(cards);
  }

  /**
   * Deals (removes and returns) the top n cards from the deck.
   *
   * @param n the number of cards to deal
   * @return a list of n cards from the top of the deck, or null if there are
   *         not enough cards remaining
   */
  public List<Card> deal(int n) {
    if (isEmpty() || cards.size() < n) {
      return null;
    }
    List<Card> newList = cards.subList(0, n);
    cards.subList(0, n).clear(); // remove n cards from deck
    return newList;
  }

  /**
   * Checks if the deck is empty.
   *
   * @return true if there are no cards left in the deck; false otherwise
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  /**
   * @return the number of cards remaining in the deck
   */
  public int size() {
    return cards.size();
  }

  /**
   * @return a string containing all the cards currently in the deck
   */
  @Override
  public String toString() {
    return "Cards: " + cards;
  }

}
