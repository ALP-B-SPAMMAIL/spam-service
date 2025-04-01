package com.example.spam.event;

import com.example.spam.eventDto.NotSpamDetectedEventDto;
import com.example.spam.eventDto.NotSpamDetectedEventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotSpamDetectedEvent extends AbstractEvent {
    private NotSpamDetectedEventDto payload;

    public NotSpamDetectedEvent() {
        super();
        this.topic = "spam";
    }

    public NotSpamDetectedEvent(NotSpamDetectedEventDto notSpamDetectedEventDto) {
        super(notSpamDetectedEventDto);
        this.topic = "spam";
        this.payload = notSpamDetectedEventDto;
    }
}
