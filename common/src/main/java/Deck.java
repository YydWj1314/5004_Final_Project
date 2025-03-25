import enumeration.CardRank;
import enumeration.CardSuit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

  private final List<Card> cards;

  public Deck() {
    cards = new ArrayList<Card>();
    initDeck(); // Initialize the deck with 52 cards in order
  }

  // clear the deck and adds all 52 unique cards in order
  public void initDeck() {
    cards.clear();
    for (CardSuit suit : CardSuit.values()) {
      for (CardRank rank : CardRank.values()) {
        cards.add(new Card(suit, rank));
      }
    }
  }

  // make a random order of the cards in the deck
  public void shuffle() {
    Collections.shuffle(cards);
  }

  // Deals (removes and returns) the top card from the deck
  public List<Card> deal(int n) {
    if (isEmpty() || cards.size() < n) {
      return null;
    }
    List<Card> newList = cards.subList(0, n);
    cards.subList(0, n).clear(); // remove n cards from deck
    return newList;
  }

  // Check if the deck is empty
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  // Returns the number of cards remaining in the deck
  public int size() {
    return cards.size();
  }

  // Get a string representation of the deck
  @Override
  public String toString() {
    return "Cards: " + cards;
  }

}
