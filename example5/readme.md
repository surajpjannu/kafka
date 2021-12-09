# Custom Consumer

-   Consumer configuration can be done via ```ConsumerFactory``` and ```ConcurrentKafkaListenerContainerFactory```
```aidl
    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        final HashMap<String, Object> consumerConfiguration = new HashMap<>();

        consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, "messageGroupId");

        final JsonDeserializer<Message> jsonDeserializer = new JsonDeserializer<>(Message.class);
//        jsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(consumerConfiguration,new StringDeserializer(), jsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        final ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
        return kafkaListenerContainerFactory;
    }
```
-   This example is the same replica of [example3](../example3) Only difference is that we moved configuration from application.yaml to Java code and instead of consuming String message We are sending Json(POJO) message.
```aidl
    @KafkaListener(topics = ConsumerListeners.TOPIC_NAME)
    public void consumeMessage(Message message) {
        log.info("Consumed message :: {}", message);
    }
```


