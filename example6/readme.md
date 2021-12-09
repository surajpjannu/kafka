# Multiple producers

-   Configuring multiple producers is simple. Define the configuration of interest for each producers and use the respective bean for sending messages
```aidl
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
```

-   There is no much difference between [example4](../example4) and [example6](../example6). here we are learning the usecase(multiple producers) design with spring boot.