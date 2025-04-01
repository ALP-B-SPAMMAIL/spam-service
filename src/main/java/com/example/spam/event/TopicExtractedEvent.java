package com.example.spam.event;

import com.example.spam.eventDto.TopicExtractedEventDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicExtractedEvent extends AbstractEvent {
    private TopicExtractedEventDto payload;
    public TopicExtractedEvent() {
        super();
    }

    public TopicExtractedEvent(TopicExtractedEventDto topicExtractedEventDto) {
        super(topicExtractedEventDto);
        this.payload = topicExtractedEventDto;
    }
}
