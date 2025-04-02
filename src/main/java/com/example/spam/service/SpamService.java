package com.example.spam.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.kafka.common.annotation.InterfaceStability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spam.event.NotSpamDetectedEvent;
import com.example.spam.event.SpamDetectedEvent;
import com.example.spam.event.requestDto.SearchAskDto;
import com.example.spam.event.responseDto.SearchResultDto;
import com.example.spam.eventDto.MailChangedToNormalEventDto;
import com.example.spam.eventDto.MailChangedToSpamEventDto;
import com.example.spam.eventDto.MailInboundedEventDto;
import com.example.spam.eventDto.NotSpamDetectedEventDto;
import com.example.spam.eventDto.SpamDetectedEventDto;
import com.example.spam.eventDto.TopicExtractedEventDto;
import com.example.spam.kafka.KafkaProducer;
import com.example.spam.model.Mail;
import com.example.spam.model.Spam;
import com.example.spam.model.SpamStatics;
import com.example.spam.policy.AddSpamPolicy;
import com.example.spam.repository.MailRepository;
import com.example.spam.repository.SpamRepository;
import com.example.spam.repository.SpamStaticsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
public class SpamService {
    @Autowired
    private SpamRepository spamRepository;
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private SpamStaticsRepository spamStaticsRepository;
    @Autowired
    private MailRepository mailRepository;

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
        Mail mail = mailRepository.findById(mailChangedToSpamEventDto.getMailId()).orElse(null);
        if (mail != null) {
            Spam spam = new Spam(mail.getMailId());
            spam.setTopic(mail.getMailTopic());
            spam.setMailContent(mail.getMailContent());
            spam.setSender(mail.getMailSender());
            spam.setReason(mailChangedToSpamEventDto.getReason());

            spamRepository.save(spam);

            // String totalSender = mailChangedToSpamEventDto.getMailSender();
            // String[] senderSplit = totalSender.split(" ");
            // String senderName = senderSplit[0].trim();
            // String senderEmailT = senderSplit[1].trim();
            // String senderEmail = senderEmailT.replaceAll("[<>]", "");

            String totalSender = mailChangedToSpamEventDto.getMailSender();
            Matcher matcher = Pattern.compile("<(.*?)>").matcher(totalSender);

            String senderEmail = matcher.find() ? matcher.group(1) : "";

            Optional<SpamStatics> spamStatic = spamStaticsRepository.findBySender(senderEmail);
            //Optional<SpamStatics> spamStatic = spamStaticsRepository.findBySender(mailChangedToSpamEventDto.getMailSender());
            
            if(spamStatic.isPresent()){
                SpamStatics spamO = spamStatic.get();
                spamO.setCount(spamO.getCount()+1);
                spamO.setReason(spam.getReason());
                spamStaticsRepository.save(spamO);
            }
            else{
                SpamStatics spamN = new SpamStatics();
                spamN.setSender(mailChangedToSpamEventDto.getMailSender());
                spamN.setCount(1L);
                spamN.setReason(mailChangedToSpamEventDto.getReason());

                spamStaticsRepository.save(spamN);
            }

        }
    }

    @Transactional
    public SearchResultDto findSpam(SearchAskDto searchAskDto){
        // String totalSender = searchAskDto.getSender();
        // String[] senderSplit = totalSender.split(" ");
        // String senderName = senderSplit[0].trim();
        // String senderEmailT = senderSplit[1].trim();
        // String senderEmail = senderEmailT.replaceAll("[<>]", "");

        //Optional<SpamStatics> spamStatics = spamStaticsRepository.findBySender();

        Optional<SpamStatics> spamStatics = spamStaticsRepository.findBySender(searchAskDto.getSender());

        SearchResultDto searchResultDto = new SearchResultDto();
        
        if(spamStatics.isPresent()){
            SpamStatics spamm = spamStatics.get();
            searchResultDto.setCount(spamm.getCount());
            searchResultDto.setSender(spamm.getSender());
            searchResultDto.setTopic(spamm.getTopic());
            searchResultDto.setReason(spamm.getReason());
        }


        return searchResultDto;
    }
}
