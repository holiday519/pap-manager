package com.pxene.pap.constant;

public class PhrasesConstant
{
	// 通用
	public static final String ID_NOT_NULL = "ID不能为空";
	public static final String NAME_NOT_NULL = "名称不能为空";
	public static final String CODE_NOT_NULL = "项目编号不能为空";
	public static final String NAME_NOT_REPEAT = "名称不能重复";
	public static final String OBJECT_NOT_FOUND = "该对象不存在";
	public static final String LENGTH_ERROR_NAME = "名称长度超过限定";
	public static final String LENGTH_ERROR_CODE = "项目编号长度超过限定";
	public static final String LACK_NECESSARY_PARAM = "缺少必要的参数";
	public static final String ARGUMENT_FORMAT_INCORRECT = "参数格式不正确";
	public static final String PARAM_OUT_OF_RANGE = "参数值超出了其允许的范围";
	public static final String PARAM_ERROR = "参数错误";
	// 广告主
	public static final String ADVERTISER_NOT_FOUND = "该客户不存在";
	public static final String ADVERTISER_AUDIT_NOT_FOUND = "该客户审核信息不存在";
	public static final String ADVERVISER_HAVE_PROJECT = "该客户下存在项目，不能删除";
	public static final String ADVERVISER_NOT_HAVE_ADX = "客户下没有可用的ADX";
	public static final String ADVERVISER_ADX_AUDIT_SUCCESS = "已审核成功的ADX不能取消";
	public static final String ADVERVISER_STATUS_CANNOT_AUDIT = "此状态，不能进行提审。";
	public static final String ADVERVISER_STATUS_CANNOT_SYNCHRONIZE = "此状态，不能进行同步。";
	// 项目
	public static final String PROJECT_BUDGET_UNDER_CAMPAIGN = "项目预算不能小于项目下活动日预算";
	public static final String EFFECT_CODE_NOT_FOUND = "转化字段编号不存在";
	public static final String AVAILABLE_ADX_NOT_FOUND = "该客户下没有可用的adx";
	public static final String PROJECT_INFO_IS_NULL = "项目信息为空";
	public static final String STATICS_INFO_IS_NULL = "静态值信息为空";
	public static final String FORMULA_ISNOT_LEGAL = "公式不符合规则";
	public static final String RULE_NAME_NOT_REPEAT = "规则名称不能重复";
	public static final String FORMULA_NAME_NOT_REPEAT = "公式名称不能重复";
	public static final String PROJECT_CODE_NOT_REPEAT = "项目编号不能重复";
	// 活动
	public static final String CAMPAIGN_NOT_FOUND = "该活动不存在";
	public static final String CAMPAIGN_DATE_ERROR = "活动周期设置有误";
	public static final String CAMPAIGN_DAILY_BUDGET_OVER_PROJECT = "项目下活动日预算不能大于项目预算";
	public static final String FREQUENCY_NOT_COMPLETE = "频次信息不全";
	public static final String CAMPAIGN_DAILY_BUDGET_OVER_TOTAL = "活动日预算不能大于项目总预算";
	public static final String CAMPAIGN_NO_LANDPAGE = "活动未绑定落地页，不能投放";
	public static final String CAMPAIGN_NO_CREATIE = "活动下无创意，不能投放";
	public static final String WBLIST_FILE_NOT_FOUND = "找不到人群定向文件";
	public static final String CAMPAIGN_BEGIN = "活动已开始，创意不能删除";
	public static final String LANDPAGE_INFO_NULL = "活动页信息为空，不能创建活动";
	public static final String PROJECT_INFO_NULL = "项目信息为空，不能创建活动";
	public static final String APP_NOT_FOUND = "活动定向中未选定APP";
	public static final String LANDPAGE_CODE_USED = "正在使用监测码，不能重复使用。";
	public static final String LANDPAGE_CODE_TODAY_USED = "今天已监测码，不能重复使用。";
	public static final String CAMPAIGN_STARTDATE_BEFORE_TODAY = "活动的开始时间，不能在今天之前。";
	public static final String CAMPAIGN_ENDDATE_BEFORE_TODAY = "活动的结束时间，不能在今天之前。";
	public static final String CAMPAIGN_QUANTITY_NOTONLY_ONE = "活动存在高级设置，不能编辑时间。";
	public static final String CAMPAIGN_IS_END_NOT_OPEN = "活动已完成，不能打开开关。";
	public static final String CAMPAIGN_APPTARGET_IS_NULL = "没有设置定向，不能打开开关。";
	// 创意
    public static final String INVALID_PHONE = "不是正确的手机号码或座机号码";
    public static final String CREATIVE_NOT_FOUND = "该创意不存在";
    // redis
    public static final String REDIS_KEY_LOCK = "redis的key被死锁";
	public static final String REDIS_DAY_BUDGET = "超出了最大日预算";
	public static final String REDIS_DAY_COUNTER = "超出日均最大展现的上限";
	public static final String REDIS_DAILY_BUDGET = "日预算为空";
	public static final String REDIS_DAILY_COUNTER = "日均最大展现为空";
	public static final String REDIS_GROUPIDS_NULL = "redis中没有Groupids信息";
	public static final String REDIS_CAMPAIGNINFO_NULL = "redis中没有活动信息";
	public static final String REDIS_PROJECTBUDGET_NULL = "redis中项目预算为空";
	
