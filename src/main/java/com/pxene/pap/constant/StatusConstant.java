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
	 * 规则状态：使用中
	 */
	public static final String CAMPAIGN_RULE_STATUS_USED = "01";
	/**
	 * 规则状态：停用
	 */
	public static final String CAMPAIGN_RULE_STATUS_UNUSED = "02";
	/**
	 * 活动规则类型：创意规则
	 */
	public static final String CAMPAIGN_RULE_TYPE_CREATIVE = "01";
	/**
	 * 活动规则类型：落地页规则
	 */
	public static final String CAMPAIGN_RULE_TYPE_LANDPAGE = "02";
	/**
	 * 活动规则类型：APP规则
	 */
	public static final String CAMPAIGN_RULE_TYPE_APP = "03";
	/**
	 * 活动规则类型：地域规则
	 */
	public static final String CAMPAIGN_RULE_TYPE_REGION = "04";
	/**
	 * 活动规则类型：时间规则
	 */
	public static final String CAMPAIGN_RULE_TYPE_TIME = "05";
	/**
	 * action操作类型：暂停
	 */
	public static final String ACTION_TYPE_PAUSE = "01";
	/**
	 * action操作类型：恢复
	 */
	public static final String ACTION_TYPE_PROCEES = "02";
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
	
	public static final String CAMPAIGN_NOT_UNIFORM = "0";
	
	public static final String CAMPAIGN_UNIFORM = "1";
	
}
