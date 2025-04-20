package enumeration;

public enum CardRank {
  TWO(2, "2", "TWO"),
  THREE(3, "3", "THREE"),
  FOUR(4, "4", "FOUR"),
  FIVE(5, "5", "FIVE"),
  SIX(6, "6", "SIX"),
  SEVEN(7, "7", "SEVEN"),
  EIGHT(8, "8", "EIGHT"),
  NINE(9, "9", "NINE"),
  TEN(10, "10", "TEN"),
  JACK(11, "J", "JACK"),
  QUEEN(12, "Q", "QUEEN"),
  KING(13, "K", "KING"),
  ACE(14, "A", "ACE");


  private final int rankValue;
  private final String symbol;
  private final String name;

  /**
   * Constructor for CardRank enum.
   *
   * @param value  the numerical rank value
   * @param symbol the character symbol used to display the rank
   * @param name   the string name of the rank
   */
  CardRank(int value, String symbol, String name) {
    this.rankValue = value;
    this.symbol = symbol;
    this.name = name;
  }

  public int getRankValue() {
    return this.rankValue;
  }

  public String getName() {
    return this.name;
  }

  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns the CardRank enum constant corresponding to the given name.
   *
   * @param name the name to look up (e.g., "KING")
   * @return the corresponding CardRank enum value
   * @throws IllegalArgumentException if the name doesn't match any rank
   */
  public static CardRank fromName(String name) {
    for (CardRank rank : values()) {
      if (rank.getName().equals(name)) {
        return rank;
      }
    }
    throw new IllegalArgumentException("No rank find with name:" + name);
  }

  @Override
  public String toString() {
    return symbol;
  }


}
