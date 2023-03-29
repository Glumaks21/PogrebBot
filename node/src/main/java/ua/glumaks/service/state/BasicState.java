package ua.glumaks.service.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.domain.UserState;
import ua.glumaks.service.command.CommandType;
import ua.glumaks.util.CommandSpringUtil;

import java.util.Optional;

import static ua.glumaks.util.MessageUtils.createSendMessage;

@Component
public class BasicState extends AbstractState {

    public BasicState() {
        super(UserState.BASIC_STATE);
    }

    @Override
    public BotApiMethod<?> process(AppUser user, Message message) {
        String text = message.getText();
        Optional<CommandType> commandCandidate = CommandType.forCommand(text);

        if (commandCandidate.isEmpty()) {
            return createSendMessage("Unknown command! To check command list enter /help", message);
        }


        CommandType commandType = commandCandidate.get();
        return CommandSpringUtil.forType(commandType)
                .execute(user, message);
    }

}
