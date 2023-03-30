package ua.glumaks.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.exceptions.TelegramServiceException;
import ua.glumaks.service.TelegramService;


@Slf4j
@Service
public class TelegramServiceImpl implements TelegramService {

    private AbsSender sender;


    @Lazy
    @Autowired
    public void setSender(AbsSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
        execute(sendMessage);
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

    public void execute(BotApiMethod<?> method) {
        try {
            sender.execute(method);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw new TelegramServiceException(e);
        }
    }

}
