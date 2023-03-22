package ua.glumaks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TelegramBotConfigurationProperties {

    @Value("${telegram.bot.name}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

}
