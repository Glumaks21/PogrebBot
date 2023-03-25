package ua.glumaks.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.controller.MessageHandler;
import ua.glumaks.service.MessageSender;
import ua.glumaks.service.jms.UpdateProducer;
import ua.glumaks.utils.MessageUtils;

import static ua.glumaks.RabbitQueue.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageHandlerImpl implements MessageHandler {

    private final UpdateProducer updateProducer;
    private final MessageSender messageSender;

    @Override
    public void handle(Update update) {
        Assert.isTrue(update.hasMessage(),
                "Update doesn't contain message: " + update);

        Message message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else if (message.hasDocument()) {
            processDocMessage(update);
        } else if (message.hasPhoto()) {
            processPhotoMessage(update);
        } else {
            log.warn("Unsupported message content type is received: " + update);
            messageSender.sendMessage(MessageUtils.createSendMessage(update,
                    "Unsupported content type of message"));
        }
    }


    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
        SendMessage sendMessage = MessageUtils.createSendMessage(
                update, "File is received! Processing...");
        messageSender.sendMessage(sendMessage);
    }

    private void processDocMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
        SendMessage sendMessage = MessageUtils.createSendMessage(
                update, "File is received! Processing...");
        messageSender.sendMessage(sendMessage);
    }

}
