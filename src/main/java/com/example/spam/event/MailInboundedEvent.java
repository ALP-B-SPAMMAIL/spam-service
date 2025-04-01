package com.example.spam.event;

import com.example.spam.eventDto.MailInboundedEventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailInboundedEvent extends AbstractEvent {
    private MailInboundedEventDto payload;

    public MailInboundedEvent() {
        super();
    }

    public MailInboundedEvent(MailInboundedEventDto mailInboundedEventDto) {
        super(mailInboundedEventDto);
        this.payload = mailInboundedEventDto;
    }
}
