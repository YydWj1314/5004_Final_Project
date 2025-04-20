package model.exception;

/**
 * Thrown when an operation requires more cards than are currently available in the deck This
 * unchecked exception extends RuntimeException, so it does not need to be caught or declared
 */
public class NoEnoughCardsException extends RuntimeException {

  public NoEnoughCardsException(String message) {
    super(message);
  }
}
