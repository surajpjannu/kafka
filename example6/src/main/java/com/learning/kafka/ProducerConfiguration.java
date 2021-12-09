package com.learning.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ProducerConfiguration {

    @Bean("string-producer")
    @Primary
    public KafkaTemplate<String, String> defaultKafkaTemplate() {
        final HashMap<String, Object> producerConfigurations = new HashMap<>();
        producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerConfigurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerConfigurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        final ProducerFactory<String, String> producerFactory =
                new DefaultKafkaProducerFactory<>(producerConfigurations);
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean("message-producer")
    public KafkaTemplate<String, Message> messageKafkaTemplate() {
        final HashMap<String, Object> producerConfigurations = new HashMap<>();
        producerConfigurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        final TypeReference<Message> typeReference = new TypeReference<Message>() {
        };
        final JsonSerializer<Message> messageSerializer = new JsonSerializer<>(typeReference);
        final ProducerFactory<String, Message> producerFactory =
                new DefaultKafkaProducerFactory<>(producerConfigurations, new StringSerializer(), messageSerializer);
        return new KafkaTemplate<>(producerFactory);
    }


}
