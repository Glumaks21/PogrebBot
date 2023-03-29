package ua.glumaks.service.command;

import java.util.Arrays;
import java.util.Optional;

public enum CommandType {
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static Optional<CommandType> forCommand(String value) {
        return Arrays.stream(CommandType.values())
                .filter(cmd -> cmd.value.equals(value))
                .findFirst();
    }
}
