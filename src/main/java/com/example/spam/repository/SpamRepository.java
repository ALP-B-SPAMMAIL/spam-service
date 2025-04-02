package com.example.spam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spam.model.Spam;

@Repository
public interface SpamRepository extends JpaRepository<Spam, Integer> {
    
}
