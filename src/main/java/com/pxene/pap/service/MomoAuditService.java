package com.pxene.pap.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.common.HttpClientUtil;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.AuditErrorConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.IndustryAdxModel;
import com.pxene.pap.domain.models.IndustryAdxModelExample;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ThirdPartyAuditException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.IndustryAdxDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.basic.ProjectDao;

/**
 * 陌陌审核
 * @author lizhuoling
 *
 */
@Service
public class MomoAuditService extends AuditService {

	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;
	
	@Autowired
	private AdxDao adxDao;
	@Autowired
	private CreativeDao creativeDao;
	@Autowired
	private InfoflowMaterialDao infoflowMaterialDao;
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private CampaignDao campaignDao;
	@Autowired
	private LandpageDao landpageDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private AdvertiserDao advertiserDao;
	@Autowired
	private IndustryAdxDao industryAdxDao;
	
	/**
	 * 审核广告主
	 */
	@Override
	@Transactional
	public void auditAdvertiser(String advertiserId) throws Exception {
		AdvertiserAuditModelExample advertiserAuditModelExample =new AdvertiserAuditModelExample();
    	advertiserAuditModelExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
    	System.out.println(advertiserAuditModelExample);
    	List<AdvertiserAuditModel> advertiserAuditList = advertiserAuditDao.selectByExample(advertiserAuditModelExample);
    	if(advertiserAuditList == null && advertiserAuditList.isEmpty() ){
    		AdvertiserAuditModel advertiserAudit  = new AdvertiserAuditModel();
    		advertiserAudit.setId(UUIDGenerator.getUUID());
    		advertiserAudit.setAdvertiserId(advertiserId);
    		advertiserAudit.setAdxId(AdxKeyConstant.ADX_MOMO_VALUE);
    		advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
    		advertiserAuditDao.insertSelective(advertiserAudit);
    	}
    	
	}

	/**
	 * 同步广告主
	 */
	@Override
	@Transactional
	public void synchronizeAdvertiser(String advertiserId) throws Exception {
		AdvertiserAuditModelExample advertiserAuditModelExample =new AdvertiserAuditModelExample();
    	advertiserAuditModelExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
    	List<AdvertiserAuditModel> advertiserAuditList = advertiserAuditDao.selectByExample(advertiserAuditModelExample);
    	if(advertiserAuditList != null && !advertiserAuditList.isEmpty() ){
    		AdvertiserAuditModel advertiserAudit  = new AdvertiserAuditModel();
    		advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
    		advertiserAuditDao.updateByExample(advertiserAudit, advertiserAuditModelExample);
    	}
	}

	/**
	 * 审核创意
	 */
	@Override
	@Transactional	
	public void auditCreative(String creativeId) throws Exception {
			
		JsonObject data = new JsonObject();
		//判断创意类型是否符合
		CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
		if (!StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creative.getType())) {
			throw new ThirdPartyAuditException(AuditErrorConstant.MOMO_CREATIVE_TYPE_NOT_SUPPORT);
		}
		JsonObject nativeCreative = new JsonObject();
		InfoflowMaterialModel material = infoflowMaterialDao.selectByPrimaryKey(creative.getMaterialId());
        nativeCreative.addProperty("title", material.getTitle());
        nativeCreative.addProperty("desc", material.getDescription());
        String iconId = material.getIconId();
        nativeCreative.add("logo", getImageInfo(iconId));
        JsonArray images = new JsonArray();
        String image1Id = material.getImage1Id();
		String image2Id = material.getImage2Id();
		String image3Id = material.getImage3Id();
		String image4Id = material.getImage4Id();
		String image5Id = material.getImage5Id();
		if (!StringUtils.isEmpty(image1Id)) {
			images.add(getImageInfo(image1Id));
		}
		if (!StringUtils.isEmpty(image2Id)) {
			images.add(getImageInfo(image2Id));
		}
		if (!StringUtils.isEmpty(image3Id)) {
			images.add(getImageInfo(image3Id));
		}
		if (!StringUtils.isEmpty(image4Id)) {
			images.add(getImageInfo(image4Id));
		}
		if (!StringUtils.isEmpty(image5Id)) {
			images.add(getImageInfo(image5Id));
		}
		
