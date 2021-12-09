# Consume messages from a multiple topics with single listener

-   Its very easy as passing multiple arguments. pass the topics in curly braces as shown below in ```KafkaListener``` annotation
```aidl
    @KafkaListener(topics = {TOPIC_1, TOPIC_2})
    public void consumeRecord(String messsage) {
        log.info(messsage);
    }
```

