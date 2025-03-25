import enumeration.CardRank;
import enumeration.CardSuit;
import java.util.Comparator;
import java.util.Objects;

public class Card implements Comparable<Card> {

  private CardSuit cardSuit;
  private CardRank cardRank;

  public Card(CardSuit cardSuit, CardRank cardRank) {
    this.cardSuit = cardSuit;
    this.cardRank = cardRank;
  }

  public CardSuit getCardSuit() {
    return cardSuit;
  }

  public CardRank getCardRank() {
    return cardRank;
  }

  // Implement the Comparable interface
  @Override
  public int compareTo(Card otherCard) {
    return Comparator.comparing(Card::getCardRank)
        .thenComparing(Card::getCardSuit)
        .compare(this, otherCard);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return cardSuit == card.cardSuit && cardRank == card.cardRank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(cardSuit, cardRank);
  }

  @Override
  public String toString() {
    return cardSuit + "" + cardRank;
  }
}



