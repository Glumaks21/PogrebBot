package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerProducerService {

    void produceAnswer(SendMessage sendMessage);

}
