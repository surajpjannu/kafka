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


@SpringBootApplication
@Slf4j
public class Example13Application implements CommandLineRunner {

    private static final String TOPIC = "example13";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Example13Application.class, args);
    }

    @Bean
    public NewTopic example13() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            kafkaTemplate.send(TOPIC, "fail");
            Thread.sleep(10000);
        }
    }


    @KafkaListener(topics = TOPIC, groupId = "example13", errorHandler = "kafkaListenerErrorHandler")
    public void consume(String data) {
        if (data.startsWith("fail")) {
            throw new IllegalArgumentException("message contains fail text");
        } else {
            log.info("message :: " + data);
        }
    }

}
