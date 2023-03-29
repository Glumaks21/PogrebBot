package ua.glumaks.service.state;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;
import ua.glumaks.domain.UserState;
import ua.glumaks.exceptions.EmailServiceException;
import ua.glumaks.service.AppUserService;
import ua.glumaks.service.command.CancelCommand;
import ua.glumaks.service.command.CommandType;
import ua.glumaks.util.CommandSpringUtil;

import java.util.Optional;

import static ua.glumaks.service.command.CommandType.CANCEL;
import static ua.glumaks.util.MessageUtils.createSendMessage;

@Slf4j
@Component
public class WaitingForEmailState extends AbstractState {

    private final AppUserService userService;


    public WaitingForEmailState(AppUserService userService) {
        super(UserState.WAIT_FOR_EMAIL_STATE);
        this.userService = userService;
    }

    @Override
    public BotApiMethod<?> process(AppUser user, Message message) {
        String text = message.getText();
        Optional<CommandType> commandCandidate = CommandType.forCommand(text);
        if (commandCandidate.isPresent()) {
            CommandType command = commandCandidate.get();

            if (CANCEL.equals(command)) {
                return CommandSpringUtil.forType(command)
                        .execute(user, message);
            }

            String answer = "Please, enter your email, to cancel registration enter /cancel";
            return createSendMessage(answer, message);
        }

        try {
            userService.register(user, text);

            String answer = "Email is successfully set, follow the link sent on email to activate your account";
            return createSendMessage(answer, message);
        } catch (IllegalArgumentException | IllegalStateException | EmailServiceException e) {
            log.warn(e.getMessage());
            return createSendMessage(e.getMessage(), message);
        }
    }
}
