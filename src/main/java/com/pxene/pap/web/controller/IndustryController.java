package com.pxene.pap.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.service.IndustryService;

@Controller
public class IndustryController {
	
	@Autowired
	private IndustryService industryService;

	@RequestMapping(value = "/industries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listIndustries(HttpServletResponse response) throws Exception {
		List<Map<String, String>> items = industryService.listIndustries();
		Map<String, List<Map<String, String>>> result = new HashMap<String, List<Map<String,String>>>();
		result.put("items", items);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
