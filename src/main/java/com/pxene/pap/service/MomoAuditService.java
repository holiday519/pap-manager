package com.pxene.pap.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.util.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.AuditErrorConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.IndustryAdxModel;
import com.pxene.pap.domain.models.IndustryAdxModelExample;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.exception.ThirdPartyAuditException;

public class MomoAuditService extends AuditService {

	@Override
	@Transactional
	public void auditAdvertiser() {
		
	}

	@Override
	@Transactional
	public void synchronizeAdvertiser() {
		
	}

	@Override
	@Transactional
	public void auditCreative(String creativeId) throws Exception {
//		AdxModel momoAdx = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_MOMO_VALUE);
		JsonObject data = new JsonObject();
		
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
		
		// 创意审核地址
//		String cexamineurl = momoModel.getCexamineUrl();
		
	}

	@Override
	@Transactional
	public void synchronizeCreative(String creativeId) {
		
	}
	
	private JsonObject getImageInfo(String imageId) throws Exception {
		ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
        JsonObject obj = new JsonObject();
        obj.addProperty("url", "http://www.immomo.com/static/w5/img/website/map.jpg");
        obj.addProperty("width", imageModel.getWidth());
        obj.addProperty("height", imageModel.getHeight());
        return obj;
	}
	
	private String getIndustry(Integer industryId) throws Exception {
		IndustryAdxModelExample example = new IndustryAdxModelExample();
		example.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_MOMO_VALUE)
			.andIndustryIdEqualTo(industryId);
		List<IndustryAdxModel> adxes = industryAdxDao.selectByExample(example);
		String code = "";
		if (adxes != null && !adxes.isEmpty()) {
			IndustryAdxModel model = adxes.get(0);
			code = model.getIndustryCode();
		}
		return code;
	}

}
