package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@Slf4j
public class Example7Application {

    public static final String TIME_TOPIC_NAME = "Time";
    public static final String MESSAGE_TOPIC_NAME = "Message2";

    public static void main(String[] args) {
        SpringApplication.run(Example7Application.class, args);
    }

    @Bean
    public NewTopic timeTopic() {
        return TopicBuilder.name(TIME_TOPIC_NAME).partitions(5).replicas(1).build();
    }

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(MESSAGE_TOPIC_NAME).partitions(5).replicas(1).build();
    }

    @KafkaListener(topics = TIME_TOPIC_NAME, containerFactory = "string-factory")
    public void processStringRecord(String message) {
        log.info("Consumed string message :: {}", message);
    }

    @KafkaListener(topics = MESSAGE_TOPIC_NAME, containerFactory = "message-factory")
    public void processMessageRecord(Message message) {
        log.info("Consumed message message :: {}", message);
    }


}
