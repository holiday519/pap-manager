package com.pxene.pap.constant;

public class PhrasesConstant
{
	// 通用
	public static final String ID_NOT_NULL = "ID不能为空";
	public static final String NAME_NOT_NULL = "名称不能为空";
	public static final String NAME_NOT_REPEAT = "名称不能重复";
	public static final String OBJECT_NOT_FOUND = "该对象不存在";
	public static final String LENGTH_ERROR_ID = "id长度不能超过36";
	public static final String LENGTH_ERROR_NAME = "名称长度不能超过100";
	
	// 广告主
	public static final String ADVERVISER_HAVE_PROJECT = "该客户下存在项目，不能删除";
	
	// 项目
	
	// 活动
	public static final String CAMPAIGN_DATE_ERROR = "活动周期设置有误";
	public static final String CAMPAIGN_ALL_BUDGET_OVER_PROJECT = "项目下活动预算总和不能大于项目预算";
	public static final String FREQUENCY_NOT_COMPLETE = "频次信息不全";
	
    public static final String INVALID_PHONE = "不是正确的手机号码或座机号码";
    // public static final String ADVERVISER_HAVE_PROJECT = "广告主还有创建成功的项目，不能删除。";
    public static final String INVALID_CAMPAIGN_NAME = "项目名称不能为空";
    public static final String INVALID_CREATIVE_NAME = "创意名称不能为空";
    public static final String INVALID_TOTAL_BUDGET = "必须填写预算值";
    public static final String PROJECT_HAVE_CAMPAIGN = "项目下还存在创建成功的活动，不能删除。";
    public static final String CAMPAIGN_HAVE_CREATIVE = "活动下还存在创建成功的创意，不能删除。";
    public static final String TEMPLET_NOT_FUOUND = "未找到模版";
    public static final String TEMPLET_NOT_MAP_SIZE = "素材尺寸与模版支持尺寸不符";
    public static final String TEMPLET_NOT_MAP_VOLUME = "素材大小与模版支持大小不符";
    public static final String TEMPLET_NOT_MAP_TIMELENGTH = "素材时长与模版支持时长不符";
    public static final String IMAGE_NOT_MAP_SIZE = "图片尺寸不符";
    public static final String IMAGE_NOT_MAP_VOLUME = "图片大小超过最大限制";
    public static final String LANDPAGE_HAVE_CAMPAIGN = "落地页已绑定活动，不能删除。";
	public static final String CAMPAIGN_NO_CREATIE = "活动下无创意，不能投放。";
	public static final String CAMPAIGN_NO_PASS_CREATIE = "活动下有创意未通过审核，不能投放。";
	public static final String CAMPAIGN_NO_LANDPAGE = "活动未绑定落地页，不能投放。";
	public static final String CAMPAIGN_NO_TMPL_PRICE = "活动下无模版价格，不能投放。";
	public static final String RULE_HAVE_CAMPAIGN = "规则已绑定活动，不能删除。";
	public static final String CAMPAIGN_START = "活动已投放，创意不能删除。";
//	public static final String NAME_NOT_REPEAT = "名称重复，操作失败！";
//	public static final String CAMPAIGN_DATE_ERROR = "活动周期错误，操作失败！";
//	public static final String CAMPAIGN_TOTAL_BUDGET_BIGGER_PROJECT = "项目下活动预算总和不能大于项目预算！";
	public static final String PROJECT_TOTAL_BUDGET_SMALL_CAMPAIGN = "项目预算不能小于项目下活动预算总和！";
	public static final String CAMPAIGN_DAILY_BUDGET_BIGGER_TOTAL = "活动日预算不能大于活动总预算！";
	
	
	
	//public static final String NAME_NOT_NULL = "名称不能为空";
	//public static final String LENGTH_ERROR_ID = "id长度不能超过36";
	//public static final String LENGTH_ERROR_NAME = "名称长度不能超过100";
	public static final String LENGTH_ERROR_REMARK = "备注长度不能超过400";
	public static final String LENGTH_ERROR_BUDGET = "总预算不能超过99999999";
	
	public static final String ADVERTISER_NOTNULL_INDUSTY = "必须选择行业";
	public static final String ADVERTISER_NOTNULL_COMPANY = "必须填写公司名称";
	public static final String ADVERTISER_NOTNULL_SITEURL = "必须填写公司官网地址";
	public static final String ADVERTISER_NOTNULL_SITENAME = "必须填写公司官网名称";
	public static final String ADVERTISER_NOTNULL_CONTACT = "必须填写联系人";
	public static final String ADVERTISER_NOTNULL_PHONE = "必须填写联系电话";
	public static final String ADVERTISER_NOTNULL_LICENSENO = "必须填写营业执照编号";
	public static final String ADVERTISER_NOTNULL_LICENSEPATH = "必须上传营业执照图片";
	
	public static final String ADVERTISER_LENGTH_ERROR_COMPANY = "公司名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_ADDRESS= "公司地址长度不能超过200";
	public static final String ADVERTISER_LENGTH_ERROR_SITEURL = "公司官网地址长度不能超过200";
	public static final String ADVERTISER_LENGTH_ERROR_SITENAME = "公司官网名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_BRANDNAME = "品牌名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_CONTACT = "联系人长度不能超过20";
	public static final String ADVERTISER_LENGTH_ERROR_PHONE= "联系电话长度不能超过20";
	public static final String ADVERTISER_LENGTH_ERROR_EMAIL = "邮箱长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_QQ = "QQ号码长度不能超过20";
	public static final String ADVERTISER_LENGTH_ERROR_LICENSENO = "营业执照编号长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_ORGANIZATIONNO = "组织机构代码长度不能超过100";
	
