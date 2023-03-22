package ua.glumaks.domain;

import java.util.Arrays;
import java.util.Optional;

public enum Command {
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");

    private final String value;

    Command(String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }

    public static Optional<Command> forCommand(String value) {
        return Arrays.stream(Command.values())
                .filter(cmd -> cmd.value.equals(value))
                .findFirst();
    }

}
