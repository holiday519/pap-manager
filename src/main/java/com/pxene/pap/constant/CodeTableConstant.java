package com.pxene.pap.constant;

/**
 * 码表
 *
 */
public class CodeTableConstant {

	//运营商
	public static final String OPERATOR_CODE_MOBILE = "1";
	
	public static final String OPERATOR_CODE_UNICOM = "2";
	
	public static final String OPERATOR_CODE_TELECOM = "3";
	
	public static final String OPERATOR_NAME_MOBILE = "中国移动";
	
	public static final String OPERATOR_NAME_UNICOM = "中国联通";
	
	public static final String OPERATOR_NAME_TELECOM = "中国电信";
	//网络
	public static final String NETWORK_CODE_WIFI = "1";
	
	public static final String NETWORK_CODE_2G = "3";
	
	public static final String NETWORK_CODE_3G = "4";
	
	public static final String NETWORK_CODE_4G = "5";
	
	public static final String NETWORK_NAME_WIFI = "wifi";
	
	public static final String NETWORK_NAME_2G = "2G";
	
	public static final String NETWORK_NAME_3G = "3G";
	
	public static final String NETWORK_NAME_4G = "4G";
	//系统
	public static final String SYSTEM_CODE_IOS = "1";
	
	public static final String SYSTEM_CODE_ANDROID = "2";
	
	public static final String SYSTEM_CODE_WINDOWS = "3";
	
	public static final String SYSTEM_NAME_IOS = "ios";
	
	public static final String SYSTEM_NAME_ANDROID = "android";
	
	public static final String SYSTEM_NAME_WINDOWS = "windows";
	
	public static final String CODE_UNKNOW = "0";
	
	public static final String NAME_UNKNOW = "unknow";
	
	public static final String CAMPAIGN_NOT_UNIFORM = "0";
	
	public static final String CAMPAIGN_UNIFORM = "1";
	
	public static final String FREQUENCY_CONTROLOBJ_CAMPAIGN = "01";
	
	public static final String FREQUENCY_CONTROLOBJ_CREATIVE = "02";
	
	public static final String FREQUENCY_TIMETYPE_DAY = "01";
	
	public static final String FREQUENCY_TIMETYPE_HOUR = "02";
	
	public static final String POPULATION_WHITE_LIST = "01";
	
	public static final String POPULATION_BLACK_LIST = "02";
	/**
	 * 创意类型 ： 图片
	 */
	public static final String CREATIVE_TYPE_IMAGE = "01";
	/**
	 * 创意类型 ： 视频
	 */
	public static final String CREATIVE_TYPE_VIDEO = "02";
	/**
	 * 创意类型 ： 信息流
	 */
	public static final String CREATIVE_TYPE_INFOFLOW = "03";
	/**
	 * 汇总方式：合计
     */
	public static final String SUMMARYWAY_TOTAL = "01";
	/**
	 * 汇总方式：分日
     */
	public static final String SUMMARYWAY_DAY = "02";
	
	
	/**
	 * 固定名称：展现数、点击数、二跳、成本、修正成本
	 */
	public static final String DISPLAY_AMOUNT = "展现数";
	
	public static final String CLICK_AMOUNT = "点击数";
	
	public static final String JUMP_AMOUNT = "二跳";
	
	public static final String COST = "成本";
	
	public static final String ADX_COST = "修正成本";

	/**
	 * 匹配模式---精确匹配
     */
	public static final String MATCH_TYPE_EQUAL = "01";

	/**
	 * 匹配模式--模糊匹配
     */
	public static final String MATCH_TYPE_LIKE = "02";

	/**
	 * 过滤类型--包含
     */
	public static final String FILTER_TYPE_INCLUDE = "01";

	/**
	 * 过滤类型--不包含
     */
	public static final String FILTER_TYPE_EXCLUDE = "02";

}
