package ua.glumaks.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Component
public class TelegramBotCommandApi extends DefaultAbsSender {

    public TelegramBotCommandApi(@Value("${telegram.bot.token}") String token) {
        super(new DefaultBotOptions(), token);
    }


}
