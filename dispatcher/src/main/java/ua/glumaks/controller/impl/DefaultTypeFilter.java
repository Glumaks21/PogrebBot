package ua.glumaks.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.controller.MessageHandler;
import ua.glumaks.controller.TypeFilter;
import ua.glumaks.service.MessageSender;
import ua.glumaks.utils.MessageUtils;

@Component
@Slf4j
@RequiredArgsConstructor
public class DefaultTypeFilter implements TypeFilter {

    private final MessageHandler messageHandler;
    private final MessageSender messageSender;


    @Override
    public void process(Update update) {
        if (update.hasMessage()) {
            messageHandler.handle(update);
        } else {
            log.warn("Unsupported message type is received: " + update);
            messageSender.sendMessage(MessageUtils.createSendMessage(update, "Unsupported type of message"));
        }
    }

}
