package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Dispatcher {
    boolean dispatch(Update update);

}
