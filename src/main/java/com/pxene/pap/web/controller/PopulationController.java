package com.pxene.pap.web.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.PopulationBean;
import com.pxene.pap.service.PopulationService;

@Controller
public class PopulationController {

	@Autowired
	private PopulationService populationService;
	
	@RequestMapping(value = "/populations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listPopulations(@RequestParam(required = false) String name, @RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		Page<Object> pager = null;
        if (pageNo != null && pageSize != null){
            pager = PageHelper.startPage(pageNo, pageSize);
        }
        
		List<PopulationBean> beans = populationService.listPopulations(name);
		
		PaginationBean result = new PaginationBean(beans, pager);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
	
	
	/*
	 * 创建人群
	 */
	@RequestMapping(value = "/population", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String createPopulation(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "name", required = true) String name, HttpServletResponse response) throws Exception 
	{
	    name = new String(name.getBytes(Charset.forName("iso-8859-1")), Charset.forName("utf-8"));
        String path = populationService.createPopulation(file, name);
        return ResponseUtils.sendReponse(HttpStatus.CREATED.value(), "id", path, response);
    }
	
	/*
	 * 修改人群
	 */
	@RequestMapping(value = "/population/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updatePopulation(@PathVariable String id, @RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "name", required = true) String name,HttpServletResponse response) throws Exception 
	{
	    populationService.updatePopulation(id, file, name);
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
	
	/*
	 * 修改人群名称
	 */
	@RequestMapping(value = "/population/name/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void updatePopulation4Name(@PathVariable String id, @RequestBody Map<String, String> map, HttpServletResponse response) throws Exception 
	{
	    populationService.updatePopulation4Name(id, map.get("name"));
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
	
	/*
	 * 批量删除人群
	 */
	@RequestMapping(value = "/populations", method = RequestMethod.DELETE)
    @ResponseBody
    public void deletePopulations(@RequestParam(required = true) String ids, HttpServletResponse response) throws Exception
    {
	    populationService.deletePopulations(ids.split(","));
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
	
	/*
	 * 查询人群
	 */
	@RequestMapping(value = "/population/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getPopulation(@PathVariable String id, HttpServletResponse response) throws Exception
    {
	    PopulationBean population = populationService.getPopulation(id);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), population, response);
    }
}
