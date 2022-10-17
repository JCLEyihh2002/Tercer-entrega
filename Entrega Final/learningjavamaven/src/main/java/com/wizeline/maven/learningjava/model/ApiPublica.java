package com.wizeline.maven.learningjava.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;


public class ApiPublica implements Serializable {

    private Integer userId;
    private String name;
    private String lastName;
    private String numberPhone;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNumberPhone() {
		return numberPhone;
	}
	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

    																																																																																																																																																																																		

}