		String puton_to; //广告样式 - 拼接使用（投放位置：附近动态、附近人、好友动态）
        String puton_type; //广告样式 - 拼接使用（投放类型：打开网页、下载（IOS、安卓））
        String puton_img; //广告样式 - 拼接使用（图片数量：无图、大图、三图）
		if (images.size() > 0) {
			if (images.size() == 1) { // 大图
				puton_img = "LARGE_IMG";
			} else { // 三图
				puton_img = "SMALL_IMG";
			}
			puton_to = "FEED";
			nativeCreative.add("image", images);
			data.addProperty("quality_level", 1);
		} else {
            puton_to = "NEARBY";
            puton_img = "NO_IMG";
		}
		String campaignId = creative.getCampaignId();
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		String landpageId = campaign.getLandpageId();
		LandpageModel landpage = landpageDao.selectByPrimaryKey(landpageId);
		nativeCreative.addProperty("landingpage_url", landpage.getUrl().replaceAll("&", "%26"));
		puton_type = "LANDING_PAGE";
		nativeCreative.addProperty("native_format", puton_to + "_" + puton_type + "_" + puton_img);
		data.add("native_creative", nativeCreative);
		
		JsonArray cats = new JsonArray();
		String projectId = campaign.getProjectId();
		ProjectModel project = projectDao.selectByPrimaryKey(projectId);
		String advertiserId = project.getAdvertiserId();
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
		cats.add(getIndustry(advertiser.getIndustryId()));
		data.add("cat", cats);
		
		String dspid = AdxKeyConstant.AUDIT_NAME_MOMO;
		data.addProperty("dspid", dspid);
		data.addProperty("cid", projectId);
		data.addProperty("adid", campaignId);
		data.addProperty("crid", creativeId);
		data.addProperty("advertiser_id", advertiserId);
		data.addProperty("advertiser_name", advertiser.getName());

