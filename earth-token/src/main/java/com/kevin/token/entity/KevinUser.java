package com.kevin.token.entity;

import lombok.Data;

@Data
public class KevinUser {
	
	private String username;
	
	private int age;
	
	private String password;
	
	public KevinUser() {
		
	}
	public KevinUser(String username, int age) {
		this.username = username;
		this.age = age;
	}
}
