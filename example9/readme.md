# Consumer Record

-   ConsumerRecord<K,V> is a generalized object class using which we can access all the required details about the message.
```aidl
    @KafkaListener(topics = TOPIC)
    public void consumeRecord(ConsumerRecord<String, String> consumerRecord) {
        final String topic = consumerRecord.topic();
        final String key = consumerRecord.key();
        final String value = consumerRecord.value();
        final long offset = consumerRecord.offset();
        final int partition = consumerRecord.partition();
        final Headers headers = consumerRecord.headers();
        final long timestamp = consumerRecord.timestamp();
        final TimestampType timestampType = consumerRecord.timestampType();

        final LocalDateTime timestampLocalDateTime = new Timestamp(timestamp).toLocalDateTime();
        log.info("==============================");
        log.info("topic :: " + topic);
        log.info("Key :: " + key);
        log.info("Value :: " + value);
        log.info("timestampLocalDateTime :: " + timestampLocalDateTime);
        log.info("Offset :: " + offset);
        log.info("Partition :: " + partition);
        log.info("headers :: " + headers);
        log.info("timestamp :: " + timestamp);
        log.info("timestampType :: " + timestampType);
    }
```

-   Key - key value sent by producer
-   value - value sent by producer
-   topic - topic from which message is consumed
-   partition - partition which is responsible for this message
-   offset - offset of message in partition
-   timestamp - timestamp at which this message is consumed by kafka cluster
-   headers - headers sent by producer

-   After running the application checks the logs and find the difference is value and timestampLocalDateTime.
-   the value will hold the timestamp sent by producer
-   timestampLocalDateTime holds the timestamp received by kafka cluster 
-   the difference between these 2 numbers are almost 0. which indicates kafka is very fast.
