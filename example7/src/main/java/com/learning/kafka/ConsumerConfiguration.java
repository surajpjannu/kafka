package com.learning.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class ConsumerConfiguration {

    public static final String string_groupId = "string-group-id";
    public static final String message_groupId = "message-group-id";

    public <K, V> ConsumerFactory<K, V> consumerFactory(String groupId, Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
        final HashMap<String, Object> consumerConfiguration = new HashMap<>();

        consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return new DefaultKafkaConsumerFactory<>(consumerConfiguration, keyDeserializer, valueDeserializer);
    }

    @Bean("string-factory")
    public ConcurrentKafkaListenerContainerFactory<String, String> stringKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory(string_groupId, new StringDeserializer(), new StringDeserializer()));
        return kafkaListenerContainerFactory;
    }

    @Bean("message-factory")
    public ConcurrentKafkaListenerContainerFactory<String, Message> messageKafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        final JsonDeserializer<Message> jsonDeserializer = new JsonDeserializer<>(Message.class);
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory(message_groupId, new StringDeserializer(), jsonDeserializer));
        return kafkaListenerContainerFactory;
    }

}
