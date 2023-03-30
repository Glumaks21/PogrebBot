package ua.glumaks.service.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.domain.UserState;
import ua.glumaks.service.TelegramService;
import ua.glumaks.service.command.CommandType;
import ua.glumaks.util.CommandSpringUtil;

import java.util.Optional;


@Component
public class BasicState extends AbstractState {

    private final TelegramService telegramService;


    public BasicState(TelegramService telegramService) {
        super(UserState.BASIC_STATE);
        this.telegramService = telegramService;
    }

    @Override
    public void process(AppUser user, Message message) {
        String text = message.getText();
        Optional<CommandType> commandCandidate = CommandType.forCommand(text);

        if (commandCandidate.isEmpty()) {
            String answer = "Unknown command! To check command list enter /help";
            telegramService.sendMessage(message.getChatId(), answer);
            return;
        }

        CommandType commandType = commandCandidate.get();
        CommandSpringUtil.forType(commandType)
                .execute(user, message);
    }

}
