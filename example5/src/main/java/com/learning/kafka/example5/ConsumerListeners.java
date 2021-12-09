package com.learning.kafka.example5;

import com.learning.kafka.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class ConsumerListeners {

    public static final String TOPIC_NAME = "Message";

    @Bean
    public NewTopic messageTopic() {
        return TopicBuilder.name(ConsumerListeners.TOPIC_NAME).partitions(5).replicas(1).build();
    }

    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        final HashMap<String, Object> consumerConfiguration = new HashMap<>();

        consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, "messageGroupId");

        final JsonDeserializer<Message> jsonDeserializer = new JsonDeserializer<>(Message.class);
//        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfiguration, new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafkaListenerContainerFactory;
    }

}