		long uptime = System.currentTimeMillis() / 1000;
		// 提交时间，Unix时间戳，单位秒，数字类型
		data.addProperty("uptime", uptime);
		Date current = new Date();
		String expiry_date = DateUtils.getDayOfChange(current, 120) + " 23:59:59";
		// 素材过期时间，最大支持当前日期往后120天
		data.addProperty("expiry_date", expiry_date);
		// 签名生成
        String sign = GlobalUtil.MD5("appkey" + dspid + "crid" + creativeId + "uptime" + uptime).toUpperCase();
        data.addProperty("sign", sign);
        String dataStr = "data=" + data.toString(); //转换数据格式
		// 创意审核地址
		//String cexamineurl = momoModel.getCexamineUrl();
        AdxModel momoAdx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineurl = momoAdx.getCexamineUrl();
        String creativeAuditResult=HttpClientUtil.getInstance().sendHttpPostJson(cexamineurl, dataStr);
        Gson gson = new Gson();
        JsonObject creativeAuditJson = gson.fromJson(creativeAuditResult, new JsonObject().getClass()); //转换数据格式
        if(creativeAuditJson.get("ec")!=null && creativeAuditJson.get("ec").getAsInt()==200){
        	//如果creativeAuditJson的get("ec")不为空并且==200，说明审核成功，查询审核信息
        	CreativeAuditModelExample creativeExample = new CreativeAuditModelExample();
        	creativeExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE);
        	List<CreativeAuditModel> creativeAuditList = creativeAuditDao.selectByExample(creativeExample);
        	if(creativeAuditList != null && !creativeAuditList.isEmpty()){
        		//如果审核信息不为空，更新状态和时间
        		for(CreativeAuditModel creativeAuditModel : creativeAuditList){
        			creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
        			creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
					creativeAuditDao.updateByPrimaryKeySelective(creativeAuditModel);
        		}
        	}else{
        		CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
        		creativeAuditModel.setId(UUIDGenerator.getUUID());
        		creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);  
        		creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
        		creativeAuditModel.setAdxId(AdxKeyConstant.ADX_MOMO_VALUE);
        		creativeAuditModel.setCreativeId(creativeId);
        		creativeAuditDao.insertSelective(creativeAuditModel);
        	}        	
        }else{
        	throw new IllegalStatusException(AuditErrorConstant.MOMO_CREATIVE_AUDIT_ERROR_REASON + creativeAuditJson.get("em").getAsString());
        }
		
	}

	/**
	 * 同步创意
	 */
	@Override
	@Transactional
	public void synchronizeCreative(String creativeId) throws Exception{
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineResultUrl = adxModel.getAexamineresultUrl();
		//私密key将dspid为一个值"pxene"
		String dspid = AdxKeyConstant.AUDIT_NAME_MOMO;	
		JsonObject jsonCreativeInfo = new JsonObject();
		JsonArray arrCreativeInfo = new JsonArray();
		arrCreativeInfo.add(creativeId);
		jsonCreativeInfo.addProperty("dspid", dspid);
		jsonCreativeInfo.add("crids", arrCreativeInfo);
		String dataStr = "data" + jsonCreativeInfo.toString(); //转换数据格式
		//获取同步结果
		String synchronizeCreativeResult = HttpClientUtil.getInstance().sendHttpPostJson(cexamineResultUrl, dataStr);
		//转换数据格式
		Gson gson = new Gson();
		JsonObject jsonSynchronizeCreative = gson.fromJson(synchronizeCreativeResult, new JsonObject().getClass());
		if("200".equals(jsonSynchronizeCreative.get("ec").getAsString())){
			//如果审核结果返回200
			JsonArray arrayCreative = jsonSynchronizeCreative.get("data").getAsJsonArray();
			for(Object object : arrayCreative){
				JsonObject creativeObject = gson.fromJson(object.toString(), new JsonObject().getClass());
				//创意数据表中查询当前创意当前媒体的审核数据
				CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
				creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE);
				CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
				if(creativeObject.get("status").getAsInt()==1){
					//待审核
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
				}else if(creativeObject.get("status").getAsInt()==2){
					// 审核通过
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
				}else if(creativeObject.get("status").getAsInt()==3){
					//审核未通过
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
				}
				if(!StringUtils.isEmpty(creativeObject.get("reason").toString())){
					//如果创意审核信息不为空，则记录审核信息
					creativeAuditModel.setMessage(creativeObject.get("reason").toString());
				}
				//更新创意审核表
				creativeAuditDao.updateByExample(creativeAuditModel, creativeAuditExample);
			}
		}else{
			throw new IllegalStatusException(AuditErrorConstant.MOMO_CREATIVE_SYCHRONIZE_ERROR_REASON + jsonSynchronizeCreative.get("em").getAsString());
		}
	}
	
	/**
	 * 获取图片信息
	 * @param imageId 图片id
	 * @return
	 * @throws Exception
	 */
	private JsonObject getImageInfo(String imageId) throws Exception {
		//通过图片id查询图片信息
		ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
		//将图片信息放入到JsonObject对象中
        JsonObject obj = new JsonObject();
        obj.addProperty("url", "http://www.immomo.com/static/w5/img/website/map.jpg");
        obj.addProperty("width", imageModel.getWidth());
        obj.addProperty("height", imageModel.getHeight());
        return obj;
	}
	
	/**
	 * 获取广告主对应的行业ID
	 * @param industryId 行业id
	 * @return
	 * @throws Exception
	 */
	private String getIndustry(Integer industryId) throws Exception {
		//查询行业adxes信息
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE)
			.andIndustryIdEqualTo(industryId);
		List<IndustryAdxModel> adxes = industryAdxDao.selectByExample(example);
		String code = "";
		//如果adxes信息不为空，获取code
		if (adxes != null && !adxes.isEmpty()) {
			IndustryAdxModel model = adxes.get(0);
			code = model.getIndustryCode();
		}
		return code;
	}

}
