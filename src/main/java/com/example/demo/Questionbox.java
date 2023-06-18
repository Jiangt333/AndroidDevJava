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
	public String getSourcePhone() {
		return sourcephone;
	}
	public void setSourcePhone(String sourcephone) {
		this.sourcephone = sourcephone;
	}
	public String getTargetPhone() {
		return targetphone;
	}
	public void setTargetPhone(String targetphone) {
		this.targetphone = targetphone;
	}
	public String getTargetName() {
		return targetname;
	}
	public void setTargetName(String targetname) {
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
	public String getQuestionTime() {
		return questiontime;
	}
	public void setQuestionTime(String questiontime) {
		this.questiontime = questiontime;
	}
	public String getAnswerTime() {
		return answertime;
	}
	public void setAnswerTime(String answertime) {
		this.answertime = answertime;
	}
}

