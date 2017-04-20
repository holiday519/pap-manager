package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.pxene.pap.constant.PhrasesConstant;
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
import com.pxene.pap.exception.ResourceNotFoundException;
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
	 * 审核广告主（向广告主审核表插入数据）
	 */
	@Override
	@Transactional
	public void auditAdvertiser(String advertiserId) throws Exception {
		//查询广告主审核信息
		AdvertiserAuditModelExample advertiserAuditModelExample =new AdvertiserAuditModelExample();
    	advertiserAuditModelExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
    	List<AdvertiserAuditModel> advertiserAuditList = advertiserAuditDao.selectByExample(advertiserAuditModelExample);
    	if(advertiserAuditList == null || advertiserAuditList.isEmpty()){
    		//如果广告主审核信息为空，则向广告主审核表插入数据
    		AdvertiserAuditModel advertiserAudit  = new AdvertiserAuditModel();
    		advertiserAudit.setId(UUIDGenerator.getUUID());
    		advertiserAudit.setAdvertiserId(advertiserId);
    		advertiserAudit.setAuditValue("1");
    		advertiserAudit.setAdxId(AdxKeyConstant.ADX_MOMO_VALUE);
    		advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
    		//向广告主审核表插入数据
    		advertiserAuditDao.insertSelective(advertiserAudit);
    	}
    	
	}

	/**
	 * 同步广告主（更新广告主审核表状态）
	 */
	@Override
	@Transactional
	public void synchronizeAdvertiser(String advertiserId) throws Exception {
		//查询广告主审核信息
		AdvertiserAuditModelExample advertiserAuditModelExample =new AdvertiserAuditModelExample();
    	advertiserAuditModelExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
    	List<AdvertiserAuditModel> advertiserAuditList = advertiserAuditDao.selectByExample(advertiserAuditModelExample);
    	if(advertiserAuditList != null && !advertiserAuditList.isEmpty()){
    		//如果广告主审核信息不为空，则更新广告主审核表状态
    		AdvertiserAuditModel advertiserAudit = advertiserAuditList.get(0);
    		advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);   
    		//更新广告主审核表数据
    		advertiserAuditDao.updateByPrimaryKey(advertiserAudit);
    	}				
		
	}

	/**
	 * 审核创意（提交陌陌审核）
	 */
	@Override
	@Transactional	
	public void auditCreative(String creativeId) throws Exception {
			
		JsonObject data = new JsonObject();
		//判断创意类型是否符合
		CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId); //根据传来的创意id查询创意信息
		if (!StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creative.getType())) {
			//如果创意类型不符合则提示
			throw new ThirdPartyAuditException(AuditErrorConstant.MOMO_CREATIVE_TYPE_NOT_SUPPORT);
		}
		//设置陌陌审核参数--本地创意信息
		JsonObject nativeCreative = new JsonObject();
		InfoflowMaterialModel material = infoflowMaterialDao.selectByPrimaryKey(creative.getMaterialId()); //根据素材id查询信息流素材信息
        nativeCreative.addProperty("title", material.getTitle()); //广告标题
        nativeCreative.addProperty("desc", material.getDescription()); //推广语
        //规定提交审核的图片
        String iconId = material.getIconId();
        nativeCreative.add("logo", getImageInfo(iconId)); //logo
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
			nativeCreative.add("image", images); //图片
			data.addProperty("quality_level", 1); //质量等级，可选字段，投放好友动态的素材设置为 1
		} else {
            puton_to = "NEARBY";
            puton_img = "NO_IMG";
		}
		String campaignId = creative.getCampaignId(); //在创意信息中获取活动id
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId); //查询活动信息
		String landpageId = campaign.getLandpageId(); //在活动信息中获取落地页id
		LandpageModel landpage = landpageDao.selectByPrimaryKey(landpageId); //查询落地页信息
		nativeCreative.addProperty("landingpage_url", landpage.getUrl().replaceAll("&", "%26"));
		puton_type = "LANDING_PAGE";
		nativeCreative.addProperty("native_format", puton_to + "_" + puton_type + "_" + puton_img);
		data.add("native_creative", nativeCreative); //本地审核信息
		
		JsonArray cats = new JsonArray();
		String projectId = campaign.getProjectId(); //在活动信息中获取项目id
		ProjectModel project = projectDao.selectByPrimaryKey(projectId); //查询项目信息
		String advertiserId = project.getAdvertiserId(); //在项目信息中获取广告主id
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId); //查询广告主信息
		cats.add(getIndustry(advertiser.getIndustryId())); //在广告主信息中获取广告主所在的行业信息
		data.add("cat", cats); // 行业类目
		
		String dspid = AdxKeyConstant.AUDIT_NAME_MOMO; //获取dspid
		data.addProperty("dspid", dspid); //DSP ID
		data.addProperty("cid", projectId); //项目 ID
		data.addProperty("adid", campaignId); //广告id
		data.addProperty("crid", creativeId); //创意id
		data.addProperty("advertiser_id", advertiserId); //广告主id
		data.addProperty("advertiser_name", advertiser.getName()); //广告主名称

		long uptime = System.currentTimeMillis() / 1000; //时间转换
		// 提交时间，Unix时间戳，单位秒，数字类型
		data.addProperty("uptime", uptime);
		Date current = new Date();
		String expiry_date = DateUtils.getDayOfChange(current, 120) + " 23:59:59";
		// 素材过期时间，最大支持当前日期往后120天
		data.addProperty("expiry_date", expiry_date);
		// 签名生成
        String sign = GlobalUtil.MD5("appkey" + dspid + "crid" + creativeId + "uptime" + uptime).toUpperCase();
        data.addProperty("sign", sign);
		// 创意审核地址
        AdxModel momoAdx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineurl = momoAdx.getCexamineUrl();
		//提交陌陌审核并
        String creativeAuditResult=HttpClientUtil.getInstance().sendHttpPostForm(cexamineurl, "data=" + data.toString());
        //转换陌陌审核数据格式
        Gson gson = new Gson();
        JsonObject creativeAuditJson = gson.fromJson(creativeAuditResult, new JsonObject().getClass()); 
        if(creativeAuditJson.get("ec")!=null && creativeAuditJson.get("ec").getAsInt()==200){
        	//如果creativeAuditJson的get("ec")不为空并且==200，说明审核成功，查询审核信息-->更新或插入创意审核表
        	CreativeAuditModelExample creativeExample = new CreativeAuditModelExample();
        	creativeExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE);
        	List<CreativeAuditModel> creativeAuditList = creativeAuditDao.selectByExample(creativeExample);
        	if(creativeAuditList != null && !creativeAuditList.isEmpty()){
        		//如果创意审核表信息不为空，更新状态和时间
        		for(CreativeAuditModel creativeAuditModel : creativeAuditList){
        			creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
        			creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
					creativeAuditDao.updateByPrimaryKeySelective(creativeAuditModel);
        		}
        	}else{
        		//如果创意审核表信息为空，添加加入相关信息
        		CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
        		creativeAuditModel.setId(UUIDGenerator.getUUID());
        		creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);  
        		creativeAuditModel.setExpiryDate(new DateTime(current).plusDays(120).toDate());
        		creativeAuditModel.setAdxId(AdxKeyConstant.ADX_MOMO_VALUE);
        		creativeAuditModel.setCreativeId(creativeId);
        		creativeAuditDao.insertSelective(creativeAuditModel);
        	}        	
        }else{
        	//否则提示审核失败的原因
        	throw new IllegalStatusException(AuditErrorConstant.MOMO_CREATIVE_AUDIT_ERROR_REASON + creativeAuditJson.get("em").getAsString());
        }
		
	}

	/**
	 * 同步创意（同步陌陌审核信息）
	 */
	@Override
	@Transactional
	public void synchronizeCreative(String creativeId) throws Exception{
		//根据审核主查询adx信息，获取审核Url
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		String cexamineResultUrl = adxModel.getCexamineResultUrl();
		//私密key将dspid为一个值"pxene"
		String dspid = AdxKeyConstant.AUDIT_NAME_MOMO;	
		//设置同步方法参数
		JsonObject jsonCreativeInfo = new JsonObject();
		JsonArray arrCreativeInfo = new JsonArray();
		arrCreativeInfo.add(creativeId);
		jsonCreativeInfo.addProperty("dspid", dspid);
		jsonCreativeInfo.add("crids", arrCreativeInfo);
		//String dataStr = "data" + jsonCreativeInfo.toString(); //转换数据格式
		//获取同步结果
		//String synchronizeCreativeResult = HttpClientUtil.getInstance().sendHttpPostJson(cexamineResultUrl, dataStr);
		String synchronizeCreativeResult = HttpClientUtil.getInstance().sendHttpPostForm(cexamineResultUrl, "data=" + jsonCreativeInfo.toString());
		//转换数据格式
		Gson gson = new Gson();
		JsonObject jsonSynchronizeCreative = gson.fromJson(synchronizeCreativeResult, new JsonObject().getClass());
		if("200".equals(jsonSynchronizeCreative.get("ec").getAsString())){
			//如果审核结果返回200，说明同步成功，根据同步信息更新创意审核表返回同步结果
			JsonArray arrayCreative = jsonSynchronizeCreative.get("data").getAsJsonArray();
			for(Object object : arrayCreative){
				JsonObject creativeObject = gson.fromJson(object.toString(), new JsonObject().getClass());
				//创意数据表中查询当前创意当前媒体的审核数据
				CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
				creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE);
				CreativeAuditModel creativeAuditModel = new CreativeAuditModel();
				if(creativeObject.get("status").getAsInt() == 1){
					//待审核
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
				}else if(creativeObject.get("status").getAsInt() == 2){
					// 审核通过
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
				}else if(creativeObject.get("status").getAsInt() == 3){
					//审核未通过
					creativeAuditModel.setStatus(StatusConstant.CREATIVE_AUDIT_FAILURE);
				}
				if(creativeObject.get("reason").toString()!=null && !"".equals(creativeObject.get("reason").toString())){
					//如果创意审核信息不为空，则记录审核信息
					creativeAuditModel.setMessage(creativeObject.get("reason").toString());
				}
				//更新创意审核表
				creativeAuditDao.updateByPrimaryKey(creativeAuditModel);
			}
		}else{
			//否则提示同步失败的原因
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
	 * @return 返回广告主对应的code
	 * @throws Exception
	 */
	private String getIndustry(Integer industryId) throws Exception {
		//查询行业adxes信息，根据adx和行业id
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
