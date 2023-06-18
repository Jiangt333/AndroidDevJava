package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.Questionbox;

@Repository
public interface QuestionboxRepository extends JpaRepository<Questionbox, Long>{
	public Questionbox findById(int id);
	public List<Questionbox> findByTargetphoneAndState(String targetphone, String state);
	public List<Questionbox> findBySourcephoneAndState(String sourcephone, String state);
	public void deleteById(int id);

//	@Query(value = "update QuestionBox qbox set qbox.answer=?1 where id=?2", nativeQuery = true)
//	@Modifying
//	public void updateById(String answer, int id);
}

