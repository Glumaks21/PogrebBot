package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface ConsumerService {
    void consume(BotApiMethod<?> response);
}
