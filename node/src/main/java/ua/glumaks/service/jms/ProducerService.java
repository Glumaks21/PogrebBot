package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface ProducerService {

    void produce(BotApiMethod<?> answer);

}
