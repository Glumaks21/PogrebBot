package ua.glumaks.service.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;

public interface State {

    BotApiMethod<?> process(AppUser user, Message message);

}
