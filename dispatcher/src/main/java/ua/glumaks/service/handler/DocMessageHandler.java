package ua.glumaks.service.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.jms.ProducerService;

import static ua.glumaks.RabbitQueue.DOC_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocMessageHandler implements Handler {

    private final ProducerService producer;


    @Override
    public boolean isApplicable(Update update) {
        return update.hasMessage() && update.getMessage().hasDocument();
    }

    @Override
    public void handle(Update update) {
        log.debug("Handled a document message update: {}", update);
        producer.produce(DOC_MESSAGE, update.getMessage());
    }

}