	public static final String ADX_NOT_FOUND = "ADX不存在";
	public static final String POPULATION_FILE_USED = "名单被使用，不能删除";
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
	public static final String CAMPAIGN_NO_PASS_CREATIE = "活动下有创意未通过审核，不能投放。";
	public static final String CAMPAIGN_NO_TMPL_PRICE = "活动下无模版价格，不能投放。";
	public static final String RULE_HAVE_CAMPAIGN = "规则已绑定活动，不能删除。";
	public static final String CREATIVE_AUDIT_NULL = "创意审核出现异常";
	
	
	
	public static final String LENGTH_ERROR_ID = "id长度不能超过36";
	public static final String LENGTH_ERROR_REMARK = "备注长度不能超过400";
	public static final String LENGTH_ERROR_BUDGET = "总预算不能超过99999999";
	
	public static final String ADVERTISER_NOTNULL_INDUSTY = "必须选择行业";
	public static final String ADVERTISER_NOTNULL_COMPANY = "必须填写公司名称";
	public static final String ADVERTISER_NOTNULL_ADX = "必须选择ADX";
	
	public static final String ADVERTISER_LENGTH_ERROR_COMPANY = "公司名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_SITEURL = "公司官网地址长度不能超过200";
	public static final String ADVERTISER_LENGTH_ERROR_SITENAME = "公司官网名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_BRANDNAME = "品牌名称长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_QQ = "QQ号码长度不能超过20";
	public static final String ADVERTISER_LENGTH_ERROR_QUALIFICATIONNO = "资质编号长度不能超过100";
	public static final String ADVERTISER_LENGTH_ERROR_QUALIFICATIONPATH = "资质图片路径长度不能超过200";
	public static final String ADVERTISER_AUDIT_ERROR = "广告主未通过ADX审核";
	public static final String ADVERTISER_LENGTH_ERROR_LEGALNAME = "客户主体名称长度不能超过100";
	
	public static final String PROJECT_NOTNULL_ADVERTISERID = "广告主ID不能为空";
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
	
	
	
	public static final String LANDPAGE_NOTNULL_URL = "落地页地址不能为空";
	public static final String LANDPAGE_LENGTH_ERROR_URL = "落地页地址长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_MONITORURL = "第三方监测地址长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_ANIDDEEP = "安卓 DeepLink长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_IOSDEEP = "ios Deepl长度不能 超过400";
	public static final String LANDPAGE_LENGTH_ERROR_REMARK = "备注长度不能 超过200";
	public static final String LANDPAGE_USED_ERROR_CAMPAIGNID_USE = "落地页正在被使用，不能修改";

	
	public static final String CREATIVE_HAVE_CHECKED = "审核过的创意不能再次提交审核";
	public static final String CREATIVE_HAVE_SYCHRONIZED = "审核过的创意不能再次同步";
	public static final String CREATIVE_TYPE_NOT_SUPPORT = "不支持的创意类型";
	
	
	public static final String DIF_TOTAL_BIGGER_REDIS = "设置的总预算不能小于已消费的预算";//"总预算减小值大于redis中剩余值，修改失败！";
	public static final String DIF_DAILY_BIGGER_REDIS = "日预算减小值大于redis中剩余值，修改失败！";
	public static final String DIF_IMPRESSION_BIGGER_REDIS = "日展现减小值大于redis中剩余值，修改失败！";
    
	public static final String POPULATION_FILE_ERROR = "人群定向文件格式不正确";
	
	public static final String EFFECT_TEMPLATE_FORMAT_ERROR = "转化数据模板文件格式错误！";
	public static final String EFFECT_BE_USED_ERROR = "转化值正在被公式或规则引用，不能关闭";
	public static final String EFFECT_TEMPLATE_FILE_HAVE_EXISTS = "项目中已存在同名的转化数据文件";

	public static final String STATIC_RULER_DELETE_ERROR = "静态值正在被规则引用，删除失败！";
	public static final String STATIC_FORMULATE_DELETE_ERROR = "静态值正在被公式引用，删除失败！";
	public static final String STATIC_VALUE_LENGTH_TOO_LANG = "静态值的取值不能大于99999999.9999和小于-99999999.9999";
	public static final String FORMULA_REFERENCE_VALUE_NULL = "公式参考值不能为空！";
	public static final String RULE_REFERENCE_VALUE_NULL = "规则参考值不能为空！";
	public static final String RULEID_IS_NULL = "规则ID不能为空";
	public static final String VERNIER_IS_NULL = "游标不能为空";
	public static final String WEIGHT_IS_NULL = "权重不能为空";
	public static final String CONDITION_IS_NULL = "触发条件的公式不能为空";
	public static final String RELATION_IS_NULL = "关系不能为空";
	public static final String VERNIER_SIGN_SYMBOL_SAME = "正向游标和负向游标，不能同为正数或同为负数";
		
	public static final String FORMULA_IS_NULL = "评估公式的公式不能为空";
	public static final String WEIGHTS_ISNOT_CORRECT = "权重之和不为1";
	public static final String STATICS_NAME_IS_SAME = "静态值名称不能重复";
	public static final String EFFECTDIC_NAME_IS_SAME = "转化值名称不能重复";
	public static final String EFFECTDIC_NAME_IS_NULL = "转化值名称不能为空";
	public static final String STATICS_NAME_IS_NULL = "静态值不能为空";

	public static final String USED_FIXED_NAME = "名称不能使用：展现数、点击数、二跳、成本、修正成本";
    public static final String RULE_TRIGGER_ERROR = "判断触发条件发生异常";
    public static final String FORMULA_RESULT_ERROR = "公式结果异常";
    public static final String CAMPAIGN_SCORE_ERROR = "评分异常";
    
    public static final String EXCEL_CREATE_ERROR = "excel文件生成失败";
    public static final String EXCEL_DELETE_ERROR = "excel文件删除失败";
    
}
