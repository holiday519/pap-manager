package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.service.AuditCreativeBaiduService;
import com.pxene.pap.service.LaunchService;
import com.pxene.pap.service.RedisService;
import com.pxene.pap.service.VideoService;

@Controller
public class VideoController {
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private LaunchService launchService;
	
	@Autowired
	private AuditCreativeBaiduService auditService;
	
	@RequestMapping(value = "/video/{id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateVideo(@PathVariable String id, @RequestBody VideoBean bean, HttpServletResponse response) throws Exception {
		videoService.updateVideo(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	@RequestMapping(value = "/video/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
//		//测试redis
//		redisService.writeCampaignInfoToRedis(id);
		//测试审核
//		auditService.audit(id);
//		auditService.synchronize(id);
//		redisService.writeCampaignIds(id);
		launchService.launchByTime();
		return null;
	}

}
