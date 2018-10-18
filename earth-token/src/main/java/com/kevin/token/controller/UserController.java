package com.kevin.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kevin.token.entity.KevinUser;
import com.kevin.token.utils.JWTUtil;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@RequestMapping(method = RequestMethod.GET)
	public JSONObject list(HttpServletRequest req) {
		String token = req.getParameter("token");
		System.out.println("通过 token 获取到的信息 : " + JWTUtil.validateToken(token));
		JSONObject json = new JSONObject();
		
		KevinUser ku = new KevinUser("kevin", 26);
		json.put("entity", ku);
		
		return json;
	}
}
