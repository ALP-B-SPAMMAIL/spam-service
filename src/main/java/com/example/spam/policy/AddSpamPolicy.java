package com.example.spam.policy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.spam.event.MailChangedToSpamEvent;
import com.example.spam.eventDto.MailChangedToSpamEventDto;
import com.example.spam.service.SpamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class AddSpamPolicy {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(AddSpamPolicy.class);

    @Autowired
    private SpamService spamService;

    
    @KafkaListener(topics = "mail", groupId = "mail-spam-mail-changed-to-spam")
    public void listen(
            @Header(value = "type", required = false) String type,
            @Payload String data
    ) {
        objectMapper.registerModule(new JavaTimeModule());
        if (type != null && type.equals("MailChangedToSpamEvent")) {
            logger.info("aaaaaaaa!");

            try {
                MailChangedToSpamEvent event = objectMapper.readValue(data, MailChangedToSpamEvent.class);
                MailChangedToSpamEventDto payload = event.getPayload();

                if (payload != null) {
                    logger.info("bbbbbbbbbbb!");
                    spamService.addSpam(payload);
                } else {
                    logger.info("ccccccccccc!");
                    System.out.println("Warning: Payload is null");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}