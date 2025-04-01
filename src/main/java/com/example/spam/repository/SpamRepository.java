package com.example.spam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spam.model.Spam;

public interface SpamRepository extends JpaRepository<Spam, Integer> {
    
}
