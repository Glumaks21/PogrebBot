package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.MessageProcessor;
import ua.glumaks.service.UpdateHandler;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageBrokerUpdateHandler implements UpdateHandler {

    private final MessageProcessor messageProcessor;


    @Override
    public BotApiMethod<?> handle(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            return messageProcessor.process(message);
        }

        log.warn("Unsupported message type is received: " + update);
        return null;
    }

}
