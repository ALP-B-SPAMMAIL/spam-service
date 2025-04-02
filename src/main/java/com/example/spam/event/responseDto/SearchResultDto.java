package com.example.spam.event.responseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResultDto {
    public String sender;
    public Long count;
    public String topic;
    public String reason;
    public String mail;
}
