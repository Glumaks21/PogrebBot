package ua.glumaks.service.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class AbstractCommand implements Command {

    private final CommandType commandType;

}
