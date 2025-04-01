package com.example.spam.policy;

import com.example.spam.event.MailInboundedEvent;
import com.example.spam.eventDto.MailInboundedEventDto;
import com.example.spam.model.Spam;
import com.example.spam.repository.SpamRepository;
import com.example.spam.service.SpamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class MailInboundedPolicy {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SpamService spamService;

    @Autowired
    private SpamRepository spamRepository;
    
    @KafkaListener(topics = "mail", groupId = "mail-spam-mail-inbounded")
    public void listen(
            @Header(value = "type", required = false) String type,
            @Payload String data
    ) {
        objectMapper.registerModule(new JavaTimeModule());
        if (type != null && type.equals("MailInboundedEvent")) {
            try {
                System.out.println("MailInboundedEvent received");
                MailInboundedEvent event = objectMapper.readValue(data, MailInboundedEvent.class);
                MailInboundedEventDto payload = event.getPayload();
                if (payload != null) {
                    spamService.isSpam(payload);
                } else {
                    System.out.println("Warning: Payload is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
    
