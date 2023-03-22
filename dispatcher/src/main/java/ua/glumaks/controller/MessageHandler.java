package ua.glumaks.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    void handle(Update update);
}
