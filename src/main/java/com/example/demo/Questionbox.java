package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "question")
@Data
public class Questionbox {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name = "SourcePhone", nullable = false)
	private String sourcephone;		// 提问的人
	@Column(name = "TargetPhone", nullable = false)
	private String targetphone;		// 被问的人
	@Column(name = "TargetName", nullable = false)
	private String targetname;
	@Column(name = "Question", nullable = false)
	private String question;
	@Column(name = "Answer", nullable = true)
	private String answer;
	@Column(name = "State", nullable = false)
	private String state;
	@Column(name = "QuestionTime", nullable = false)
	private String questiontime;
	@Column(name = "AnswerTime", nullable = true)
	private String answertime;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getSourcephone() {
		return sourcephone;
	}
	public void setSourcephone(String sourcephone) {
		this.sourcephone = sourcephone;
	}
	public String getTargetphone() {
		return targetphone;
	}
	public void setTargetphone(String targetphone) {
		this.targetphone = targetphone;
	}
	public String getTargetname() {
		return targetname;
	}
	public void setTargetname(String targetname) {
		this.targetname = targetname;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getQuestiontime() {
		return questiontime;
	}
	public void setQuestiontime(String questiontime) {
		this.questiontime = questiontime;
	}
	public String getAnswertime() {
		return answertime;
	}
	public void setAnswertime(String answertime) {
		this.answertime = answertime;
	}


}

