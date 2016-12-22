package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.service.CreativeService;

@Controller
public class CreativeController {
	
	@Autowired
	private CreativeService creativeService;
	
	/**
	 * 添加创意
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createCreative(@Valid @RequestBody CreativeBean bean, HttpServletResponse response) throws Exception {
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id", bean.getId(), response);
	}
	
	/**
	 * 修改创意
	 * @param id
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCreative(@PathVariable String id, @RequestBody CreativeBean bean, HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, bean);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 删除创意
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
		creativeService.deleteCreative(id);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}

	/**
	 * 上传创意图片
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative_image", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addImage(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws Exception {
		String id = creativeService.addImage(file);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id", id, response);
	}
	
	/**
	 * 上传视频
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative_video", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addVideo(@RequestPart(value = "file", required = true) MultipartFile file, HttpServletResponse response) throws Exception {
		String id = creativeService.addVideo(file);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, "id", id, response);
	}
	
	/**
     * 广告主提交第三方审核
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/creative_audit/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void auditCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
    	creativeService.auditCreative(id);
    	response.setStatus(HttpStatusCode.NO_CONTENT);
    }
    
    /**
     * 同步广告主第三方审核结果
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/creative_synchronize/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void synchronizeCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
    	creativeService.synchronize(id);
    	response.setStatus(HttpStatusCode.NO_CONTENT);
    }
}
