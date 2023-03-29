package ua.glumaks.service.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repository.AppUserRepo;

import static ua.glumaks.domain.UserState.WAIT_FOR_EMAIL_STATE;
import static ua.glumaks.util.MessageUtils.createSendMessage;

@Component
public class RegistrationCommand extends AbstractCommand {

    private final AppUserRepo userRepo;


    public RegistrationCommand(AppUserRepo userRepo) {
        super(CommandType.REGISTRATION);
        this.userRepo = userRepo;
    }

    @Override
    public BotApiMethod<?> execute(AppUser user, Message message) {
        if (user.getEmail() != null) {
            return createSendMessage("You are already registered", message);
        }

        user.setState(WAIT_FOR_EMAIL_STATE);
        userRepo.save(user);

        String answer = "Enter your email, please";
        return createSendMessage(answer, message);
    }

}
