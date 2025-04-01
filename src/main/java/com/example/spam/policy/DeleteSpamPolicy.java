package com.example.spam.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.example.spam.event.MailChangedToNormalEvent;
import com.example.spam.eventDto.MailChangedToNormalEventDto;
import com.example.spam.service.SpamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class DeleteSpamPolicy {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SpamService spamService;

    
    @KafkaListener(topics = "mail", groupId = "mail-spam-mail-changed-to-normal")
    public void listen(
            @Header(value = "type", required = false) String type,
            @Payload String data
    ) {
        objectMapper.registerModule(new JavaTimeModule());
        if (type != null && type.equals("MailChangedToNormalEvent")) {
            try {
                MailChangedToNormalEvent event = objectMapper.readValue(data, MailChangedToNormalEvent.class);
                MailChangedToNormalEventDto payload = event.getPayload();
                if (payload != null) {
                    spamService.deleteSpam(payload);
                } else {
                    System.out.println("Warning: Payload is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
