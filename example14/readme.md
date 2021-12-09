# Forwarding Listener Results using @SendTo

-   https://docs.spring.io/spring-kafka/reference/html/#annotation-properties

-   incase if you want to read some message, process it and write the processed record into a new topic, you can go for ```@SendTo```
```aidl
    @KafkaListener(topics = TOPIC, groupId = TOPIC)
    @SendTo(value = SEND_TO_TOPIC) // Send Message to particular topic
    public String kafkaListener(String message) {
        return message;
    }
```

-   ```KafkaListener``` should return the response which needs to be published to new topic configured in ```@SendTo```
