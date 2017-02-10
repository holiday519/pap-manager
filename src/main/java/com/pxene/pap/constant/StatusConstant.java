package com.pxene.pap.constant;

public class StatusConstant {

//	/**
//	 * 项目状态：未开启
//	 */
//	public static final String PROJECT_WATING = "01";
	/**
	 * 项目状态：投放中
	 */
	public static final String PROJECT_START = "02";
	/**
	 * 项目状态：已暂停
	 */
	public static final String PROJECT_PAUSE = "03";
	/**
	 * 项目状态：已关闭
	 */
	public static final String PROJECT_CLOSE = "04";
	/**
	 * 活动状态：未开启
	 */
	public static final String CAMPAIGN_WATING = "01";
	/**
	 * 活动状态：投放中
	 */
	public static final String CAMPAIGN_START = "02";
	/**
	 * 活动状态：已暂停
	 */
	public static final String CAMPAIGN_PAUSE = "03";
	/**
	 * 活动状态：已关闭
	 */
	public static final String CAMPAIGN_CLOSE = "04";
	/**
	 * 广告主审核状态 未审核
	 */
	public static final String ADVERTISER_AUDIT_NOCHECK = "00";
	/**
	 * 广告主审核状态 ：审核中
	 */
	public static final String ADVERTISER_AUDIT_WATING = "01";
	/**
	 * 广告主审核状态 ：审核通过
	 */
	public static final String ADVERTISER_AUDIT_SUCCESS = "02";
	/**
	 * 广告主审核状态 ：审核不通过
	 */
	public static final String ADVERTISER_AUDIT_FAILURE = "03";
	/**
	 * 创意审核状态 ：审核中
	 */
	public static final String CREATIVE_AUDIT_WATING = "01";
	/**
	 * 创意审核状态 ：审核通过
	 */
	public static final String CREATIVE_AUDIT_SUCCESS = "02";
	/**
	 * 创意审核状态 ：审核不通过
	 */
	public static final String CREATIVE_AUDIT_FAILURE = "03";
	/**
	 * 创意类型 ： 图片
	 */
	public static final String CREATIVE_TYPE_IMAGE = "1";
	/**
	 * 创意类型 ： 视频
	 */
	public static final String CREATIVE_TYPE_VIDEO = "2";
	/**
	 * 创意类型 ： 信息流
	 */
	public static final String CREATIVE_TYPE_INFOFLOW = "3";
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
	 * action操作类型：关闭
	 */
	public static final String ACTION_TYPE_CLOSE = "03";
	/**
	 * 落地页代码检查状态：未检查
	 */
	public static final String LANDPAGE_CHECK_NOTCHECK = "00";
	/**
	 * 落地页代码检查状态：代码安装成功
	 */
	public static final String LANDPAGE_CHECK_SUCCESS = "01";
	/**
	 * 落地页代码检查状态：代码安装失败
	 */
	public static final String LANDPAGE_CHECK_ERROR = "02";
	/**
	 * 落地页代码检查状态：未发现代码
	 */
	public static final String LANDPAGE_CHECK_NOTFIND = "03";
	/**
	 * 落地页代码检查状态：无法完成代码检查
	 */
	public static final String LANDPAGE_CHECK_URLERRPR = "04";
	
	
}
