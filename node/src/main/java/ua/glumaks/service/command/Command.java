package ua.glumaks.service.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.domain.AppUser;


public interface Command {

    void execute(AppUser user, Message message);

}
