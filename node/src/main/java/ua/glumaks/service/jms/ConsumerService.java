package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface ConsumerService {

    void consumeTextMessage(Message message);
    void consumeDocMessage(Message message);
    void consumePhotoMessage(Message message);

}