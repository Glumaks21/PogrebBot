package ua.glumaks.service.jms;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface ProducerService {
    void produce(String rabbitQueue, Message message);
}
