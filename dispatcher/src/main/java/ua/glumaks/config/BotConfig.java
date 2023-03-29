package ua.glumaks.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class BotConfig {

    private final BotConfigurationProperties properties;


    @Bean
    SetWebhook setWebhook() {
        return SetWebhook.builder()
                .url(properties.getUrl())
                .build();
    }

}
