package com.example.spam.eventDto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MailChangedToSpamEventDto extends AbstractDto {
    private int mailId;
    private String reason;

}
