package com.example.demo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionboxRepository extends JpaRepository<Questionbox, Long>{

	public List<Questionbox> findBytarget(String target);
	public List<Questionbox> findBysource(String source);
}

