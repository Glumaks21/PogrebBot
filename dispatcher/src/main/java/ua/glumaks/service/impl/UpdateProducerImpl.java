package ua.glumaks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.UpdateProducer;

@Service
@Slf4j
public class UpdateProducerImpl implements UpdateProducer {

    @Override
    public void produce(String rabbitQueue, Update update) {
        log.debug(update.getMessage().getText());
    }

}
