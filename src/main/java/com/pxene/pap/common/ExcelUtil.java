package com.pxene.pap.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil<T>
{
    
    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook, boolean isHeader)
    {
        HSSFCellStyle style = workbook.createCellStyle();
        
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        
        style.setLocked(true);
        
        if (isHeader)
        {
            // 设置填充背景色为：灰色
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            
            // 设置填充模式为：实线填充
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            font.setBold(true);
            style.setFont(font);
        }
        
        return style;
    }
    
    public void generateHeader(HSSFWorkbook workbook, HSSFSheet sheet, String[] headerColumns)
    {
        HSSFCellStyle style = getCellStyle(workbook, true);
        
        Row row = sheet.createRow(0);
        row.setHeightInPoints(30);
        
        for (int i = 0; i < headerColumns.length; i++)
        {
            Cell cell = row.createCell(i);
            String[] column = headerColumns[i].split("_#_");
            sheet.setColumnWidth(i, Integer.valueOf(column[1]));
            cell.setCellValue(column[0]);
            cell.setCellStyle(style);
        }
    }
    
    public static InputStream writeExcelToStream(HSSFWorkbook workbook, HSSFSheet sheet)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        try
        {
            workbook.write(bos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            workbook.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        // 数据在bos中
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        return bis;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public HSSFSheet creatAuditSheet(HSSFWorkbook workbook, String sheetName, List<T> dataset, String[] headerColumns, String[] fieldColumns)
            throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
        
        HSSFSheet sheet = workbook.createSheet(sheetName);
        
        // 保护生成Excel文档，设置密码访问.
        // sheet.protectSheet(""); 
        
        // 自动对生成的Excel 文档第一行标题栏设置成filter 过滤形式, 方便用户使用
        char[] endChar = Character.toChars('A' + (headerColumns.length - 1));
        String rangeAddress = "A1:" + String.valueOf(endChar) + "1";
        sheet.setAutoFilter(CellRangeAddress.valueOf(rangeAddress));
        
        generateHeader(workbook, sheet, headerColumns);
        HSSFCellStyle style = getCellStyle(workbook, false);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        int rowNum = 0;
        for (T t : dataset)
        {
            rowNum++;
            Row row = sheet.createRow(rowNum);
            row.setHeightInPoints(25);
            for (int i = 0; i < fieldColumns.length; i++)
            {
                String fieldName = fieldColumns[i];
                
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try
                {
                    Class clazz = t.getClass();
                    Method getMethod;
                    getMethod = clazz.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    String cellValue = "";
                    if (value instanceof Date)
                    {
                        Date date = (Date) value;
                        cellValue = sd.format(date);
                    }
                    else
                    {
                        cellValue = null != value ? value.toString() : "";
                    }
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style);
                    cell.setCellValue(cellValue);
                    
                }
                catch (Exception e)
                {
                    
                }
            }
        }
        return sheet;
    }
}
