package com.learning.kafka.example5;

import com.learning.kafka.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class Example5Application {

    public static void main(String[] args) {
        SpringApplication.run(Example5Application.class, args);
    }

    @KafkaListener(topics = ConsumerListeners.TOPIC_NAME)
    public void consumeMessage(Message message) {
        log.info("Consumed message :: {}", message);
    }

}
