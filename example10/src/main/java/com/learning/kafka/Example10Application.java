package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@Slf4j
public class Example10Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "example10";

    public static void main(String[] args) {
        SpringApplication.run(Example10Application.class, args);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(TOPIC, 5, (short) 1);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            kafkaTemplate.send(TOPIC, LocalDateTime.now().toString());
        }
    }

    @KafkaListener(topics = TOPIC, groupId = "example10")
    public void processMessageRecord2(List<String> messages) {
        log.info("Messages count {}", messages.size());
    }

}
