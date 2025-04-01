package com.example.spam.eventDto;

import java.time.LocalDateTime;

import com.example.spam.model.Spam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpamDetectedEventDto extends AbstractDto {
    private int mailId;

    public SpamDetectedEventDto(Spam spam) {
        this.mailId = spam.getMailId();
    }
}
