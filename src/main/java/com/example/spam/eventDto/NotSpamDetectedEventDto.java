package com.example.spam.eventDto;

import com.example.spam.model.Spam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotSpamDetectedEventDto extends AbstractDto {
    private int mailId;

    public NotSpamDetectedEventDto(int mailId) {
        this.mailId = mailId;
    }
}
