package com.kevin.token.service;

import org.springframework.stereotype.Service;

import com.kevin.token.entity.KevinUser;

@Service
public class UserService {

	public KevinUser findByUsername(String username) {
		if("kevin".equals(username)) {
			KevinUser ku = new KevinUser("kevin", 26);
			ku.setPassword("123");
			return ku;
		}
		return null;
	}
	
	

}
