package enumeration;

public enum CommandType {
    // Message Casting Type:
    UNICAST("UNIT"),
    BROADCAST("BROAD"),

    // Server command:
    JOIN("JOIN"),
    START("START"),

    // Client command:
    WELCOME("WELCOME"),
    CLIENT_PLAY("CLIENT_PLAY");


    private final String type;

    CommandType(String message) {
        this.type = message;
    }

    public String getMessage() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
