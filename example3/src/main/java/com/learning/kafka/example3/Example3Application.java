package com.learning.kafka.example3;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class Example3Application {

	private static final String TOPIC_NAME = "users";

	public static void main(String[] args) {
		SpringApplication.run(Example3Application.class, args);
	}

	@Bean
	public NewTopic users() {
		return new NewTopic(TOPIC_NAME, 5, (short) 1);
	}

	@KafkaListener(topics = TOPIC_NAME)
	public void processRecord(String message){
		log.info("Consumed message :: {}",message);
	}


}
