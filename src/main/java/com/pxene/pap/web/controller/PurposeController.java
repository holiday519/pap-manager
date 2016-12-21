package com.pxene.pap.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.HttpStatusCode;
import com.pxene.pap.domain.beans.PurposeBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.PurposeService;

@Controller
public class PurposeController {

	@Autowired
	private PurposeService purposeService;
	
	/**
	 * 添加活动目标
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/purpose", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createPurpose(@Valid @RequestBody PurposeBean bean, HttpServletResponse response) throws Exception {
		purposeService.createPurpose(bean);
		return ResponseUtils.sendReponse(HttpStatusCode.CREATED, "id", bean.getId(), response);
	}
	
	/**
	 * 编辑活动目标
	 * @param id
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/purpose/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updatePurpose(@PathVariable String id, @Valid @RequestBody PurposeBean bean, HttpServletResponse response) throws Exception {
		purposeService.updatePurpose(id, bean);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 删除活动目标
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/purpose/{id}",method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePurpose(@PathVariable String id, HttpServletResponse response) throws Exception {
		purposeService.deletePurpose(id);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 根据ID查询活动目标
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/purpose/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectPurpose(@PathVariable String id, HttpServletResponse response) throws Exception {
		
		PurposeBean purposeBean = purposeService.selectPurpose(id);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, purposeBean, response);
	}
	
	/**
	 * 查询活动目标列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/purpose", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectPurposes(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<PurposeBean> selectPurposes = purposeService.selectPurposes(name);
		
		PaginationResult result = new PaginationResult(selectPurposes, pager);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, result, response);
	}
}
