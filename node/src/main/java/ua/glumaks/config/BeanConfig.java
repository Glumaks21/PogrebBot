package ua.glumaks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ua.glumaks.utils.CryptoUtil;

@Configuration
public class BeanConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CryptoUtil hashUtil() {
        return new CryptoUtil();
    }

}
