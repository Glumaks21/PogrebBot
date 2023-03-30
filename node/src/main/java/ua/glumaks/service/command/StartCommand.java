package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.service.TelegramService;

@Component
public class StartCommand extends AbstractCommand {

    private final TelegramService telegramService;

    public StartCommand(TelegramService telegramService) {
        super(CommandType.START);
        this.telegramService = telegramService;
    }

    @Override
    public void execute(AppUser user, Message message) {
        String answer = "Hello! To check command list enter /help";
        telegramService.sendMessage(message.getChatId(), answer);
    }

}
