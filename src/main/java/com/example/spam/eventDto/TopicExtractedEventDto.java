package com.example.spam.eventDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicExtractedEventDto extends AbstractDto {
    private int mailId;
    private String topic;
}
