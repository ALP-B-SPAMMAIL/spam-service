package com.example.spam.eventDto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class MailChangedToNormalEventDto extends AbstractDto {
    private int mailId;
}
