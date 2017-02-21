package com.pxene.pap.web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class DataController {

	@RequestMapping(value = "/data/times", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listTimes(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/data/regions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listRegions(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/data/operators", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listOperators(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/data/networks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listNetworks(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		return "";
	}
	
	@RequestMapping(value = "/data/systems", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listSystems(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, 
			@RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, 
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		return "";
	}
	
}
