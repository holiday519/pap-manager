package com.pxene.pap.common;

import java.io.IOException;
import java.io.InputStream;

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
}
