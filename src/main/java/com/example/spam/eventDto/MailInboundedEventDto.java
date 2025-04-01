package com.example.spam.eventDto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailInboundedEventDto extends AbstractDto {
    private int mailId;
    private String mailContent;
    private String mailSender;
    private LocalDateTime mailArrivalTime;
}
