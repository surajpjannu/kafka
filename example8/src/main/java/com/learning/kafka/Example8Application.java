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
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example8Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC_1 = "topic1";
    private static final String TOPIC_2 = "topic2";

    public static void main(String[] args) {
        SpringApplication.run(Example8Application.class, args);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(TOPIC_1, 5, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(TOPIC_2, 5, (short) 1);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            sendMessage(kafkaTemplate, TOPIC_1);
            sendMessage(kafkaTemplate, TOPIC_2);
            Thread.sleep(1000);
        }
    }

    @KafkaListener(topics = {TOPIC_1, TOPIC_2})
    public void consumeRecord(String messsage) {
        log.info(messsage);
    }

    private void sendMessage(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        final String currentTime = topic + " message :: " + LocalDateTime.now().toString();
        final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, currentTime);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Message sending failed for topic {} with reason {}", topic, ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
//                log.error("Message sent successfully to topic {}", topic);
            }
        });
    }

}
