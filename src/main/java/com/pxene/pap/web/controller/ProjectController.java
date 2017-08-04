package com.pxene.pap.web.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.AdxCostData;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.ProjectBean;
import com.pxene.pap.domain.beans.RuleFormulasBean;
import com.pxene.pap.domain.beans.RuleGroupBean;
import com.pxene.pap.domain.beans.StaticvalBean;
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
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
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
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 编辑项目状态
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/status/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateProjectStatus(@PathVariable String id, @RequestBody Map<String,String> map, HttpServletResponse response) throws Exception {
		projectService.updateProjectStatus(id, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());
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
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 删除项目信息
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteProjects(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception {

		projectService.deleteProjects(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
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
	public String getProject(@PathVariable String id, HttpServletResponse response) throws Exception {

		ProjectBean projectDetailBean = projectService.getProject(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), projectDetailBean, response);
	}

	/**
	 * 查询项目列表
	 * @param name
	 * @param pageNo
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/projects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listProjects(@RequestParam(required = false) String name, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false) String advertiserId,
							   @RequestParam(required = false) String sortKey, @RequestParam(required = false) String sortType,@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {

		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
		List<ProjectBean> beans = projectService.listProjects(name, startDate, endDate, advertiserId, sortKey, sortType);

		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}

	/**
	 * 转化字段命名
	 * @param fieldId   项目ID
	 * @param name  位于Http Body中的请求参数，包含转化字段编号code和转化字段名称name
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/project/effect/name/{fieldId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void changeEffectName(@PathVariable String fieldId, @RequestBody Map<String,String> map, HttpServletResponse response) throws Exception
	{
	    projectService.changeEffectName(fieldId, map);
        response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 启用/禁用转化字段
	 * @param fieldId   项目ID
	 * @param map  位于Http Body中的请求参数，包含转化字段编号code和操作标识enable
	 * @param response
	 */
	@RequestMapping(value = "/project/effect/enable/{fieldId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void changeEffectEnable(@PathVariable String fieldId, @RequestBody Map<String,String> map, HttpServletResponse response)
	{
		projectService.changeEffectEnable(fieldId, map);
		response.setStatus(HttpStatus.NO_CONTENT.value());

	}

	/**
	 * 修改项目预算
	 * @param id   项目ID
	 * @param map  位于Http Body中的请求参数，包含项目预算budget
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/project/budget/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void changeProjectBudget(@PathVariable String id, @RequestBody Map<String,String> map, HttpServletResponse response) throws Exception
	{
	    projectService.changeProjectBudget(id, map);
	    response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 创建静态值
	 * @param bean
	 * @param response
	 * @return
	 * @throws Exception
     */
	@RequestMapping(value = "/project/staticval", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String createStatic(@Valid @RequestBody StaticvalBean bean, HttpServletResponse response) throws Exception {
		projectService.createStatic(bean);
		return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
	}

	/**
	 * 修改静态值
	 * @param id
	 * @param bean
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(value = "/project/staticval/value/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateStaticValue(@PathVariable String id, @RequestBody StaticvalBean bean, HttpServletResponse response) throws Exception {
		projectService.updateStaticValue(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 修改静态值名称
	 * @param id
	 * @param bean
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(value = "/project/staticval/name/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public void updateStaticName(@PathVariable String id, @Valid@RequestBody StaticvalBean bean, HttpServletResponse response) throws Exception {
		projectService.updateStaticName(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}

	/**
	 * 删除静态值
	 * @param ids 要删除的静态值id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/staticvals", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteStatics(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception {

		projectService.deleteStatics(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 创建规则
	 * @param bean 规则和公式  
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/rule",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
    public String createRule(@Valid @RequestBody RuleFormulasBean bean, HttpServletResponse response) throws Exception
    {
        projectService.createRule(bean);
        
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", bean.getId(), response);
    }
	
	/**
	 * 批量删除规则  
	 * @param ids 要删除的规则id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/rules",method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteRules(@RequestParam(required = true)String ids, HttpServletResponse response) throws Exception {
		projectService.deleteRules(ids.split(","));
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
	 * 根据ID查询规则
	 * @param id 规则ID
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/rule/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getRule(@PathVariable String id, HttpServletResponse response) throws Exception {
		RuleFormulasBean ruleFormulasBean = projectService.getRule(id);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), ruleFormulasBean, response);
	}
	
	/**
	 * 编辑规则
	 * @param id 规则id
	 * @param bean 规则及其对应的公式
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/project/rule/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody  
	public void updateRule(@PathVariable String id, @Valid @RequestBody RuleFormulasBean bean, HttpServletResponse response) throws Exception {
		projectService.updateRule(id, bean);
		response.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
	/**
     * 创建规则组
     * @param map
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/project/ruleGroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createRuleGroup(@RequestBody Map<String,String> map, HttpServletResponse response) throws Exception 
    {
        String id = projectService.createRuleGroup(map);
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", id, response);
    }
    
    /**
     * 批量删除规则组
     * @param ids 要删除的规则id
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/project/ruleGroup", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteRuleGroups(@RequestParam(required = true)String ids, HttpServletResponse response) throws Exception 
    {
        projectService.deleteRuleGroups(ids.split(","));
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 编辑规则组（只能修改名称）
     * @param id 规则id
     * @param bean 规则及其对应的公式
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/project/ruleGroup/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody  
    public void updateRuleGroup(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception 
    {
        projectService.updateRuleGroup(id, map);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
    
    /**
     * 根据ID查询规则组                                                                                                
     * @param id 规则组ID
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/project/ruleGroup/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getRuleGroup(@PathVariable String id, HttpServletResponse response) throws Exception
    {
        RuleGroupBean ruleFormulasBean = projectService.getRuleGroup(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), ruleFormulasBean, response);
    }
    
    /**
     * 查询规则组列表
     * @param name
     * @param pageNo
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/project/ruleGroups", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listRuleGroups(@RequestParam(required = false) String name, @RequestParam(required = false) String projectId, @RequestParam(required = false) String sortKey, @RequestParam(required = false) String sortType, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception 
    {
        List<RuleGroupBean> beans = projectService.listRuleGroups(name, projectId, sortKey, sortType);
        
        Map<String, List<RuleGroupBean>> o = new HashMap<>();
        o.put("items", beans);

        return ResponseUtils.sendReponse(HttpStatus.OK.value(), beans, response);
    }
}
