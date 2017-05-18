package com.pxene.pap.web.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pxene.pap.common.ExcelOperateUtil;
import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.common.ResponseUtils;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.PaginationBean;
import com.pxene.pap.domain.beans.TrafficData;
import com.pxene.pap.domain.beans.UploadFilesBean;
import com.pxene.pap.service.TrafficDataService;

@Controller
public class TrafficDataController
{
    @Autowired
    private TrafficDataService trafficDataService;
    
    
    @RequestMapping(value = "/traffic-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void exportExcel(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, @RequestParam(required = false) String scope, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
        // 定义列名称和列宽度
        String[] recoresColumns = new String[] { "日期_#_3000", "名称_#_7000", "展现数_#_7000", "点击数_#_7000", "CTR_#_7000", "二跳数_#_7000", "成本_#_7000", "展现成本_#_7000", "点击成本_#_7000", "二跳成本_#_7000" };
        
        // 定义需要显示在Excel行中的实体Bean中的属性名称
        String[] recoresFields = new String[] { "date", "name", "impressionAmount", "clickAmount", "clickRate", "jumpAmount", "totalCost", "impressionCost", "clickCost", "jumpCost" };

        List<TrafficData> datas = trafficDataService.listData(advertiserId, projectId, campaignId, creativeId, scope, startDate, endDate);
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        ExcelUtil<TrafficData> excelUtil = new ExcelUtil<TrafficData>();
        
        HSSFSheet sheet = excelUtil.creatAuditSheet(workbook, "UserTest sheet xls", datas, recoresColumns, recoresFields);
        
        ByteArrayInputStream inputStream = (ByteArrayInputStream) ExcelUtil.writeExcelToStream(workbook, sheet);
        
        ExcelOperateUtil.downloadExcel(inputStream, response, "traffic-data.xls");
    }
    
    @RequestMapping(value = "/data/traffic-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String listTrafficData(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, @RequestParam(required = false) String scope, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
        List<TrafficData> datas = trafficDataService.listData(advertiserId, projectId, campaignId, creativeId, scope, startDate, endDate);
        return ResponseUtils.sendReponse(HttpStatus.OK.value(), datas, response);
    }
    
    /**
     * 列出转化数据文件
     * @param pageNo
     * @param pageSize
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/data/effect/list",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public String listUploadfiles(@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize, HttpServletResponse response) throws Exception {
		// 定义分页对象
		Page<Object> page = null;
		if (pageNo != null && pageSize != null)
        {
            page = PageHelper.startPage(pageNo, pageSize);
        }
		// 查询转换数据文件
		List<UploadFilesBean> uploadFiles = trafficDataService.listUploadFiles();
		// 分页
		PaginationBean result = new PaginationBean(uploadFiles, page);
		return ResponseUtils.sendReponse(HttpStatus.OK.value(), result, response);
	}
}
