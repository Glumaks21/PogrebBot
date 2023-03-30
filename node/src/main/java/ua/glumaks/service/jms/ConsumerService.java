package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface ConsumerService {

    void consumeTextMessageUpdate(Message message);
    void consumeDocMessageUpdate(Message message);
    void consumePhotoMessageUpdate(Message message);

}