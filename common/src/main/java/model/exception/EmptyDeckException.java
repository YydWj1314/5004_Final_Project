package model.exception;

/**
 * Thrown to indicate that an operation was attempted on an empty deck of cards This unchecked
 * exception extends RuntimeException, so it does not need to be explicitly declared
 */
public class EmptyDeckException extends RuntimeException {

  public EmptyDeckException(String message) {
    super(message);
  }
}
