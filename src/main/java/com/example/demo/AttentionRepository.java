package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Integer> {

    List<Attention> findBytarget(String target);
    List<Attention> findBysource(String source);
}
