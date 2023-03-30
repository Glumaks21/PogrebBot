package ua.glumaks.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.jms.ProducerService;

import static ua.glumaks.RabbitQueue.TEXT_MESSAGE;


@Slf4j
@Component
@RequiredArgsConstructor
public class TextMessageHandler implements Handler {

    private final ProducerService producer;


    @Override
    public boolean isApplicable(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    @Override
    public void handle(Update update) {
        log.debug("Handled a text message update: {}", update);
        producer.produce(TEXT_MESSAGE, update.getMessage());
    }

}
