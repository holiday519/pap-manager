package com.pxene.pap.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.service.PurposeService;

@Controller
public class PurposeController {

	@Autowired
	private PurposeService purposeService;
	
	@RequestMapping(value = "/purpose", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createProject(@Valid @RequestBody PurposeBean bean, HttpServletResponse response) throws Exception {
		purposeService.createPurpose(bean);
		return ResponseUtils.sendReponse(HttpStatusCode.CREATED, "id", bean.getId(), response);
	}
	
	@RequestMapping(value = "/purpose/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String updateProject(@PathVariable String id, @Valid @RequestBody PurposeBean bean, HttpServletResponse response) throws Exception {
		purposeService.updatePurpose(id, bean);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, bean, response);
	}
	
	@RequestMapping(value="/purpose",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void deleteProject(@RequestBody String id, HttpServletResponse response) throws Exception {
		purposeService.deletePurpose(id);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
}
