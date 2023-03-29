package ua.glumaks.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public class MessageUtils {

    public static SendMessage createSendMessage(String text, Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .build();
    }

}
