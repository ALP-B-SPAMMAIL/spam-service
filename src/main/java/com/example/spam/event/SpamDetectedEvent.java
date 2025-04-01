package com.example.spam.event;

import com.example.spam.eventDto.SpamDetectedEventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpamDetectedEvent extends AbstractEvent {
    private SpamDetectedEventDto payload;

    public SpamDetectedEvent() {
        super();
        this.topic = "spam";
    }

    public SpamDetectedEvent(SpamDetectedEventDto spamDetectedEventDto) {
        super(spamDetectedEventDto);
        this.topic = "spam";
        this.payload = spamDetectedEventDto;
    }
}
