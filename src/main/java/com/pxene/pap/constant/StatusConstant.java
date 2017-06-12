package com.pxene.pap.constant;

public class StatusConstant {

	/**
	 * 项目状态：启动
	 */
	public static final String PROJECT_PROCEED = "01";
	/**
	 * 项目状态：暂停
	 */
	public static final String PROJECT_PAUSE = "02";
	/**
	 * 活动状态：启动
	 */
	public static final String CAMPAIGN_PROCEED = "01";
	/**
	 * 活动状态：暂停
	 */
	public static final String CAMPAIGN_PAUSE = "02";
	/**
	 * 广告主审核状态 ：审核中
	 */
	public static final String ADVERTISER_AUDIT_WATING = "02";
	/**
	 * 广告主审核状态 ：审核通过
	 */
	public static final String ADVERTISER_AUDIT_SUCCESS = "03";
	/**
	 * 广告主审核状态 ：审核不通过
	 */
	public static final String ADVERTISER_AUDIT_FAILURE = "04";
	/**
	 * 广告主审核状态 ：未审核
	 */
	public static final String ADVERTISER_AUDIT_NOCHECK = "01";
	/**
	 * 创意审核状态 ：未审核
	 */
	public static final String CREATIVE_AUDIT_NOCHECK = "01";
	/**
	 * 创意审核状态 ：审核中
	 */
	public static final String CREATIVE_AUDIT_WATING = "02";
	/**
	 * 创意审核状态 ：审核通过
	 */
	public static final String CREATIVE_AUDIT_SUCCESS = "03";
	/**
	 * 创意审核状态 ：审核不通过
	 */
	public static final String CREATIVE_AUDIT_FAILURE = "04";
	/**
	 * 创意审核状态 ：已过期
	 */
	public static final String CREATIVE_AUDIT_EXPITY = "05";
	/**
	 * 落地页代码检查状态：未检查
	 */
	public static final String LANDPAGE_CHECK_NOTCHECK = "01";
	/**
	 * 落地页代码检查状态：代码安装成功
	 */
	public static final String LANDPAGE_CHECK_SUCCESS = "02";
	/**
	 * 落地页代码检查状态：代码安装失败   
	 */
	public static final String LANDPAGE_CHECK_ERROR = "03";
	/**
	 * 广告主Adx的状态： 启用 
	 */
	public static final String ADVERTISER_ADX_ENABLE = "01";
	/**
	 * 广告主Adx的状态： 禁用 
	 */
	public static final String ADVERTISER_ADX_DISABLE = "02";
	/**
	 * 转义字段状态：启用
	 */
	public static final String EFFECT_STATUS_ENABLE = "01";
	
	/**
	 * 转义字段状态：禁用
	 */
	public static final String EFFECT_STATUS_DISABLE = "02";  
	/**
	 * 活动未正常投放原因：项目预算达到上限
	 */
	public static final String CAMPAIGN_PROJECTBUDGET_OVER = "01";
	/**
	 * 活动未正常投放原因：日预算达到上限
	 */
	public static final String CAMPAIGN_DAILYBUDGET_OVER = "02";
	/**
	 * 活动未正常投放原因：展现数达到上限
	 */
	public static final String CAMPAIGN_COUNTER_OVER = "03";
	/**
	 * 活动未正常投放原因：不在定向时间段内
	 */
	public static final String CAMPAIGN_ISNOT_TARGETTIME = "04"; 
	/**
	 * 活动未正常投放原因：活动下无可投放创意
	 */
	public static final String CAMPAIGN_NOTHAVE_CREATIVE = "05";
	/**
	 * 创意状态：启动
	 */
	public static final String CREATIVE_IS_ENABLE = "01";
	/**
	 * 创意状态：暂停
	 */
	public static final String CREATIVE_ISNOT_ENABLE = "02";
}
