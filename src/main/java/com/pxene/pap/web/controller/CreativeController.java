package com.pxene.pap.web.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.CreativeAddBean;
import com.pxene.pap.domain.beans.MaterialListBean;
import com.pxene.pap.domain.beans.PaginationBean;
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
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
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
	public void updateCreative(@PathVariable String id, @RequestBody CreativeAddBean bean, HttpServletResponse response) throws Exception {
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
	 * 批量删除创意
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatives",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCreatives(@RequestBody String[] ids, HttpServletResponse response) throws Exception {
		creativeService.deleteCreatives(ids);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 批量删除创意下素材
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/materials",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCreativeMaterials(@RequestBody String[] mapIds, HttpServletResponse response) throws Exception {
		creativeService.deleteCreativeMaterials(mapIds);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	/**
	 * 删除创意下素材
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/material/{mapId}",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteCreativeMaterial(@PathVariable String mapId, HttpServletResponse response) throws Exception {
		creativeService.deleteCreativeMaterial(mapId);
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
	public String addMaterial(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "tmplId") String tmplId, HttpServletResponse response) throws Exception {
		Map<String, String> map = creativeService.addMaterial(tmplId, file);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), map, response);
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
    
    /**
	 * 查询创意列表
     * @param name
     * @param campaignId
     * @param pageNo
     * @param pageSize
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectCreatives(@RequestParam(required = false) String name, @RequestParam(required = false) String campaignId, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		if (pageNo != null && pageSize != null) {
			pager = PageHelper.startPage(pageNo, pageSize);
		}
        
		List<Map<String, Object>> creatives = creativeService.selectCreatives(name, campaignId);
		
		PaginationBean result = new PaginationBean(creatives, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
	/**
	 * 列出所有素材
	 * @param name
	 * @param creativeId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/materials", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectMaterials(@RequestParam(required = false) String name, @RequestParam(required = false) String creativeId, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		
		List<MaterialListBean> creatives = creativeService.selectCreativeMaterials(name, creativeId);
		
		PaginationBean result = new PaginationBean(creatives, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
