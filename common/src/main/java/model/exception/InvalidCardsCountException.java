package model.exception;

/**
 * Thrown when an invalid number of cards is provided to a hand or deck operation. This unchecked
 * exception extends RuntimeException, so methods using it need not declare it.
 */
public class InvalidCardsCountException extends RuntimeException {

  public InvalidCardsCountException(String message) {
    super(message);
  }
}
