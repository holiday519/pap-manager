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
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ImageCreativeBean;
import com.pxene.pap.domain.beans.InfoflowCreativeBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.VideoCreativeBean;
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
	@RequestMapping(value = "/creative/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createImageCreative(@Valid @RequestBody ImageCreativeBean bean, HttpServletResponse response) throws Exception {
		bean.setType(StatusConstant.CREATIVE_TYPE_IMAGE);
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	
	/**
	 * 添加创意
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/video", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createVideoCreative(@Valid @RequestBody VideoCreativeBean bean, HttpServletResponse response) throws Exception {
		bean.setType(StatusConstant.CREATIVE_TYPE_VIDEO);
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	
	/**
	 * 添加创意
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/infoflow", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createInfoflowCreative(@Valid @RequestBody InfoflowCreativeBean bean, HttpServletResponse response) throws Exception {
		bean.setType(StatusConstant.CREATIVE_TYPE_INFOFLOW);
		creativeService.createCreative(bean);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}
	
	/**
	 * 修改创意价格
	 * @param id
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/price/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCreativePrice(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception {
		creativeService.updateCreativePrice(id, map);
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
	public void deleteCreatives(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception {
		creativeService.deleteCreatives(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
//	/**
//	 * 批量删除创意下素材
//	 * @param id
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/creative/materials",method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteCreativeMaterials(@RequestBody String[] mapIds, HttpServletResponse response) throws Exception {
//		creativeService.deleteCreativeMaterials(mapIds);
//		response.setStatus(HttpStatus.NO_CONTENT.value());
//	}
//	/**
//	 * 删除创意下素材
//	 * @param id
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/creative/material/{mapId}",method = RequestMethod.DELETE)
//	@ResponseBody
//	public void deleteCreativeMaterial(@PathVariable String mapId, HttpServletResponse response) throws Exception {
//		creativeService.deleteCreativeMaterial(mapId);
//		response.setStatus(HttpStatus.NO_CONTENT.value());
//	}

	/**
	 * 上传素材
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/upload", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String uploadMaterial(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "tmplId") String tmplId, HttpServletResponse response) throws Exception {
		Map<String, String> map = creativeService.uploadMaterial(tmplId, file);
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
    @RequestMapping(value = "/creative/synchronize/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void synchronizeCreative(@PathVariable String id, HttpServletResponse response) throws Exception {
    	creativeService.synchronizeCreative(id);
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
	public String selectCreatives(@RequestParam(required = true) String campaignId, @RequestParam(required = false) String type, @RequestParam(required = false) String name, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
		        
		List<BasicDataBean> creatives = creativeService.selectCreatives(campaignId, name, type, startDate, endDate);
		
		PaginationBean result = new PaginationBean(creatives, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	/**
	 * 查询单个创意
	 * @param campaignId
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectCreative(@PathVariable String id, HttpServletResponse response, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate) throws Exception {
		
		BasicDataBean creative = creativeService.getCreative(id, startDate, endDate);
		
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), creative, response);
	}
	
	/**
	 * 修改图片创意
	 * @param id
	 * @param imageBean
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/image/{id}",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateImageCreative(@PathVariable String id,@RequestBody ImageCreativeBean imageBean,HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, imageBean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 修改视频创意
	 * @param id
	 * @param videoBean
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/video/{id}",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateVideoCreative(@PathVariable String id,@RequestBody VideoCreativeBean videoBean,HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, videoBean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 修改信息流
	 * @param id
	 * @param infoBean
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creative/infoflow/{id}",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateInfoflowCreative(@PathVariable String id,@Valid @RequestBody InfoflowCreativeBean infoBean,HttpServletResponse response) throws Exception {
		creativeService.updateCreative(id, infoBean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 批量同步创意，批量同步创意第三方审核结果
	 * @param ids 创意ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatives/synchronize",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody	
	public void synchronizeCreatives(@RequestPart(value = "ids",required = true)String ids,HttpServletResponse response) throws Exception {
		creativeService.synchronizeCreatives(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 批量审核创意
	 * @param ids 创意ids
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatives/audit",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody	
	public void auditCreatives(@RequestPart(value = "ids",required = true)String ids,HttpServletResponse response) throws Exception {
		creativeService.auditCreative(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 编辑创意的状态，启动/暂停创意
	 * @param id
	 * @param map
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatives/enable/{id}",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateCreativeStatus(@PathVariable String id, @RequestBody Map<String,String> map,HttpServletResponse response) throws Exception {
		creativeService.updateCreativeStatus(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());
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
//	@RequestMapping(value = "/creatives", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
//	public String selectMaterials(@RequestParam(required = true) String campaignId, @RequestParam(required = false) long beginTime, @RequestParam(required = false) long endTime, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
//		Page<Object> pager = null;
//		
//		List<MaterialListBean> creatives = creativeService.selectCreativeMaterials(campaignId, beginTime, endTime);
//		
//		PaginationBean result = new PaginationBean(creatives, pager);
//		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
//	}
}
