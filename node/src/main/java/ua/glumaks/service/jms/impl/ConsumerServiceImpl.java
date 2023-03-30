package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.service.jms.ConsumerService;
import ua.glumaks.service.MessageProcessor;

import static ua.glumaks.RabbitQueue.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final MessageProcessor processor;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE)
    public void consumeTextMessageUpdate(Message message) {
        log.debug("NODE: text message received: " + message);
        processor.processTextMessage(message);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE)
    public void consumeDocMessageUpdate(Message message) {
        log.debug("NODE: doc message received: " + message);
        processor.processDocMessage(message);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE)
    public void consumePhotoMessageUpdate(Message message) {
        log.debug("NODE: photo message received: " + message);
        processor.processPhotoMessage(message);
    }
}
