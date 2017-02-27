package com.pxene.pap.web.controller;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pxene.pap.common.ExcelOperateUtil;
import com.pxene.pap.common.ExcelUtil;
import com.pxene.pap.domain.beans.TrafficData;
import com.pxene.pap.service.TrafficDataService;

@Controller
public class TrafficDataController
{
    @Autowired
    private TrafficDataService trafficDataService;
    
    
    @RequestMapping(value = "/traffic-data", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void exportExcel(@RequestParam(required = false) String advertiserId, @RequestParam(required = false) String projectId, @RequestParam(required = false) String campaignId, @RequestParam(required = false) String creativeId, @RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, HttpServletResponse response) throws Exception
    {
        // 定义列名称和列宽度
        String[] recoresColumns = new String[] { "时间_#_3000", "名称_#_7000", "展现数_#_7000", "点击数_#_7000", "CTR_#_7000", "转化_#_7000", "成本_#_7000", "展现成本_#_7000", "点击成本_#_7000", "转化成本_#_7000" };
        
        // 定义需要显示在Excel行中的实体Bean中的属性名称
        String[] recoresFields = new String[] { "date", "name", "impressionAmount", "clickAmount", "clickRate", "conversionAmount", "totalCost", "impressionCost", "clickCost", "conversionCost" };

        List<TrafficData> datas = trafficDataService.listData(advertiserId, projectId, campaignId, creativeId, startDate, endDate);
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        ExcelUtil<TrafficData> excelUtil = new ExcelUtil<TrafficData>();
        
        HSSFSheet sheet = excelUtil.creatAuditSheet(workbook, "UserTest sheet xls", datas, recoresColumns, recoresFields);
        
        ByteArrayInputStream inputStream = (ByteArrayInputStream) ExcelUtil.writeExcelToStream(workbook, sheet);
        
        ExcelOperateUtil.downloadExcel(inputStream, response, "traffic-data.xls");
        /*
        FileOutputStream fileOut = new FileOutputStream("d:/demo_ningyu.xls");
        
        int data = inputStream.read();
        while (data != -1)
        {
            fileOut.write(data);
            data = inputStream.read();
        }
        fileOut.close();*/
        
    }
}
