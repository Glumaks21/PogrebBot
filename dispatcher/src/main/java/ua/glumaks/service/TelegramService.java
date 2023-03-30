package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface TelegramService {
    void sendMessage(Long chatId, String text, ReplyKeyboardMarkup keyboard);
    default void sendMessage(Long chatId, String text) {
        sendMessage(chatId, text, null);
    }
    void execute(BotApiMethod<?> method);
}
