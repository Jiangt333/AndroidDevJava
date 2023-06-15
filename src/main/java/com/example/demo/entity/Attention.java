package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "myattention")
@Data
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "TargetPhone", nullable = false)
    private String target;//被关注的对象
    @Column(name = "SourcePhone", nullable = false)
    private String source;//关注的对象
	@Column(name = "TargetName", nullable = false)
	private String targetname;//被关注的对象昵称
	@Column(name = "SourceName", nullable = false)
	private String sourcename;//关注的对象昵称
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTargetName() {
		return targetname;
	}
	public void setTargetName(String targetname) {
		this.targetname = targetname;
	}
	public String getSourceName() {
		return sourcename;
	}
	public void setSourceName(String sourcename) {
		this.sourcename = sourcename;
	}
	
}
