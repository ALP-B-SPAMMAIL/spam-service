package com.example.spam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spam.model.SpamStatics;

@Repository
public interface SpamStaticsRepository extends JpaRepository<SpamStatics, Integer>{
    Optional<SpamStatics> findBySender(String sender);
}
