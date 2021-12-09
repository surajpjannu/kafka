# Custom producer

-   We know that for sending message we use ```KafkaTemplate``` 
```aidl
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
```
-   This example is the same replica of [example2](../example2) Only difference is that we moved configuration from application.yaml to Java code and instead of sending String message We are sending Json message.
