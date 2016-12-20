package com.pxene.pap.service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModel;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModelExample;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CreativeAuditModel;
import com.pxene.pap.domain.model.basic.CreativeAuditModelExample;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModel;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModelExample;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;
import com.pxene.pap.repository.basic.view.ImageSizeTypeDao;

@Service
public class AuditCreativeBaiduService {

	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private AdvertiserDao advertiserDao;
	
    @Autowired
    private AdvertiserAuditDao advertiserAuditDao;
    
    @Autowired
    private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private AuditAdvertiserBaiduService auditAdvertiserBaiduService;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
	@Autowired
	private CreativeImageDao creativeImageDao;
	
	@Autowired
	private CreativeVideoDao creativeVideoDao;
	
	@Autowired
	private CreativeInfoflowDao creativeInfoflowDao;
	
	@Autowired
	private ImageSizeTypeDao imageSizeTypeDao;
	
	@Autowired
	private AdxDao adxDao;
	
	public void audit(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB==null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		//查询创意下的各个mapid,分别进行审核
		CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
		mapExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeMaterialModel> mapModels = creativeMaterialDao.selectByExample(mapExample);
		
		if (mapModels != null && !mapModels.isEmpty()) {
			for (CreativeMaterialModel mapModel : mapModels) {
				String mapId = mapModel.getId();
				String creativeType = mapModel.getCreativeType();
				// 图片创意
				if ("1".equals(creativeType)) {
					auditImgCreative(mapId, "add");
				// 视频创意
				} else if ("2".equals(creativeType)) {
//					auditVideoCreative(mapId);
				// 信息流创意
				} else if ("3".equals(creativeType)) {
					auditInfoCreative(mapId, "add");
				}
			}
		}
		
	}
	/**
	 * 重新审核
	 * @param creativeId
	 * @throws Exception
	 */
	public void auditEdit(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB==null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		//查询创意下的各个mapid,分别进行审核
		CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
		mapExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeMaterialModel> mapModels = creativeMaterialDao.selectByExample(mapExample);
		
		if (mapModels != null && !mapModels.isEmpty()) {
			for (CreativeMaterialModel mapModel : mapModels) {
				String mapId = mapModel.getId();
				String creativeType = mapModel.getCreativeType();
				// 图片创意
				if ("1".equals(creativeType)) {
					auditImgCreative(mapId, "edit");
					// 视频创意
				} else if ("2".equals(creativeType)) {
					//暂无视频创意
//					auditVideoCreative(mapId);/
					// 信息流创意
				} else if ("3".equals(creativeType)) {
					auditInfoCreative(mapId, "edit");
				}
			}
		}
		
	}
	/**
	 * 审核图片创意
	 * @param mapId
	 * @throws Exception
	 */
	@Transactional
	public void auditImgCreative(String mapId, String type) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		//调用接口----百度第一次审核  与再次审核还用这个字段  但是值在再次审核时更新了
		String cexamineurl = "";
		if ("edit".equals(type)) {
			cexamineurl = adxModel.getCupdateUrl();
		} else {
			cexamineurl = adxModel.getCexamineUrl();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("token") == null) {
			throw new NotFoundException("baidu : 缺少私密key");//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
    	//将私密key转成json格式
		Long dspId = json.get("dspId").getAsLong();
		String token = json.get("token").getAsString();
		//广告主审核数字key
		Long advertiserIdValue = getAdvAuditValueByMapId(mapId);
		//创意审核key
		Long creativeIdvalue = (long) Math.floor((new Random()).nextDouble() * 1000000000D);//————————————————————
		//创意审核表中插入数据
		CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
		creativeAuditModel.setId(UUID.randomUUID().toString());
		creativeAuditModel.setAdxId(AdxKeyConstant.ADX_BAIDU_VALUE);
		creativeAuditModel.setAuditValue(String.valueOf(creativeIdvalue));
		creativeAuditModel.setCreativeId(mapId);
		creativeAuditModel.setStatus("00");
		creativeAuditDao.insertSelective(creativeAuditModel);
		
		JsonObject creative = new JsonObject();//创意信息
		//流量类型 默认为Mobile流量——只支持图片、图文
		int adviewType = 2;
		creative.addProperty("creativeId", creativeIdvalue);
		creative.addProperty("adviewType", adviewType);
		//广告类型（1：图片  2：flash  3：视频  4：图文（信息流））
		Integer typeValue = 1;
		//从视图中查询出mapid对应信息
		CreativeImageModelExample ciExample = new CreativeImageModelExample();
		ciExample.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeImageModelWithBLOBs> creativeImages = creativeImageDao.selectByExampleWithBLOBs(ciExample);
		CreativeImageModelWithBLOBs creativeImage = null;
		for (CreativeImageModelWithBLOBs mol : creativeImages) {
			creativeImage = mol;
		}
		int width;//宽
		int height;//高
		if (creativeImage.getW() != null && creativeImage.getH() != null) {
			width = creativeImage.getW();
			height = creativeImage.getH();
		} else {
			throw new NotFoundException("百度创意提交第三方审核执行失败！原因：获取图片尺寸异常！");
		}
		String ftype = creativeImage.getFtype();
		String sourceUrl = creativeImage.getSourceUrl();
		if ("2".equals(ftype) || "3".equals(ftype)) {// 不止2、3（此处需要根据图片格式的code待定）——————————————————（jpg、png、gif、swf四种格式都需要）
//			byte[] imgContent = GlobalUtil.getImageContent(sourceUrl);
//			creative.addProperty("binaryData", new String(imgContent));// 创意二进制数据
		}
		creative.addProperty("Width", width);
		creative.addProperty("Height", height);
		creative.addProperty("creativeUrl", sourceUrl);// ————————————————————图片服务器地址

		// 点击链接targetUrl
		String encodeUrl = URLEncoder.encode(creativeImage.getCurl(), "UTF-8");
		String targetUrl = "http://cl2.pxene.com" + adxModel.getClickUrl() + "&curl=" + encodeUrl;// 平台的点击地址 + 编码后的落地页——————需要点击地址————————————
		creative.addProperty("targetUrl", targetUrl);
		String imonitorUrl = creativeImage.getImonitorUrl();
		String[] imonitors = imonitorUrl.split("##");
		JsonArray imot = new JsonArray();
		if (imonitors != null && imonitors.length > 0) {
			for (String im : imonitors) {
				imot.add(im);
			}
		}
		if (!StringUtils.isEmpty(adxModel.getImpressionUrl())) {
			imot.add("http://cl2.pxene.com" + adxModel.getImpressionUrl());// 需要点击地址——————————————————
		}
		creative.addProperty("monitorUrls", imot.toString());
		creative.addProperty("creativeTradeId", 60);// 此处需要进行调整，创意所属广告行业——————————————————
		creative.addProperty("advertiserId", advertiserIdValue);
		// 创意的互动样式interactiveStyle（0：无 1：电话直拨 2：点击下载）
		int interactiveStyle = 0;
		String promotiontype = getCampaignPromotionTypeByMapId(mapId);
		if ("a".equals(promotiontype)) {
			interactiveStyle = 2;
			Float appPackageSize = 0F;
			creative.addProperty("appName", creativeImage.getApkName());// 应用名称
			creative.addProperty("appDesc", creativeImage.getAppDescription());//应用介绍
			creative.addProperty("downloadUrl", creativeImage.getDownloadUrl());// 下载包地址
			creative.addProperty("appPackageSize", appPackageSize);// 应用大小
		} else if ("b".equals(promotiontype)) {
			interactiveStyle = 0;
			// 拨打电话暂时无法使用
		} else if ("e".equals(promotiontype)) {
			interactiveStyle = 0;
		}
		creative.addProperty("Type", typeValue);
		creative.addProperty("interactiveStyle", interactiveStyle);
		// 如果是跳转页面添加落地页跳转地址；否则添加app下载地址
		if (!StringUtils.isEmpty(creativeImage.getLandingUrl())) {//到达页面
			creative.addProperty("landingPage", creativeImage.getLandingUrl());
		} else {
			creative.addProperty("landingPage", creativeImage.getDownloadUrl());
		}
		// 组成“请求头”
		JsonObject authHeader = new JsonObject();
		authHeader.addProperty("dspId", dspId);
		authHeader.addProperty("token", token);

		// 创意参数数组
		JsonArray creatives = new JsonArray();
		creatives.add(creative);
		JsonObject param = new JsonObject();
		param.addProperty("authHeader", authHeader.toString());
		param.addProperty("request", creatives.toString());
		System.out.println(param.toString());
		//发送请求
		Map<String, String> map = auditAdvertiserBaiduService.post(cexamineurl, param.toString());
		//返回结构中取出reslut
		String result = map.get("result");
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		//查询状态
        int status = jsonMap.get("status").getAsInt();
        String statucode = jsonMap.get("statucode").getAsString();
		if ("200".equals(statucode) && status == 0) {
			CreativeAuditModelExample ex = new CreativeAuditModelExample();
			ex.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus("03");
			creativeAuditDao.updateByExampleSelective(creativeAuditModel, ex);
		}else{
			throw new IllegalStateException("百度创意提交第三方审核执行失败！原因：" + jsonMap.get("message").getAsString());
		}
	}
	/**
	 * 审核视频创意
	 * @param mapId
	 * @throws Exception
	 */
	public void auditVideoCreative(String mapId) throws Exception {
		//暂不接受视频创意
	}
	/**
	 * 审核信息流创意
	 * @param mapId
	 * @throws Exception
	 */
	public void auditInfoCreative(String mapId, String type) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		//调用接口----百度第一次审核  与再次审核还用这个字段  但是值在再次审核时更新了
		String cexamineurl = "";
		if ("edit".equals(type)) {
			cexamineurl = adxModel.getCupdateUrl();
		} else {
			cexamineurl = adxModel.getCexamineUrl();
		}
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("token") == null) {
			throw new NotFoundException("baidu : 缺少私密key");//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
    	//将私密key转成json格式
		Long dspId = json.get("dspId").getAsLong();
		String token = json.get("token").getAsString();
		//广告主审核数字key
		Long advertiserIdValue = getAdvAuditValueByMapId(mapId);
		//创意审核key
		Long creativeIdvalue = (long) Math.floor((new Random()).nextDouble() * 1000000000D);//————————————————————
		//创意审核表中插入数据
		CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
		creativeAuditModel.setId(UUID.randomUUID().toString());
		creativeAuditModel.setAdxId(AdxKeyConstant.ADX_BAIDU_VALUE);
		creativeAuditModel.setAuditValue(String.valueOf(creativeIdvalue));
		creativeAuditModel.setCreativeId(mapId);
		creativeAuditModel.setStatus("00");
		creativeAuditDao.insertSelective(creativeAuditModel);
		
		JsonObject creative = new JsonObject();//创意信息
		//流量类型 默认为Mobile流量——只支持图片、图文
		int adviewType = 2;
		creative.addProperty("creativeId", creativeIdvalue);
		creative.addProperty("adviewType", adviewType);
		//广告类型（1：图片  2：flash  3：视频  4：图文（信息流））
		Integer typeValue = 4;
		CreativeInfoflowModelExample ifExample = new CreativeInfoflowModelExample();
		ifExample.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeInfoflowModelWithBLOBs> creativeInfos = creativeInfoflowDao.selectByExampleWithBLOBs(ifExample);
		CreativeInfoflowModelWithBLOBs creativeInfo = null;
		for (CreativeInfoflowModelWithBLOBs mol : creativeInfos) {
			creativeInfo = mol;
		}
		String title = creativeInfo.getTitle();//标题
		String description = creativeInfo.getDescription();//描述
		String brandname = getAdvertiserBrandNameByMapId(mapId);//品牌名称（广告主表字段）
		if (StringUtils.isEmpty(title) || title.length() >= 18) {
			throw new IllegalStateException("百度创意提交第三方审核执行失败！原因：信息流广告推广标题最长支持18个中文字！" );
		}
		if (StringUtils.isEmpty(description) || description.length() >= 50) {
			throw new IllegalStateException("百度创意提交第三方审核执行失败！原因：信息流广告必须包含推广描述，且最长支持50个中文字！" );
		}
		if (StringUtils.isEmpty(brandname) || brandname.length() >= 14) {
			throw new IllegalStateException("百度创意提交第三方审核执行失败！原因：广告主品牌名称必须填写，且最长支持14个中文字！" );
		}
		creative.addProperty("title", title);
		creative.addProperty("description", description);
		creative.addProperty("brandname", brandname);
		creative.addProperty("Style", 5); //5：APP 图文信息流 （adviewType 必须为2）
		//原生图片信息——有顺序
		List<String> creativeUrls = new ArrayList<>();
		//图文创意二进制数据列表——有顺序
		List<byte[]> binaryDatas = new ArrayList<>();
		String icon = creativeInfo.getIcon();
		String image1 = creativeInfo.getImage1();
		if(!StringUtils.isEmpty(icon)){
			ImageSizeTypeModelExample istExample = new ImageSizeTypeModelExample();
			istExample.createCriteria().andIdEqualTo(icon);
			List<ImageSizeTypeModel> iconList = imageSizeTypeDao.selectByExample(istExample);
			ImageSizeTypeModel imageModel = null;
			for (ImageSizeTypeModel mol : iconList) {
				imageModel = mol;
			}
			String path = imageModel.getPath();
			creativeUrls.add(path);//图片地址——————————————
			if ("2".equals(imageModel.getCode()) || "3".equals(imageModel.getCode())) {// 不止2、3（此处需要根据图片格式的code待定）——————————————————（jpg、png、gif、swf四种格式都需要）
				byte[] content = GlobalUtil.getImageContent(path);
				binaryDatas.add(content);
			}
		}
		if(!StringUtils.isEmpty(image1)){
			ImageSizeTypeModelExample istExample = new ImageSizeTypeModelExample();
			istExample.createCriteria().andIdEqualTo(image1);
			List<ImageSizeTypeModel> imageList = imageSizeTypeDao.selectByExample(istExample);
			ImageSizeTypeModel imageModel = null;
			for (ImageSizeTypeModel mol : imageList) {
				imageModel = mol;
			}
			String path = imageModel.getPath();
			creativeUrls.add(path);//图片地址——————————————
			if ("2".equals(imageModel.getCode()) || "3".equals(imageModel.getCode())) {// 不止2、3（此处需要根据图片格式的code待定）——————————————————（jpg、png、gif、swf四种格式都需要）
				byte[] content = GlobalUtil.getImageContent(path);
				binaryDatas.add(content);
			}
		}
		creative.addProperty("binaryDatas", binaryDatas.toString());
		creative.addProperty("creativeUrls", creativeUrls.toString());
		
		// 点击链接targetUrl
		String encodeUrl = URLEncoder.encode(creativeInfo.getCurl(), "UTF-8");
		String targetUrl = "点击地址" + adxModel.getClickUrl() + "&curl=" + encodeUrl;// 平台的点击地址 + 编码后的落地页——————需要点击地址————————————
		creative.addProperty("targetUrl", targetUrl);
		String imonitorUrl = creativeInfo.getImonitorUrl();
		String[] imonitors = imonitorUrl.split("##");
		JsonArray imot = new JsonArray();
		if (imonitors != null && imonitors.length > 0) {
			for (String im : imonitors) {
				imot.add(im);
			}
		}
		if (!StringUtils.isEmpty(adxModel.getImpressionUrl())) {
			imot.add("点击地址" + adxModel.getImpressionUrl());// 需要点击地址——————————————————
		}
		creative.addProperty("monitorUrls", imot.toString());
		creative.addProperty("creativeTradeId", 2);// 此处需要进行调整，创意所属广告行业——————————————————
		creative.addProperty("advertiserId", advertiserIdValue);
		// 创意的互动样式interactiveStyle（0：无 1：电话直拨 2：点击下载）
		int interactiveStyle = 0;
		String promotiontype = getCampaignPromotionTypeByMapId(mapId);
		if ("a".equals(promotiontype)) {
			interactiveStyle = 2;
			Float appPackageSize = 0F;
			creative.addProperty("appName", creativeInfo.getApkName());// 应用名称
			creative.addProperty("appDesc", creativeInfo.getAppDescription());//应用介绍
			creative.addProperty("downloadUrl", creativeInfo.getDownloadUrl());// 下载包地址
			creative.addProperty("appPackageSize", appPackageSize);// 应用大小
		} else if ("b".equals(promotiontype)) {
			interactiveStyle = 0;
			// 拨打电话暂时无法使用
		} else if ("e".equals(promotiontype)) {
			interactiveStyle = 0;
		}
		creative.addProperty("Type", typeValue);
		creative.addProperty("interactiveStyle", interactiveStyle);
		// 如果是跳转页面添加落地页跳转地址；否则添加app下载地址
		if (!StringUtils.isEmpty(creativeInfo.getLandingUrl())) {
			creative.addProperty("downloadUrl", creativeInfo.getLandingUrl());
		} else {
			creative.addProperty("downloadUrl", creativeInfo.getDownloadUrl());
		}
		// 组成“请求头”
		JsonObject authHeader = new JsonObject();
		authHeader.addProperty("dspId", dspId);
		authHeader.addProperty("token", token);

		// 创意参数数组
		JsonArray creatives = new JsonArray();
		creatives.add(creative);
		JsonObject param = new JsonObject();
		param.addProperty("authHeader", authHeader.toString());
		param.addProperty("request", creatives.toString());
		//发送请求
		Map<String, String> map = auditAdvertiserBaiduService.post(cexamineurl, param.toString());
		//返回结构中取出reslut
		String result = map.get("result");
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		//查询状态
        int status = jsonMap.get("status").getAsInt();
        String statucode = jsonMap.get("statucode").getAsString();
		if ("200".equals(statucode) && status == 0) {
			CreativeAuditModelExample ex = new CreativeAuditModelExample();
			ex.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
			CreativeAuditModel mod = new CreativeAuditModel();
			mod.setStatus("03");
			creativeAuditDao.updateByExampleSelective(creativeAuditModel, ex);
		}else{
			throw new IllegalStateException("百度创意提交第三方审核执行失败！原因：" + jsonMap.get("message").getAsString());
		}
	}

	/**
	 * 同步创意
	 * @param mapId
	 * @throws Exception
	 */
	public void synchronize(String mapId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		String aexamineresultUrl = adxModel.getAexamineresultUrl();
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("dspId") == null || json.get("token") == null) {
			throw new NotFoundException("baidu : 缺少私密key");//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
		Long dspId = json.get("dspId").getAsLong();
		String token = json.get("token").getAsString();
		//组成“请求头”
    	JsonObject authHeader = new JsonObject();
    	authHeader.addProperty("dspId", dspId);
    	authHeader.addProperty("token", token);
    	
    	CreativeAuditModelExample ex = new CreativeAuditModelExample();
		ex.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(ex);
		CreativeAuditModel auditModel = null;
		if (list != null && !list.isEmpty()) {
			for (CreativeAuditModel cre : list) {
				auditModel = cre;
			}
		} else {
			throw new NotFoundException();
		}
		String auditValue = auditModel.getAuditValue();
		JsonArray values = new JsonArray();
		values.add(Long.parseLong(auditValue));
		JsonObject obj = new JsonObject();
		obj.addProperty("authHeader", authHeader.toString());
		obj.addProperty("creativeIds", values.toString());
		//发送同步请求
		Map<String, String> map = auditAdvertiserBaiduService.post(aexamineresultUrl, obj.toString());
		//返回结构中取出reslut
		String result = map.get("result");
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		//获取状态
		int status = jsonMap.get("status").getAsInt();
		String statucode = jsonMap.get("statucode").getAsString();
		if ("200".equals(statucode) && status == 0) {
			int state = 1;
			String refuseReasonStr = "";
			JsonArray array = jsonMap.get("response").getAsJsonArray();
			if (array !=null && array.size() >0 ) {
				JsonObject jsonObj = array.get(0).getAsJsonObject();
				state = jsonObj.get("state").getAsInt();
				refuseReasonStr = jsonObj.get("refuseReason").getAsString();
			}
			
			String creativeAdxStatus;
			if(state == 0){
				creativeAdxStatus = "00";//审核通过
			}else if(state == 2){
				creativeAdxStatus = "09";//审核不通过
			}else {
				creativeAdxStatus = "10";//审核中
			}
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
			CreativeAuditModel model = new CreativeAuditModel();
			model.setStatus(creativeAdxStatus);//—————状态值待确定———————
			model.setMessage(refuseReasonStr);
			creativeAuditDao.updateByExample(model, example);
		}else{
			CreativeAuditModelExample example = new CreativeAuditModelExample();
			example.createCriteria().andCreativeIdEqualTo(mapId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
			CreativeAuditModel model = new CreativeAuditModel();
			JsonArray errors = jsonMap.get("error").getAsJsonArray();
			JsonObject error = errors.get(0).getAsJsonObject();
			String massage = error.get("massage").getAsString();
			model.setMessage(massage);
			creativeAuditDao.updateByExample(model, example);
		}
	}
	/**
	 * 查询创意对应广告主的审核值
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public Long getAdvAuditValueByMapId (String mapId) throws Exception {
		//查询创意ID
		CreativeMaterialModel creativeMaterialModel = creativeMaterialDao.selectByPrimaryKey(mapId);
		String creativeId = creativeMaterialModel.getCreativeId();
		//查询活动ID
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		String campaignId = creativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询广告主审核Key
		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(example);
		if (list == null || list.isEmpty()) {
			throw new IllegalAccessError("百度广告主未审核");
		}
		long value = -1L;
		for (AdvertiserAuditModel adv : list) {
			String auditValue= adv.getAuditValue();
			if(!StringUtils.isEmpty(auditValue)){
				value = Long.parseLong(auditValue);
			}
		}
		return value;
	}
	
	/**
	 * 查询创意对应广告主的品牌名称
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public String getAdvertiserBrandNameByMapId (String mapId) throws Exception {
		//查询创意ID
		CreativeMaterialModel creativeMaterialModel = creativeMaterialDao.selectByPrimaryKey(mapId);
		String creativeId = creativeMaterialModel.getCreativeId();
		//查询活动ID
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		String campaignId = creativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String projectId = campaignModel.getProjectId();
		//查询广告主ID
		ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = projectModel.getAdvertiserId();
		//查询广告主的品牌名称
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(projectId);
		String brandName = advertiserModel.getBrandName();
		
		return brandName;
	}
	/**
	 * 查询创意对应活动的活动类型
	 * @param mapId
	 * @return
	 * @throws Exception
	 */
	public String getCampaignPromotionTypeByMapId (String mapId) throws Exception {
		//查询创意ID
		CreativeMaterialModel creativeMaterialModel = creativeMaterialDao.selectByPrimaryKey(mapId);
		String creativeId = creativeMaterialModel.getCreativeId();
		//查询活动ID
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		String campaignId = creativeModel.getCampaignId();
		//查询项目ID
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		String type = campaignModel.getType();
		return type;
	}
	

}
