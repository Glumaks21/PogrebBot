package ua.glumaks.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ua.glumaks.controller.MyTelegramBot;

@Configuration
public class TelegramConfig {

    @Bean
    CommandLineRunner commandLineRunner(MyTelegramBot bot) {
        return args -> {
            try {
                TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
                telegramBotsApi.registerBot(bot);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        };
    }

}
