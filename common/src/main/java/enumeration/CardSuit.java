package enumeration;

/**
 * Represents the suit of a playing card
 */
public enum CardSuit {
  CLUBS("CLUBS", "♣"),
  DIAMONDS("DIAMONDS", "♦"),
  HEARTS("HEARTS", "♥"),
  SPADES("SPADES", "♠");

  private final String name;
  private final String symbol;

  /**
   * Constructs a CardSuit with the specified name and symbol
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

  /**
   * Returns the CardSuit enum constant corresponding to the given name.
   *
   * @param name the name to look up (e.g., "CLUBS")
   * @return the corresponding CardSuit enum value
   * @throws IllegalArgumentException if the name doesn't match any suit
   */
  public static CardSuit fromName(String name){
    for(CardSuit suit: values()){
      if(suit.getName().equals(name)){
        return suit;
      }
    }
    throw new IllegalArgumentException("No suit find with name:" + name);
  }

}
