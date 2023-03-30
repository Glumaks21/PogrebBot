package ua.glumaks;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.glumaks.config.BotConfigurationProperties;
import ua.glumaks.service.Dispatcher;
import ua.glumaks.service.TelegramService;


@Controller
public class MyTelegramBot extends TelegramWebhookBot {

    private final BotConfigurationProperties properties;
    private final TelegramService telegramService;
    private final Dispatcher dispatcher;


    @PostConstruct
    void init() {
        SetWebhook setWebhook = SetWebhook.builder()
                .url(properties.getUrl())
                .build();
        try {
            this.setWebhook(setWebhook);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public MyTelegramBot(BotConfigurationProperties properties,
                         TelegramService telegramService,
                         Dispatcher dispatcher) {
        super(properties.getToken());
        this.properties = properties;
        this.telegramService = telegramService;
        this.dispatcher = dispatcher;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (!dispatcher.dispatch(update) && update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            telegramService.sendMessage(chatId,
                    "Unknown command! To check command list enter /help");
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return properties.getPath();
    }

    @Override
    public String getBotUsername() {
        return properties.getUsername();
    }

}
