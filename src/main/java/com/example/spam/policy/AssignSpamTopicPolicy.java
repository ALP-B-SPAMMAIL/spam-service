package com.example.spam.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.spam.event.TopicExtractedEvent;
import com.example.spam.eventDto.TopicExtractedEventDto;
import com.example.spam.repository.SpamRepository;
import com.example.spam.service.SpamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class AssignSpamTopicPolicy {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SpamService spamService;

    
    @KafkaListener(topics = "mail", groupId = "ai-spam-topic-extracted")
    public void listen(
            @Header(value = "type", required = false) String type,
            @Payload String data
    ) {
        objectMapper.registerModule(new JavaTimeModule());
        if (type != null && type.equals("TopicExtractedEvent")) {
            try {
                TopicExtractedEvent event = objectMapper.readValue(data, TopicExtractedEvent.class);
                TopicExtractedEventDto payload = event.getPayload();
                if (payload != null) {
                    spamService.assignSpamTopic(payload);
                } else {
                    System.out.println("Warning: Payload is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
