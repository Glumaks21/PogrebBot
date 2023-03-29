package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;

import static ua.glumaks.util.MessageUtils.createSendMessage;

@Component
public class HelpCommand extends AbstractCommand {

    public HelpCommand() {
        super(CommandType.HELP);
    }

    @Override
    public BotApiMethod<?> execute(AppUser user, Message message) {
        String answer = """
                        List of accessible commands:
                        /cancel - cancel execution of the current command
                        /registration - register a client
                        """;
        return createSendMessage(answer, message);
    }
}
