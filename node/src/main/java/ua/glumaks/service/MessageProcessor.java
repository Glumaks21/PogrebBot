package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessor {

    void processTextMessage(Message message);
    void processDocMessage(Message message);
    void processPhotoMessage(Message message);

}
