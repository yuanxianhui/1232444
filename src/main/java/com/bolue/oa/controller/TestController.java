package com.bolue.oa.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bolue.oa.model.UserLogin;
import com.bolue.oa.service.TestService;

@Controller
public class TestController {
	/*@Autowired
	private TestService testService;
	
	@RequestMapping(value="layouts")
	public String layouts() {
		return "layouts";
	}
	
	@RequestMapping(value="init")
	public String init(Model model) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		
		UserLogin user = new UserLogin();
		user.setId("909090");
		user.setName("花花");
		user.setSex("爷们");
		
		model.addAttribute("time", time);
		model.addAttribute("href", "index");
		model.addAttribute("user", user);
		
		return "hello";
	}
	
	@ResponseBody
	@RequestMapping(value="data")
	public JSONObject getData() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("val01", "你好啊");
		
		System.out.println("进来了");
		
		return jsonObject;
	}
	
	@ResponseBody
	@RequestMapping(value="data01")
	public List<UserLogin> getData01() {
		List<UserLogin> users = new ArrayList<UserLogin>();
		users = testService.user();
		UserLogin user = new UserLogin();
		user.setId("110");
		user.setName("花花世界");
		user.setSex("人妖");
		users.add(user);
		return users;
	}
	
	@ResponseBody
	@RequestMapping(value="wahaha")
	public List<UserLogin> wahaha() {
		List<UserLogin> users = new ArrayList<UserLogin>();
		users = testService.user();
		UserLogin user = new UserLogin();
		user.setId("110");
		user.setName("花花世界");
		user.setSex("人妖");
		users.add(user);
		return users;
	}
	
	@ResponseBody
	@RequestMapping(value="getData02")
	public List<UserLogin> getData02() {
		List<UserLogin> users = new ArrayList<UserLogin>();
		users = testService.user();
		return users;
	}*/
}
