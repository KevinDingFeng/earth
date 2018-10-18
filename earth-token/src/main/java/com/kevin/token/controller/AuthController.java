package com.kevin.token.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kevin.token.entity.KevinUser;
import com.kevin.token.service.UserService;
import com.kevin.token.utils.JWTUtil;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public JSONObject auth(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {
		//TODO 检查用户是否已登录
		//TODO 检查登录信息是否完整
		JSONObject json = new JSONObject();
		KevinUser ku = userService.findByUsername(username);
		if(ku != null) {
			if(ku.getPassword().equals(password)) {
				json.put("code", 200);
				json.put("token", JWTUtil.generateToken(username, new Date(System.currentTimeMillis())));
			}else {
				json.put("code", 401);
			}
		}else {
			json.put("code", 402);
		}
		return json;
	}
}
