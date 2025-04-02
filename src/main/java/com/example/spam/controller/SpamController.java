package com.example.spam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.spam.event.requestDto.SearchAskDto;
import com.example.spam.event.responseDto.SearchResultDto;
import com.example.spam.eventDto.MailChangedToSpamEventDto;
import com.example.spam.service.SpamService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.spam.event.responseDto.TopSpamDto;



@RestController
@RequiredArgsConstructor
public class SpamController {

    private final SpamService spamService;

    @PostMapping("/test")
    public ResponseEntity<String> postMethodName(@RequestBody MailChangedToSpamEventDto mailChangedToSpamEventDto) {
        spamService.addSpam(mailChangedToSpamEventDto);
        
        return ResponseEntity.ok("created");
    }
    
    @PostMapping("/search")
    public ResponseEntity<SearchResultDto> searchSpamStatics(@RequestBody SearchAskDto searchAskDto){
        SearchResultDto searchResultDto = spamService.findSpam(searchAskDto);

        return ResponseEntity.ok(searchResultDto);
    }

    @GetMapping("/top/spam")
    public ResponseEntity<TopSpamDto> getTopSpam() {
        TopSpamDto topSpamDto = spamService.findTopSpam();

        return ResponseEntity.ok(topSpamDto);
    }
    

}
