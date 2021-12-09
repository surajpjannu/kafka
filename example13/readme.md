# Kafka Listener Error Handler

-   Consider that you faced some exception during processing any message. You need to handle that scenario as well.
-   So ```KafkaListener``` comes with ```errorHandler``` configuration which helps to handle the failed messages
-   So ```KafkaListener``` needs a ```KafkaListenerErrorHandler``` bean for errorHandling.
```aidl
    @Bean
    public KafkaListenerErrorHandler kafkaListenerErrorHandler1() {
        return new KafkaListenerErrorHandler() {
            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
                return null;
            }
        };
    }
```

