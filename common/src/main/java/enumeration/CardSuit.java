package enumeration;

/**
 * Represents the suit of a playing card.
 */
public enum CardSuit {
  CLUBS("CLUBS", "♣"),
  DIAMONDS("DIAMONDS", "♦"),
  HEARTS("HEARTS", "♥"),
  SPADES("SPADES", "♠");

  private final String name;
  private final String symbol;

  /**
   * Constructs a CardSuit with the specified name and symbol.
   *
   * @param name   the name of the card suit (e.g., "HEARTS")
   * @param symbol the symbol associated with the card suit (e.g., "♥")
   */
  CardSuit(String name, String symbol) {
    this.name = name;
    this.symbol = symbol;
  }

  /**
   * @return the name of this card suit
   */
  public String getName() {
    return name;
  }

  /**
   * @return the symbol of this card suit
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns a string representation of this card suit, which is its symbol.
   *
   * @return the symbol of this card suit
   */
  public String toString() {
    return symbol;
  }

}
