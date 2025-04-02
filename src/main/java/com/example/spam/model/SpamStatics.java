package com.example.spam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpamStatics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mailId;
    private String sender;
    private Long count;
    private String topic;
    private String reason;
    private String mail;

    public SpamStatics(Integer mailId){
        this.mailId=mailId;
    }
}
