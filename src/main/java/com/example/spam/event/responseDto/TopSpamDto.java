package com.example.spam.event.responseDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopSpamDto {
    public List<SearchResultDto> searchResultDto;
}
