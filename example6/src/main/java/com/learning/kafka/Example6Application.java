package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example6Application implements CommandLineRunner {


    public static final String TIME_TOPIC_NAME = "Time";
    public static final String MESSAGE_TOPIC_NAME = "Message2";

    @Autowired
    @Qualifier("string-producer")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    @Qualifier("message-producer")
    private KafkaTemplate<String, Message> messageKafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Example6Application.class, args);
    }

    @Bean
    public NewTopic timeTopic() {
        return TopicBuilder.name(TIME_TOPIC_NAME).partitions(5).replicas(1).build();
    }

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(MESSAGE_TOPIC_NAME).partitions(5).replicas(1).build();
    }


    @Override
    public void run(String... args) throws Exception {
        while (true) {
            final ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TIME_TOPIC_NAME, "Current time is ::" + LocalDateTime.now());
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    log.info("Message sent successfully to topic {}", TIME_TOPIC_NAME);
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Message sending failed for topic {} with reason {}", TIME_TOPIC_NAME, ex.getMessage(), ex);
                }
            });

            final ListenableFuture<SendResult<String, Message>> messagefuture = messageKafkaTemplate.send(MESSAGE_TOPIC_NAME, new Message("Current time is ::" + LocalDateTime.now().toString()));
            messagefuture.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
                @Override
                public void onSuccess(SendResult<String, Message> result) {
                    log.info("Message sent successfully to topic {}", MESSAGE_TOPIC_NAME);
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Message sending failed for topic {} with reason {}", MESSAGE_TOPIC_NAME, ex.getMessage(), ex);
                }
            });
            Thread.sleep(5000);
        }
    }
}
