package com.example.Employee_Directory.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer {

    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String topic;

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void sendMessage(String message) {
//        kafkaTemplate.send(topic, message);
//        System.out.println("Produced message: " + message);
//    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Message sent: " + message +
                                " to partition " + result.getRecordMetadata().partition() +
                                " with offset " + result.getRecordMetadata().offset());
                    } else {
                        System.err.println(" Failed to send message: " + message + " due to " + ex.getMessage());
                    }
                });
    }

    public void sendMessageWithKey(String key, String message) {
        kafkaTemplate.send(topic, key, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Message sent with key [" + key + "]: " + message +
                                " to partition " + result.getRecordMetadata().partition() +
                                " with offset " + result.getRecordMetadata().offset());
                    } else {
                        System.err.println(" Failed to send message with key [" + key + "]: " + message +
                                " due to " + ex.getMessage());
                    }
                });
    }
}
