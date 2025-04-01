package com.example.spam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Spam {
    @Id
    private int mailId;

    private String topic;
    private String sender;
    @Lob
    private String mailContent;
    private LocalDateTime whenArrived;

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
