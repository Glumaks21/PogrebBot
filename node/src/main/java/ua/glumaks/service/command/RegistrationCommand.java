package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.service.TelegramService;

import static ua.glumaks.domain.UserState.WAIT_FOR_EMAIL_STATE;

@Component
public class RegistrationCommand extends AbstractCommand {

    private final AppUserRepo userRepo;
    private final TelegramService telegramService;


    public RegistrationCommand(AppUserRepo userRepo,
                               TelegramService telegramService) {
        super(CommandType.REGISTRATION);
        this.userRepo = userRepo;
        this.telegramService = telegramService;
    }

    @Override
    public void execute(AppUser user, Message message) {
        if (user.getEmail() != null) {
            String answer = "You are already registered";
            telegramService.sendMessage(message.getChatId(), answer);
            return;
        }

        user.setState(WAIT_FOR_EMAIL_STATE);
        userRepo.save(user);

        String answer = "Enter your email, please";
        telegramService.sendMessage(message.getChatId(), answer);
    }

}
