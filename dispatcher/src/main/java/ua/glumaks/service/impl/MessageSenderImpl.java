package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.controller.MyTelegramBot;
import ua.glumaks.service.MessageSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageSenderImpl implements MessageSender {

    private MyTelegramBot bot;

    @Autowired
    public void setBot(@Lazy MyTelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Failed to send message: {}", sendMessage);
        }
    }

}
