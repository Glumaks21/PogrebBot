package ua.glumaks.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.glumaks.service.command.*;

@Slf4j
@Component
public class CommandSpringUtil {

    private static ApplicationContext context;

    @Autowired
    public void setContext(ApplicationContext context) {
        CommandSpringUtil.context = context;
    }

    public static Command forType(CommandType type) {
        return switch (type) {
            case CANCEL -> context.getBean(CancelCommand.class);
            case START -> context.getBean(StartCommand.class);
            case REGISTRATION -> context.getBean(RegistrationCommand.class);
            case HELP -> context.getBean(HelpCommand.class);
            default -> {
                log.error("Unknown command type: " + type);
                throw new EnumConstantNotPresentException(CommandType.class, type.name());
            }
        };
    }

}
