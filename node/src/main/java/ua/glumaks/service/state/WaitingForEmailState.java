package ua.glumaks.service.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.domain.UserState;
import ua.glumaks.exceptions.MailServiceException;
import ua.glumaks.service.AppUserService;
import ua.glumaks.service.TelegramService;
import ua.glumaks.service.command.CommandType;
import ua.glumaks.util.CommandSpringUtil;

import java.util.Optional;

import static ua.glumaks.service.command.CommandType.CANCEL;

@Slf4j
@Component
public class WaitingForEmailState extends AbstractState {

    private final AppUserService userService;
    private final TelegramService telegramService;

    public WaitingForEmailState(AppUserService userService,
                                TelegramService telegramService) {
        super(UserState.WAIT_FOR_EMAIL_STATE);
        this.userService = userService;
        this.telegramService = telegramService;
    }

    @Override
    public void process(AppUser user, Message message) {
        String text = message.getText();
        Optional<CommandType> commandCandidate = CommandType.forCommand(text);
        if (commandCandidate.isPresent()) {
            CommandType command = commandCandidate.get();

            if (CANCEL.equals(command)) {
                CommandSpringUtil.forType(command)
                        .execute(user, message);
                return;
            }

            String answer = "Please, enter your email, to cancel registration enter /cancel";
            telegramService.sendMessage(message.getChatId(), answer);
            return;
        }

        try {
            userService.register(user, text);

            String answer = "Email is successfully set, follow the link sent on email to activate your account";
            telegramService.sendMessage(message.getChatId(), answer);
        } catch (IllegalArgumentException | IllegalStateException | MailServiceException e) {
            log.warn(e.getMessage());
            telegramService.sendMessage(message.getChatId(), e.getMessage());
        }
    }
}
