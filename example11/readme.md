# Limiting batch size

-   Messages can be read in batches with one simple configuration
```aidl
spring:
  kafka:
    listener:
      type: batch
```

Or it can be handled in ```KafkaListener``` annotation with batch = "true"

-   this example is similar to [example10](../example10) with some additional configuration

-   We can limit batch size with the configuration in ```KafkaListener``` annotation
```aidl
    @KafkaListener(topics = TOPIC, groupId = "example11",batch = "true",properties = "max.poll.records=250")
    public void processMessageRecord(@Payload List<Message> messages) {
        log.info("Consumed messages count :: {}", messages.size());
    }
```