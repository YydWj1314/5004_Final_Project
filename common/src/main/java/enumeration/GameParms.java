package enumeration;

/**
 * Enum representing game parameters/constants used to configure the game. These parameters define
 * limits such as maximum cards per player and maximum number of players allowed in the game.
 */
public enum GameParms {
  MAX_CARD_NUMBER(5),
  MAX_PLAYER_NUMBER(3);

  private final int value;

  GameParms(int value) {
    this.value = value;

  }

  /**
   * Getter method to retrieve the value of the game parameter.
   *
   * @return the integer value associated with the parameter
   */
  public int getValue() {
    return value;
  }
}
