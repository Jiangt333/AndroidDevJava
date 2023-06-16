package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "Username", nullable = false)
    private String name;
    // Getters and setters
    @Column(name = "Password", nullable = false)
    private String realpassword;
    @Column(name = "Phone", nullable = false)
    private String phone;
    @Column(name = "isChanged", nullable = false)
    private int ischanged;
	public int getId() {
		return Id;
	}
	public int getIschanged() {
		return ischanged;
	}
	public void setIschanged(int ischanged) {
		this.ischanged = ischanged;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealpassword() {
		return realpassword;
	}
	public void setRealpassword(String realpassword) {
		this.realpassword = realpassword;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}