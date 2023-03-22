package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.glumaks.service.jms.AnswerProducerService;

import static ua.glumaks.RabbitQueue.ANSWER_MESSAGE;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements AnswerProducerService {

    private final RabbitTemplate template;

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        template.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

}
