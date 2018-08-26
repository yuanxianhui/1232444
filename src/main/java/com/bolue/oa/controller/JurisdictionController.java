package com.bolue.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/setsys/jurisdiction/")
public class JurisdictionController {

	@RequestMapping(value="list")
	public String list() {
		System.out.println("权限分配");
		
		
		return "jurisdiction/list";
	}
}
