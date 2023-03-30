package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ua.glumaks.service.TelegramService;
import ua.glumaks.service.jms.ConsumerService;

import static ua.glumaks.RabbitQueue.ANSWER_BOT_API_METHOD;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final TelegramService service;

    @Override
    @RabbitListener(queues = ANSWER_BOT_API_METHOD)
    public void consume(BotApiMethod<?> responseMethod) {
        log.debug("Consumed response from queue {}: {}",
                ANSWER_BOT_API_METHOD, responseMethod);
        service.execute(responseMethod);
    }

}
