package model.exception;

public class EMPTY_HAND extends RuntimeException {

  /**
   * Thrown when an operation is performed on a hand that contains no cards.
   */
  public EMPTY_HAND(String message) {
    super(message);
  }
}
