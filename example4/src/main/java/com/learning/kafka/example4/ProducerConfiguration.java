package com.learning.kafka.example4;

import com.learning.kafka.Message;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class ProducerConfiguration {

    public static final String TOPIC_NAME = "Message";

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        final HashMap<String, Object> producerConfigurations = new HashMap<>();
        producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfigurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        final ProducerFactory<String, Message> producerFactory =
                new DefaultKafkaProducerFactory<>(producerConfigurations);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(TOPIC_NAME).partitions(5).replicas(1).build();
    }

}
