# Multiple consumers

-   As seen in [example6](../example6), for multiple consumers we have to define beans with configuration of interest.
```aidl
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
```

-   configuring listeners with different configuration is very easy. configure the ```containerFactory```  with respective bean and its done. 
```aidl
    @KafkaListener(topics = TIME_TOPIC_NAME, containerFactory = "string-factory")
    public void processStringRecord(String message) {
        log.info("Consumed string message :: {}", message);
    }

    @KafkaListener(topics = MESSAGE_TOPIC_NAME, containerFactory = "message-factory")
    public void processMessageRecord(Message message) {
        log.info("Consumed message message :: {}", message);
    }

```