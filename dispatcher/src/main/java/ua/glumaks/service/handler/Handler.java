package ua.glumaks.service.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Handler {

    boolean isApplicable(Update update);
    void handle(Update update);

}
