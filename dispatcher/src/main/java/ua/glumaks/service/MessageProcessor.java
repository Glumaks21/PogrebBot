package ua.glumaks.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageProcessor {
    BotApiMethod<?> process(Message message);
}
