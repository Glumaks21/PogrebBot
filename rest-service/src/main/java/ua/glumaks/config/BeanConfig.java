package ua.glumaks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.glumaks.utils.CryptoUtil;

@Configuration
public class BeanConfig {

    @Bean
    CryptoUtil cryptoUtil() {
        return new CryptoUtil();
    }

}
