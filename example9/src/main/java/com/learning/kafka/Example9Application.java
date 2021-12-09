package com.learning.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class Example9Application implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "example9";

    public static void main(String[] args) {
        SpringApplication.run(Example9Application.class, args);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(TOPIC, 5, (short) 1);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            sendMessage(kafkaTemplate, TOPIC);
            Thread.sleep(1000);
        }
    }

    @KafkaListener(topics = TOPIC,groupId = "example9-group")
    public void consumeRecord(ConsumerRecord<String, String> consumerRecord) {
        final String topic = consumerRecord.topic();
        final String key = consumerRecord.key();
        final String value = consumerRecord.value();
        final long offset = consumerRecord.offset();
        final int partition = consumerRecord.partition();
        final Headers headers = consumerRecord.headers();
        final long timestamp = consumerRecord.timestamp();
        final TimestampType timestampType = consumerRecord.timestampType();

        final LocalDateTime timestampLocalDateTime = new Timestamp(timestamp).toLocalDateTime();
        log.info("==============================");
        log.info("topic :: " + topic);
        log.info("Key :: " + key);
        log.info("Value :: " + value);
        log.info("timestampLocalDateTime :: " + timestampLocalDateTime);
        log.info("Current Time :: " + LocalDateTime.now());
        log.info("Offset :: " + offset);
        log.info("Partition :: " + partition);
        log.info("headers :: " + headers);
        log.info("timestamp :: " + timestamp);
        log.info("timestampType :: " + timestampType);
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
