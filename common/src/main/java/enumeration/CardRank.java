package enumeration;

/**
 * Represents the rank of a playing card.
 */
public enum CardRank {
  TWO("2", 2),
  THREE("3", 3),
  FOUR("4", 4),
  FIVE("5", 5),
  SIX("6", 6),
  SEVEN("7", 7),
  EIGHT("8", 8),
  NINE("9", 9),
  TEN("10", 10),
  JACK("J", 11),
  QUEEN("Q", 12),
  KING("K", 13),
  ACE("A", 14);

  private final String symbol;
  private final int rankValue;

  /**
   * Constructs a CardRank with the specified symbol and rank value.
   *
   * @param symbol    the symbol associated with the card rank (e.g., "A" for Ace)
   * @param rankValue the numerical value of the card rank used for comparison
   */
  CardRank(String symbol, int rankValue) {
    this.symbol = symbol;
    this.rankValue = rankValue;
  }

  /**
   * @return the symbol of this card rank
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * @return rank value of this card rank
   */
  public int getRankValue() {
    return rankValue;
  }

  /**
   * Returns a string representation of this card rank, which is its symbol.
   *
   * @return the symbol of this card rank
   */
  @Override
  public String toString() {
    return symbol;
  }
}
