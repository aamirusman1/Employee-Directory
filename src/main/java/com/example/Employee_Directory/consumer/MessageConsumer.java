package com.example.Employee_Directory.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "my-group")
    public void listen(String message) {
        System.out.println("Consumed message: " + message);
    }
}
