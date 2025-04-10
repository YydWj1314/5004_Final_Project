package enumeration;

public enum CommandType {
    // Cast Type:
    UNICAST("UNIT"),
    BROADCAST("BROAD"),

    // Server command:
    JOIN("JOIN"),
    JSON("JSON"),

    // Client command:
    WELCOME("WELCOME"),
    CLIENT_PLAY("CLIENT_PLAY"),
    START("START");


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
