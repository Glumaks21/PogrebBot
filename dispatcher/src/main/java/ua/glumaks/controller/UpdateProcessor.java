package ua.glumaks.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProcessor {
    void process(Update update);
}
