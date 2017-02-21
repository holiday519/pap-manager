package com.pxene.pap.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

public class DateUtils {

	/**
	 * 获取当前小时
	 * 
	 * @return
	 */
	public static String getCurrentHour() {
		DateTime time = new DateTime();
		String string = time.toString("HH");
		return string;
	}

	/**
	 * 获取当前天
	 * 
	 * @return
	 */
	public static String getCurrentDay() {
		DateTime time = new DateTime();
		String string = time.toString("dd");
		return string;
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		DateTime time = new DateTime();
		String string = time.toString("yyyy");
		return string;
	}

	/**
	 * 获取当前日期（年-月-日）
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		DateTime time = new DateTime();
		String string = time.toString("yyyy-MM-dd");
		return string;
	}

	/**
	 * 根据格式获取当前时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentFormatData(String format) {
		DateTime time = new DateTime();
		String string = time.toString(format);
		return string;
	}

	/**
	 * 获取当前星期（中文）
	 * 
	 * @return
	 */
	public static String getCurrentWeek() {
		DateTime time = new DateTime();
		String string = time.toString("EE", Locale.CHINESE);
		return string;
	}

	/**
	 * 获取当前星期（数字）
	 * 
	 * @return
	 */
	public static String getCurrentWeekInNumber() {
		DateTime time = new DateTime();
		String string = time.toString("EE", Locale.CHINESE);
		switch (string) {
		case "星期一":
			return "1";
		case "星期二":
			return "2";
		case "星期三":
			return "3";
		case "星期四":
			return "4";
		case "星期五":
			return "5";
		case "星期六":
			return "6";
		case "星期日":
			return "7";
		default:
			return string;
		}
	}
	
	/**
	 * 查询时间改变天数后的日期
	 * @param days
	 * @return
	 */
	public static String getDayOfChange(Date date, int days) {
		DateTime time = new DateTime(date).plusDays(days);
		String string = time.toString("yyyy-MM-dd", Locale.CHINESE);
		return string;
	}
	
	/**
	 * 获取两个日期之间天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getDayNumBetween(Date start, Date end) {
		DateTime start2 = new DateTime(start);    
		DateTime end2 = new DateTime(end); 
		int days = Days.daysBetween(start2, end2).getDays();  
		return days;
	}
	
	public static int getHourNumBetween(Date start, Date end) {
		DateTime start2 = new DateTime(start);    
		DateTime end2 = new DateTime(end); 
		int hours = Hours.hoursBetween(start2, end2).getHours();
		return hours;
	}
	
	/**
	 * 查询两个日期中间所有的“天”
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] getDaysBetween(Date start, Date end) {
		int num = getDayNumBetween(start, end);
		if (num < 0) {
			return null;
		}
		String[] array = new String[num+1];
		for (int i = 0; i <= num; i++) {
			array[i] = new DateTime(start).plusDays(i).toString("yyyyMMdd");
		}
		return array;
	}
	
	/**
	 * 获取两个时间之间的“小时”
	 * @param start
	 * @param end
	 * @return
	 */
	public static String[] getHoursBetween(Date start, Date end) {
		Integer st = Integer.parseInt(new DateTime(start).toString("HH"));
		Integer ed = Integer.parseInt(new DateTime(end).toString("HH"));
		
		List<String> list = new ArrayList<String>();
		for (int i = 0;i <= (ed-st); i++ ) {
			list.add(new DateTime(start).plusHours(i).toString("HH"));
		}
		return list.toArray(new String[list.size()]);
	}
	
	
	public static String[] getDayHoursBetween(Date start, Date end) {
		int num = getHourNumBetween(start, end);
		if (num < 0) {
			return null;
		}
		String[] array = new String[num+1];
		for (int i = 0; i <= num; i++) {
			array[i] = new DateTime(start).plusHours(i).toString("yyyyMMddHH");
		}
		return array;
	}
	
	 /**
     * 获取一天中最大时间
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getBigHourOfDay(Date time) throws Exception {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat fot = new SimpleDateFormat("yyyy-MM-dd");
    	String fTime = fot.format(time);
        Date date = format.parse(fTime + " 23:59:59");
        return date;
    }
    
    /**
     * 获取一天中最小时间
     * @param time
     * @return
     * @throws Exception
     */
    public static Date getSmallHourOfDay(Date time) throws Exception {
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	SimpleDateFormat fot = new SimpleDateFormat("yyyy-MM-dd");
    	String fTime = fot.format(time);
    	Date date = format.parse(fTime + " 00:00:00");
    	return date;
    }
	
    
	public static void main(String[] args) throws Exception {
		
//		for (int i=0;i<=32;i++) {
//			System.out.println(new DateTime(new Date(1355443200000L)).plusDays(i).toString("dd", Locale.CHINESE));
//		}
		System.out.println(Long.parseLong("20130901") < Long.parseLong("20160205"));
//		String[] hourArrayBetweenTwoDate = getHourArrayBetweenTwoDate(new Date(1484020800000L), new Date(1484038800000L));
	}
}
