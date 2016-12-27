package com.pxene.pap.common;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

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
	public static String getCurrentData() {
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
	
	public static void main(String[] args) {
		
		System.out.println(getDayOfChange(new Date(),0));

	}
}
