package enumeration;

public enum CommandType {
    // Message Casting Type:
    SERVER_UNICAST("UNIT"),
    SERVER_BROADCAST("BROAD"),

    // Server command:
    SERVER_START("START"),
    SERVER_RESULT("RESULT"),
    SERVER_WELCOME("WELCOME"),

    // Client command:
    CLIENT_PLAY("PLAY"),
    CLIENT_JOIN("JOIN");

    private final String message;

    CommandType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
