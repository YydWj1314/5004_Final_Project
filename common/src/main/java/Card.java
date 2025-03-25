import enumeration.CardRank;
import enumeration.CardSuit;
import java.util.Comparator;
import java.util.Objects;

public class Card implements Comparable<Card> {

  private CardSuit cardSuit;
  private CardRank cardRank;

  /**
   * Constructs a new Card with the specified suit and rank.
   *
   * @param cardSuit the suit of the card
   * @param cardRank the rank of the card
   */
  public Card(CardSuit cardSuit, CardRank cardRank) {
    this.cardSuit = cardSuit;
    this.cardRank = cardRank;
  }

  /**
   * @return the suit of this card
   */
  public CardSuit getCardSuit() {
    return cardSuit;
  }

  /**
   * @return the rank of this card
   */
  public CardRank getCardRank() {
    return cardRank;
  }

  /**
   * Compares this card with the specified card for order.
   * Comparison is primarily based on card rank, and secondarily on card suit.
   *
   * @param otherCard the card to be compared
   * @return a negative integer, zero, or a positive integer as this card is less than, equal to,
   *         or greater than the specified card
   */
  @Override
  public int compareTo(Card otherCard) {
    return Comparator.comparing(Card::getCardRank)
        .thenComparing(Card::getCardSuit)
        .compare(this, otherCard);
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   * Two cards are equal if they have the same suit and rank.
   *
   * @param o the reference object with which to compare
   * @return true if this card is equal to the provided object; false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return cardSuit == card.cardSuit && cardRank == card.cardRank;
  }

  /**
   * Returns a hash code value for this card based on its suit and rank.
   *
   * @return a hash code value for this card
   */
  @Override
  public int hashCode() {
    return Objects.hash(cardSuit, cardRank);
  }

  /**
   * Returns a string representation of this card in the format "SuitRank".
   *
   * @return a string representation of this card
   */
  @Override
  public String toString() {
    return cardSuit + "" + cardRank;
  }
}



