package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.service.TelegramService;

@Component
public class HelpCommand extends AbstractCommand {

    private final TelegramService telegramService;


    public HelpCommand(TelegramService telegramService) {
        super(CommandType.HELP);
        this.telegramService = telegramService;
    }

    @Override
    public void execute(AppUser user, Message message) {
        String answer = """
                        List of accessible commands:
                        /cancel - cancel execution of the current command
                        /registration - register a client
                        """;
        telegramService.sendMessage(message.getChatId(), answer);
    }
}
