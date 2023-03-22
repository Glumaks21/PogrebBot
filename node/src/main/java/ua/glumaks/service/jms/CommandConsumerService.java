package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandConsumerService {

    void consumeTextMessageUpdate(Update update);
    void consumeDocMessageUpdate(Update update);
    void consumePhotoMessageUpdate(Update update);

}