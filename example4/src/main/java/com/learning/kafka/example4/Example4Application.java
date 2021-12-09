package com.learning.kafka.example4;

import com.learning.kafka.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example4Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Example4Application.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        while (true) {
            final ListenableFuture<SendResult<String, Message>> future = kafkaTemplate.send(ProducerConfiguration.TOPIC_NAME, new Message("Current time is ::" + LocalDateTime.now().toString()));
            future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {
                @Override
                public void onSuccess(SendResult<String, Message> result) {
                    log.info("Message sent successfully to topic {}", ProducerConfiguration.TOPIC_NAME);
                }

                @Override
                public void onFailure(Throwable ex) {
                    log.error("Message sending failed for topic {} with reason {}", ProducerConfiguration.TOPIC_NAME, ex.getMessage(), ex);
                }
            });
            Thread.sleep(5000);
        }
    }
}
