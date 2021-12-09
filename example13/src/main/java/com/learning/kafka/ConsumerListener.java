package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerListener {

    @Bean
    public KafkaListenerErrorHandler kafkaListenerErrorHandler() {
        return (message, exception) -> {
            log.error("message {} ::: exception {}", message.getPayload(), exception.getLocalizedMessage());
            return message;
        };
    }
}
