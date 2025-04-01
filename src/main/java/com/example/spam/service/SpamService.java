package com.example.spam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spam.event.NotSpamDetectedEvent;
import com.example.spam.event.SpamDetectedEvent;
import com.example.spam.eventDto.MailChangedToNormalEventDto;
import com.example.spam.eventDto.MailChangedToSpamEventDto;
import com.example.spam.eventDto.MailInboundedEventDto;
import com.example.spam.eventDto.NotSpamDetectedEventDto;
import com.example.spam.eventDto.SpamDetectedEventDto;
import com.example.spam.eventDto.TopicExtractedEventDto;
import com.example.spam.kafka.KafkaProducer;
import com.example.spam.model.Spam;
import com.example.spam.repository.SpamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;

@Service
public class SpamService {
    @Autowired
    private SpamRepository spamRepository;
    @Autowired
    private KafkaProducer kafkaProducer;

    @Transactional
    public boolean isSpam(MailInboundedEventDto mailInboundedEventDto) {
            try {        
                System.out.println("isSpam called");
                System.out.println(mailInboundedEventDto.getMailSender());
                System.out.println(mailInboundedEventDto.getMailContent());
                if (mailInboundedEventDto.getMailSender().contains("bhdj0107")) {
                Spam spam = Spam.builder()
                .mailId(mailInboundedEventDto.getMailId())
                .sender(mailInboundedEventDto.getMailSender())
                .mailContent(mailInboundedEventDto.getMailContent())
                .whenArrived(mailInboundedEventDto.getMailArrivalTime())
                .build();
                spamRepository.save(spam);
                
                SpamDetectedEvent spamDetectedEvent = new SpamDetectedEvent(new SpamDetectedEventDto(spam));
                kafkaProducer.publish(spamDetectedEvent);
                return true;
                } else {
                    NotSpamDetectedEvent notSpamDetectedEvent = new NotSpamDetectedEvent(new NotSpamDetectedEventDto(mailInboundedEventDto.getMailId()));
                    kafkaProducer.publish(notSpamDetectedEvent);
                    return false;
                }
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    @Transactional
    public void assignSpamTopic(TopicExtractedEventDto topicExtractedEventDto) {
        Spam spam = spamRepository.findById(topicExtractedEventDto.getMailId()).orElse(null);
        if (spam != null) {
            spam.setTopic(topicExtractedEventDto.getTopic());
            spamRepository.save(spam);
        }
    }

    @Transactional
    public void deleteSpam(MailChangedToNormalEventDto mailChangedToNormalEventDto) {
        Spam spam = spamRepository.findById(mailChangedToNormalEventDto.getMailId()).orElse(null);
        if (spam != null) {
            spamRepository.delete(spam);
        }
    }

    @Transactional
    public void addSpam(MailChangedToSpamEventDto mailChangedToSpamEventDto) {
        Spam spam = spamRepository.findById(mailChangedToSpamEventDto.getMailId()).orElse(null);
        if (spam != null) {
            spam.setReason(mailChangedToSpamEventDto.getReason());
            spamRepository.save(spam);
        }
    }
}
