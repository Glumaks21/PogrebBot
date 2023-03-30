package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.exceptions.TelegramServiceException;
import ua.glumaks.service.TelegramService;
import ua.glumaks.service.jms.ProducerService;

@Service
@RequiredArgsConstructor
public class TelegramServiceImpl implements TelegramService {

    private final ProducerService producer;


    @Override
    public void sendMessage(Long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .build();
        producer.produce(sendMessage);
    }

    @Override
    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }


}
