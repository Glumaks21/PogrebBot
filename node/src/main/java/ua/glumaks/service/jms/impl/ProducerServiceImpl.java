package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import ua.glumaks.service.jms.ProducerService;

import static ua.glumaks.RabbitQueue.ANSWER_BOT_API_METHOD;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate template;

    @Override
    public void produce(BotApiMethod<?> answer) {
        template.convertAndSend(ANSWER_BOT_API_METHOD, answer);
    }

}
