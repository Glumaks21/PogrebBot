package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;

import static ua.glumaks.util.MessageUtils.createSendMessage;

@Component
public class StartCommand extends AbstractCommand {

    public StartCommand() {
        super(CommandType.START);
    }

    @Override
    public BotApiMethod<?> execute(AppUser user, Message message) {
        String answer = "Hello! To check command list enter /help";
        return createSendMessage(answer, message);
    }

}
