package com.example.spam.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spam.model.SpamStatics;

@Repository
public interface SpamStaticsRepository extends JpaRepository<SpamStatics, Integer>{
    //Optional<SpamStatics> findBySender(String sender);

    @Query(value = "SELECT * FROM spam_statics WHERE REPLACE(SUBSTRING_INDEX(sender, '<', -1), '>', '') = :email", 
       nativeQuery = true)
    Optional<SpamStatics> findBySender(@Param("email") String email);

    List<SpamStatics> findByCountGreaterThanEqual(Long count);

    @Query(value = "SELECT * FROM spam_statics ORDER BY count DESC LIMIT 5", nativeQuery = true)
    List<SpamStatics> findTop5ByCount();

}
