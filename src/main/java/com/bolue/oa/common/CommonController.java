package com.bolue.oa.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/common/")
public class CommonController {

	@Autowired
	private Common common;
	
	@ResponseBody
	@RequestMapping(value="province")
	public JSONObject province(){
		ResultDO<JSONObject> pac = common.getProvince();
		return pac.getModule();
	}
	
	@ResponseBody
	@RequestMapping(value="area")
	public JSONObject area(String cityid){
		ResultDO<JSONObject> pac = common.getArea(cityid);
		return pac.getModule();
	}
	
	@ResponseBody
	@RequestMapping(value="citie")
	public JSONObject citie(String provinceid){
		ResultDO<JSONObject> pac = common.getCitie(provinceid);
		return pac.getModule();
	}
	
	@ResponseBody
	@RequestMapping(value="carea")
	public ResultDO<JSONObject> citieArea(){
		ResultDO<JSONObject> pac = common.getCitieArea();
		return pac;
	}
}
