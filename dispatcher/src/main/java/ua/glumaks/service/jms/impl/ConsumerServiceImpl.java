package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.service.jms.ConsumerService;

import static ua.glumaks.RabbitQueue.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final TelegramWebhookBot bot;

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(BotApiMethod<?> response) {
        try {
            bot.execute(response);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
