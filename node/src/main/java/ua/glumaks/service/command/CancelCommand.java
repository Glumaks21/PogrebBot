package ua.glumaks.service.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.repository.AppUserRepo;
import ua.glumaks.service.TelegramService;

import static ua.glumaks.domain.UserState.BASIC_STATE;

@Component
@Scope
@RequiredArgsConstructor
public class CancelCommand implements Command {

    private final AppUserRepo userRepo;
    private final TelegramService telegramService;


    @Override
    public void execute(AppUser user, Message message) {
        user.setState(BASIC_STATE);
        userRepo.save(user);

        String answer = "You sre successful rolled up to basic state";
        telegramService.sendMessage(message.getChatId(), answer);
    }

}
