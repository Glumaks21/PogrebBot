package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.glumaks.controller.MyTelegramBot;
import ua.glumaks.service.AnswerConsumer;

import static ua.glumaks.RabbitQueue.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class AnswerConsumerService implements AnswerConsumer {

    private final MyTelegramBot bot;

    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        bot.setView(sendMessage);
    }

}
