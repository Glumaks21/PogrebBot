package ua.glumaks.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repository.AppUserRepo;

import static ua.glumaks.domain.UserState.BASIC_STATE;
import static ua.glumaks.util.MessageUtils.createSendMessage;

@Component
@Scope
@RequiredArgsConstructor
public class CancelCommand implements Command {

    private final AppUserRepo userRepo;


    @Override
    public BotApiMethod<?> execute(AppUser user, Message message) {
        user.setState(BASIC_STATE);
        userRepo.save(user);

        String answer = "You sre successful rolled up to basic state";
        return createSendMessage(answer, message);
    }

}
