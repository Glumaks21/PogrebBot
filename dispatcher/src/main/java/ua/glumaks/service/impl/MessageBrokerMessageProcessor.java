package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.glumaks.service.MessageProcessor;
import ua.glumaks.service.jms.ProducerService;
import ua.glumaks.util.MessageUtils;

import static ua.glumaks.RabbitQueue.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageBrokerMessageProcessor implements MessageProcessor {

    private final ProducerService producer;


    @Override
    public BotApiMethod<?> process(Message message) {
        if (message.hasText()) {
            return processText(message);
        }
        if (message.hasDocument()) {
            return processDoc(message);
        }
        if (message.hasPhoto()) {
            return processPhoto(message);
        }

        log.warn("Unsupported message content type is received: " + message);

        String answer = "Unsupported content type of message";
        return MessageUtils.createSendMessage(answer, message);
    }

    private BotApiMethod<?> processText(Message message) {
        producer.produce(TEXT_MESSAGE, message);
        return null;
    }

    private BotApiMethod<?> processPhoto(Message message) {
        producer.produce(PHOTO_MESSAGE, message);

        String answer = "File is received! Processing...";
        return MessageUtils.createSendMessage(answer, message);
    }

    private BotApiMethod<?> processDoc(Message message) {
        producer.produce(DOC_MESSAGE, message);

        String answer = "File is received! Processing...";
        return MessageUtils.createSendMessage(answer, message);
    }

}
