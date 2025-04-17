package model.exception;

/**
 * Thrown to indicate that a requested Card could not be found in the current context
 * This unchecked exception extends RuntimeException so it need not be declared or caught
 */
public class CardNotFoundException extends RuntimeException {
  public CardNotFoundException(String message) {
    super(message);
  }
}
