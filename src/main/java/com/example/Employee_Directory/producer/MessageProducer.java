package com.example.Employee_Directory.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "test-topic";

    public MessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void sendMessage(String message) {
//        kafkaTemplate.send(TOPIC, message);
//        System.out.println("Produced message: " + message);
//    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message)
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
        kafkaTemplate.send(TOPIC, key, message)
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
