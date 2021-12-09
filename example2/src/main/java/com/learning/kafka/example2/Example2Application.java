package com.learning.kafka.example2;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example2Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_NAME = "users";

    public static void main(String[] args) {
        SpringApplication.run(Example2Application.class, args);
    }

    @Bean
    public NewTopic users() {
        return new NewTopic(TOPIC_NAME, 5, (short) 1);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            final String currentTime = LocalDateTime.now().toString();
            final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_NAME, currentTime);
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error("Message sending failed for topic {} with reason {}", TOPIC_NAME, ex.getMessage(), ex);
                }

                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.error("Message sent successfully to topic {}", TOPIC_NAME);
                }
            });
            Thread.sleep(1000);
        }
    }
}
