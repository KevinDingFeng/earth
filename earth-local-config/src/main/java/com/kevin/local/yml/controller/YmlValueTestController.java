package com.kevin.local.yml.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping(value = "/")
public class YmlValueTestController {

	@Value("${kevin.msg}")
	private String msg;
	
	@RequestMapping(method = RequestMethod.GET)
	public JSONObject readValueInYml() {
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		return json;
	}
}
