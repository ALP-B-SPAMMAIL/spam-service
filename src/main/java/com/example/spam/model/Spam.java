package com.example.spam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    private Integer mailId;

    private String topic;
    private String sender;
    @Lob
    @Column(columnDefinition="LONGTEXT")
    private String mailContent;
    private LocalDateTime whenArrived;

    private String reason;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
