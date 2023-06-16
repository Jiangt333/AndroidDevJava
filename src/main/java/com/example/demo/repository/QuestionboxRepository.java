package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Questionbox;

@Repository
public interface QuestionboxRepository extends JpaRepository<Questionbox, Long>{
	public List<Questionbox> findByTargetphoneAndState(String targetphone, String state);
	public List<Questionbox> findBySourcephoneAndState(String sourcephone, String state);
	public void deleteById(int id);
}

