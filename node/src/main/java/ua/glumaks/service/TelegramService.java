package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface TelegramService {

    void sendMessage(Long chatId, String text, ReplyKeyboard replyKeyboard);

    default void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }

}
