package com.example.spam.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "spam", groupId = "spam")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}
