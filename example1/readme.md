# Ways of creating topic

1. Using NewTopic
```aidl
    @Bean
    public NewTopic topicWithNewTopicFormat() {
        return new NewTopic("new-topic", 1, (short) 1);
    }
```

2. Using TopicBuilder
```aidl
    @Bean
    public NewTopic topicWithTopicBuilder() {
        return TopicBuilder
                .name("topic-builder-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
```

- Notes
  - Number of partitions can be increased cannot be decreased once assigned