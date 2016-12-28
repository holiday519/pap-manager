package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.pxene.pap.domain.beans.CreativeAddBean;
import com.pxene.pap.domain.beans.CreativeUpdateBean;
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
	public String createCreative(@Valid @RequestBody CreativeAddBean bean, HttpServletResponse response) throws Exception {
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), "id", bean.getId(), response);
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
	public void updateCreative(@PathVariable String id, @RequestBody CreativeUpdateBean bean, HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
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
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 上传素材
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/upload", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addMaterial(@RequestPart(value = "file", required = true) MultipartFile file, @RequestBody String tmplId, HttpServletResponse response) throws Exception {
		String id = creativeService.addMaterial(tmplId, file);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), "id", id, response);
	}
	
	/**
     * 创意提交第三方审核
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/creative/audit/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void auditCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
    	creativeService.auditCreative(id);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 同步创意第三方审核结果
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/creative/synchronize/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void synchronizeCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
    	creativeService.synchronize(id);
    	response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
