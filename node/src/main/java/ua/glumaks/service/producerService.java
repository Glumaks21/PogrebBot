package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface producerService {

    void produceAnswer(SendMessage sendMessage);

}
