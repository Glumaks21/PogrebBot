package ua.glumaks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.glumaks.utils.HashUtil;

@Configuration
public class BeanConfig {

    @Bean
    HashUtil hashUtil() {
        return new HashUtil();
    }

}
