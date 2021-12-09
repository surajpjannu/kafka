package com.learning.kafka.example1;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class Example1Application {

    public static void main(String[] args) {
        SpringApplication.run(Example1Application.class, args);
    }

    /*
    * Create Topic if not exist
    * */
    @Bean
    public NewTopic topicWithNewTopicFormat() {
        return new NewTopic("new-topic", 1, (short) 1);
    }

    @Bean
    public NewTopic topicWithTopicBuilder() {
        return TopicBuilder
                .name("topic-builder-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
