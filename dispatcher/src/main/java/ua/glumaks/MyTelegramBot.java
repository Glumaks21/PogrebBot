package ua.glumaks;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ua.glumaks.config.BotConfigurationProperties;
import ua.glumaks.service.UpdateHandler;


@Component
public class MyTelegramBot extends SpringWebhookBot {

    private final BotConfigurationProperties properties;
    private final UpdateHandler handler;



    public MyTelegramBot(SetWebhook setWebhook,
                         BotConfigurationProperties properties,
                         UpdateHandler handler) {
        super(setWebhook, properties.getToken());
        this.properties = properties;
        this.handler = handler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handler.handle(update);
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
