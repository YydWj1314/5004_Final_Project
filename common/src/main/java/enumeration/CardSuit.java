package enumeration;

public enum CardSuit {
  CLUBS("CLUBS", "♣"),
  DIAMONDS("DIAMONDS", "♦"),
  HEARTS("HEARTS", "♥"),
  SPADES("SPADES", "♠");

  private final String name;
  private final String symbol;

  CardSuit(String name, String symbol) {
    this.name = name;
    this.symbol = symbol;
  }

  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }

  public String toString() {
    return symbol;
  }

}
