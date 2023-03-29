package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.jms.ProducerService;

import static ua.glumaks.RabbitQueue.ANSWER_MESSAGE;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate template;

    @Override
    public void produce(BotApiMethod<?> answer) {
        template.convertAndSend(ANSWER_MESSAGE, answer);
    }

}
