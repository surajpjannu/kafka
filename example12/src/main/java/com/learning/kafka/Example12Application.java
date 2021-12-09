package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example12Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    public static void main(String[] args) {
        SpringApplication.run(Example12Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("====================================");
        this.template.send("topic4", "fail");
    }

    @RetryableTopic(attempts = "5", backoff = @Backoff(delay = 2_000, maxDelay = 10_000, multiplier = 2))
    @KafkaListener(groupId = "fooGroup", topics = "topic4")
    public void consumeMessage(ConsumerRecord<String, String> consumerRecord) {
        log.info("===================================");
        log.info("Time :: {}", LocalDateTime.now());
        final String input = consumerRecord.value();
        log.info("value :: " + input);
        log.info("partition :: " + consumerRecord.partition());
        log.info("offset :: " + consumerRecord.offset());
        log.info("topic :: " + consumerRecord.topic());
        log.info("===================================");
        if (input.startsWith("fail")) {
            throw new RuntimeException("failed");
        }
    }

    @DltHandler
    public void listenDlt(ConsumerRecord<String, String> consumerRecord) {
        log.info("=============== DLT Handler ====================");
        log.info("Time :: {}", LocalDateTime.now());
        final String input = consumerRecord.value();
        log.info("value :: " + input);
        log.info("partition :: " + consumerRecord.partition());
        log.info("offset :: " + consumerRecord.offset());
        log.info("topic :: " + consumerRecord.topic());
        log.info("===================================");
    }


}
