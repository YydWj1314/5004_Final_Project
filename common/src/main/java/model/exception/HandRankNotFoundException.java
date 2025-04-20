package model.exception;

/**
 * Thrown when a hand’s rank cannot be determined from the given cards This unchecked exception
 * indicates the evaluator failed to match any known HandRank
 */
public class HandRankNotFoundException extends RuntimeException {

  public HandRankNotFoundException(String message) {
    super(message);
  }
}
