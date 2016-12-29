package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.APPTmplBean;
import com.pxene.pap.domain.beans.SizeBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.TmplService;

@Controller
public class TmplController {

	@Autowired
	private TmplService tmplService;
	
	
	/**
	 * 查询尺寸列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tmplAppImage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectsizes(@RequestParam String appids, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APPTmplBean bean = tmplService.selectAppTmplVideo(appids);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), bean, response);
	}
}
