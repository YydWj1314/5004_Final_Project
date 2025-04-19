package enumeration;

public enum GameParms {
    MAX_CARD_NUMBER(5),
    MAX_PLAYER_NUMBER(2);

    private final int value;

    GameParms(int value) {
        this.value = value;

    }

    public int getValue() {
        return value;
    }
}
