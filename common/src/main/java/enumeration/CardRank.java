package enumeration;

public enum CardRank {
  TWO(2, "2", "TWO"),
  THREE(3, "3","THREE" ),
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

  CardRank(int value, String symbol, String name) {
    this.rankValue = value;
    this.symbol = symbol;
    this.name = name;
  }

  public int getRankValue() {
    return this.rankValue;
  }

  public String getName(){ return this.name; }

  public String getSymbol() {
    return symbol;
  }

  /**
   * @param name
   * @return
   */
  public static CardRank fromName(String name){
    for(CardRank rank: values()){
      if(rank.getName().equals(name)){
        return rank;
      }
    }
    throw new IllegalArgumentException("No suit find with name:" + name);
  }

  @Override
  public String toString() {
    return symbol;
  }


}
