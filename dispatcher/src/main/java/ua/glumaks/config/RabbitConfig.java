package ua.glumaks.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static ua.glumaks.RabbitQueue.*;

@Configuration
public class RabbitConfig {

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    Queue textMessageQueue() {
        return new Queue(TEXT_MESSAGE);
    }

    @Bean
    Queue docMessageQueue() {
        return new Queue(DOC_MESSAGE);
    }

    @Bean
    Queue photoMessageQueue() {
        return new Queue(PHOTO_MESSAGE);
    }

    @Bean
    Queue answerMessageQueue() {
        return new Queue(ANSWER_BOT_API_METHOD);
    }

}
