package com.pxene.pap.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public class ExcelOperateUtil
{
    public static void downloadExcel(InputStream inputStream, HttpServletResponse response, String excelName)
    {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;" + "filename=" + excelName);
        
        ServletOutputStream outputStream = null;
        
        try
        {
            outputStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outputStream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally 
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载07版excel文件
     * @param inputStream
     * @param response
     * @param excelName
     * @throws UnsupportedEncodingException
     */
    public static void downloadExcel07(InputStream inputStream, HttpServletResponse response, String excelName) throws UnsupportedEncodingException {

        //判断后缀名
        if(!excelName.endsWith(".xlsx")) {
            excelName = excelName + ".xlsx";
        }
//        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Type","application/force-download");
        response.setContentType("application/vnd.ms-excel;charset=utf-8"); 
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
//        response.addHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(excelName, "UTF-8"));
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(excelName, "UTF-8"));

        ServletOutputStream outputStream = null;
        try
        {
            outputStream = response.getOutputStream();
            FileCopyUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (outputStream != null)
            {
                try
                {
                    outputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
