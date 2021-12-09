# Simple batch

-   Messages can be read in batches with one simple configuration
```aidl
spring:
  kafka:
    listener:
      type: batch
```

-   KafkaListener signature also should be updated accordingly
```aidl
    @KafkaListener(topics = TOPIC, groupId = "example10")
    public void processMessageRecord2(List<String> messages) {
        log.info("Messages count {}", messages.size());
    }
```
