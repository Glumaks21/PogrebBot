package ua.glumaks.controller;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TypeFilter {
    void process(Update update);
}
