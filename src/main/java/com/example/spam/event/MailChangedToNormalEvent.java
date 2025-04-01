package com.example.spam.event;

import com.example.spam.eventDto.MailChangedToNormalEventDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailChangedToNormalEvent extends AbstractEvent {
    private MailChangedToNormalEventDto payload;
    
    // Default constructor for Jackson deserialization
    public MailChangedToNormalEvent() {
        super();
    }

    public MailChangedToNormalEvent(MailChangedToNormalEventDto mailChangedToNormalEventDto) {
        super(mailChangedToNormalEventDto);
        this.payload = mailChangedToNormalEventDto;
    }
}