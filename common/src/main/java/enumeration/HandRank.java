package enumeration;


/**
 * Enumerates poker hand types in ascending strength, each with an associated numeric value
 */
public enum HandRank {
  /**
   * Highest single card (no better combination)
   */
  HIGH_CARD(1),

  /**
   * Exactly two cards of the same rank
   */
  PAIR(2),

  /**
   * three consecutive cards of mixed suits
   */
  STRAIGHT(3),

  /**
   * three cards of the same suit (not in sequence)
   */
  FLUSH(4),

  /**
   * Exactly three cards of the same rank
   */
  THREE_OF_A_KIND(5),

  /**
   * three consecutive cards of the same suit
   */
  STRAIGHT_FLUSH(6);

  /**numeric value indicating the relative strength of this hand type*/
  private final int rankValue;

  HandRank(int rankValue) {
    this.rankValue = rankValue;
  }

  /**
   * @return an integer representing the hand type's strength
   */
  public int getRankValue() {
    return rankValue;
  }
}
