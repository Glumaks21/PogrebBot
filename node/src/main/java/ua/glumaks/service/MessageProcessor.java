package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageProcessor {

    void processTextMessage(Update update);
    void processDocMessage(Update update);
    void processPhotoMessage(Update update);

}
