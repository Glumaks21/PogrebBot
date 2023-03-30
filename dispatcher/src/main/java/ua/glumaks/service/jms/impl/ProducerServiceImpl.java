package ua.glumaks.service.jms.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.jms.ProducerService;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate template;


    @Override
    public void produce(String queue, Message message) {
        log.debug("Send to queue {} a message: {}", queue, message);
        template.convertAndSend(queue, message);
    }

}
