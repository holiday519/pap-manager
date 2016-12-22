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
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.model.custom.PaginationResult;
import com.pxene.pap.service.ProjectService;

@Controller
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 添加项目
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createProject(@Valid @RequestBody ProjectBean bean, HttpServletResponse response) throws Exception {
		
		projectService.createProject(bean);
        return ResponseUtils.sendReponse(HttpStatusCode.CREATED, "id", bean.getId(), response);
	}
	
	/**
	 * 编辑项目信息
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateProject(@PathVariable String id, @Valid @RequestBody ProjectBean bean, HttpServletResponse response) throws Exception {
		
		projectService.updateProject(id, bean);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 删除项目信息
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		
		projectService.deleteProject(id);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
	
	/**
	 * 根据id查询项目
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectProject(@PathVariable String id, HttpServletResponse response) throws Exception {
		
		ProjectBean projectBean = projectService.selectProject(id);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, projectBean, response);
	}
	
	/**
	 * 查询项目列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String selectProjects(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<ProjectBean> selectProjects = projectService.selectProjects(name);
		
		PaginationResult result = new PaginationResult(selectProjects, pager);
		return ResponseUtils.sendReponse(HttpStatusCode.OK, result, response);
	}
	
	/**
	 * 投放项目
	 * @param projectIds
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/projectPutOn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void putOnProject(@RequestBody List<String> projectIds, HttpServletResponse response) throws Exception {
		projectService.putOnProject(projectIds);
		response.setStatus(HttpStatusCode.NO_CONTENT);
	}
}
