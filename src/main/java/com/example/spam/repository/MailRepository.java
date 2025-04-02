package com.example.spam.repository;

import java.util.Optional;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spam.model.Mail;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer>{
    Optional<Mail> findByMailId(Integer mailId);
}
