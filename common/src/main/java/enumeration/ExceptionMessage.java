package enumeration;

/**
 * Defines exception messages for game operations
 * Each enum constant encapsulates a specific error description
 */
public enum ExceptionMessage {
  /**
   * Thrown when attempting to draw from an empty deck.
   */
  EMPTY_DECK("Invalid Operation: Empty Deck"),

  /**
   * Thrown when there aren’t enough cards remaining to fulfill a deal
   */
  NO_ENOUGH_CARDS("Invalid Operation: No Enough Cards in Deck"),

  /**
   * Thrown when an operation is performed on a hand that contains no cards.
   */
  EMPTY_HAND("Invalid Operation: Empty Hand"),

  /**
   * Thrown when a hand is initialized or evaluated with an incorrect card count
   */
  INVALID_CARDS_COUNT("Invalid Operation: Invalid Card Count"),

  /**
   * Thrown when trying to reference a card that does not exist in the current collection
   */
  CARD_NOT_FOUND("Invalid Operation: Card Not Found"),

  /**
   * Thrown when a requested HandRank cannot be determined
   */
  HAND_RANK_NOT_FOUND("Invalid Operation: HandRank Not Found")
  ;

  private final String message;

  /**
   * Constructs an ExceptionMessage enum constant
   * @param message the descriptive text for this error scenario
   */
  ExceptionMessage(String message) {
    this.message = message;
  }

  /**
   * @return the descriptive error message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the descriptive error message
   */
  @Override
  public String toString() {
    return message;
  }
}
