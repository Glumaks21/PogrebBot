package ua.glumaks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class EmailConfig {

    @Value("${spring.mail.username}")
    private String from;

}
