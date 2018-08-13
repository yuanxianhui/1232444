package com.bolue.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/baseinfo/enum/")
public class EnumerController {

	@RequestMapping(value="list")
	public String list(Model model) {
		model.addAttribute("dd", "等等");
		return "/enumer/list";
	}
	
	@RequestMapping(value="popup")
	public String popup() {
		System.out.println("909090");
		return "enumer/popup";
	}
}