	public static final String PROJECT_NOTNULL_ADVERTISERID = "广告主ID不能为空";
	public static final String PROJECT_NOTNULL_KPI = "必须选择KPI指标";
	public static final String PROJECT_NOTNULL_KPI_VALUE = "必须填写KPI指标值";
	
	public static final String PROJECT_LENGTH_ERROR_ADVERTISERID = "广告主ID长度不能超过36";
	public static final String PROJECT_LENGTH_ERROR_KPIID = "KPI指标ID长度不能超过36";
	
	public static final String CAMPAIGN_NOTNULL_PROJECTID = "项目ID不能为空";
	public static final String CAMPAIGN_NOTNULL_TYPE = "活动类型不能为空";
	public static final String CAMPAIGN_NOTNULL_STARTDATE = "开始时间不能为空";
	public static final String CAMPAIGN_NOTNULL_ENDDATE = "结束时间不能为空";
	public static final String CAMPAIGN_NOTNULL_TOTALBUDGET = "活动总预算不能为空";
	public static final String CAMPAIGN_NOTNULL_DAILYBUDGET = "活动日预算不能为空";
	public static final String CAMPAIGN_NOTNULL_DAILYIMPRESSION = "活动日均最大展现量不能为空";
	public static final String CAMPAIGN_NOTNULL_DAILYCLICK = "活动日均最大点击量不能为空";
	public static final String CAMPAIGN_NOTNULL_FREQUENCY_CONTROLOBJ = "活动频次控制目标不能为空";
	public static final String CAMPAIGN_NOTNULL_FREQUENCY_TIMETYPE = "活动频次控制时段类型不能为空";
	public static final String CAMPAIGN_NOTNULL_FREQUENCY_NUMBER = "活动频次次数不能为空";
	
	public static final String CAMPAIGN_LENGTH_ERROR_PROJECTID = "项目ID长度不能超过36";
	
	public static final String CREATIVE_NOTNULL_CAMPAIGNID = "活动ID不能为空";
	public static final String CREATIVE_NOTNULL_TMPLID = "模板ID不能为空";
	public static final String CREATIVE_NOTNULL_IMAGE_ID = "图片素材ID不能为空";
	public static final String CREATIVE_NOTNULL_IMAGE_TMPLID = "图片素材模版ID不能为空";
	public static final String CREATIVE_NOTNULL_IMAGE_PRICE = "图片素材价格不能为空";
	public static final String CREATIVE_NOTNULL_VIDEO_ID = "视频素材ID不能为空";
	public static final String CREATIVE_NOTNULL_VIDEO_TMPLID = "视频素材模版ID不能为空";
	public static final String CREATIVE_NOTNULL_VIDEO_PRICE = "视频素材价格不能为空";
	public static final String CREATIVE_NOTNULL_INFO_ID = "信息流素材标题ID不能为空";
	public static final String CREATIVE_NOTNULL_INFO_TITLE = "信息流素材标题不能为空";
	public static final String CREATIVE_NOTNULL_INFO_PRICE = "信息流素材价格不能为空";
	public static final String CREATIVE_NOTNULL_INFO_TMPLID = "信息流素材模版ID不能为空";
	
	public static final String CREATIVE_LENGTH_ERROR_CAMPAIGNID = "活动ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_IMAGE_ID = "图片素材ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_IMAGE_TMPLID = "图片素材模版ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_VIDEO_ID = "视频素材ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_VIDEO_TMPLID = "视频素材模版ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_VIDEO_IMAGEID = "视频素材图片ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_ID = "视频素材ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_TITLE = "信息流素材标题长度不能超过100";
	public static final String CREATIVE_LENGTH_ERROR_INFO_DESCRIPTION = "信息流素材描述长度不能超过100";
	public static final String CREATIVE_LENGTH_ERROR_INFO_CTADESCRIPTION = "信息流素材CTA描述长度不能超过100";
	public static final String CREATIVE_LENGTH_ERROR_INFO_ICONID = "信息流素材小图ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_IMAGEID1 = "信息流素材大图1 ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_IMAGEID2 = "信息流素材大图2 ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_IMAGEID3 = "信息流素材大图3 ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_IMAGEID4 = "信息流素材大图4 ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_IMAGEID5 = "信息流素材大图5 ID长度不能超过36";
	public static final String CREATIVE_LENGTH_ERROR_INFO_TMPLID = "信息流素材模版ID长度不能超过36";
	
	
	
//	public static final String LANDPAGE_NOTNULL_CAMPAIGNID = "活动ID不能为空";
	public static final String LANDPAGE_NOTNULL_URL = "落地页地址不能为空";
	
	public static final String LANDPAGE_LENGTH_ERROR_URL = "落地页地址长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_MONITORURL = "第三方监测地址长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_ANIDDEEP = "安卓 DeepLink长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_IOSDEEP = "ios Deepl长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_REMARK = "备注长度不能 超过200";
	
	
	public static final String CREATIVE_HAVE_CHECKED = "审核过的创意不能再次提交审核";
	public static final String CREATIVE_HAVE_SYCHRONIZED = "审核过的创意不能再次同步";
	
	
	public static final String DIF_TOTAL_BIGGER_REDIS = "总预算减小值大于redis中剩余值，修改失败！";
	public static final String DIF_DAILY_BIGGER_REDIS = "日预算减小值大于redis中剩余值，修改失败！";
	public static final String DIF_IMPRESSION_BIGGER_REDIS = "日展现减小值大于redis中剩余值，修改失败！";
	
    
}
