package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.domain.model.basic.AdvertiserModel;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.CreativeModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeImageModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeInfoflowModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.CreativeVideoModelExample;
import com.pxene.pap.domain.model.basic.view.CreativeVideoModelWithBLOBs;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModel;
import com.pxene.pap.domain.model.basic.view.ImageSizeTypeModelExample;
import com.pxene.pap.repository.mapper.basic.AdvertiserModelMapper;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeMaterialModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeModelMapper;
import com.pxene.pap.repository.mapper.basic.ProjectModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeImageModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeInfoflowModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeVideoModelMapper;
import com.pxene.pap.repository.mapper.basic.view.ImageSizeTypeModelMapper;

@Service
public class RedisService {

	/**
	 * 检测地址模版
	 */
	private static final String MONITOR_TEMPLATES = "var imgdc{index} = new Image();imgdc{index}.src = '{imonitorurl}';window[new Date()] = imgdc{index};";
	private static final Map<String, Integer[]> TARGET_CODES = new HashMap<String, Integer[]>();// 定向标志位

    static {
        TARGET_CODES.put("geo", new Integer[]{0x0, 0x1, 0x2, 0x3});
        TARGET_CODES.put("network", new Integer[]{0x0, 0x4, 0x8, 0xc});
        TARGET_CODES.put("os", new Integer[]{0x0, 0x10, 0x20, 0x30});
        TARGET_CODES.put("provider", new Integer[]{0x0, 0x40, 0x80, 0xc0});
        TARGET_CODES.put("device", new Integer[]{0x0, 0x100, 0x200, 0x300});
        TARGET_CODES.put("wblist", new Integer[]{0x0, 0x1, 0x2, 0x3});
        TARGET_CODES.put("cat", new Integer[]{0x0, 0x1, 0x2, 0x3});
        TARGET_CODES.put("make", new Integer[]{0x0, 0x400, 0x800, 0xc00});
    }
	
	@Autowired
	private AdvertiserModelMapper advertiserMapper;
	
	@Autowired
	private CampaignModelMapper campaignMapper;
	
	@Autowired
	private ProjectModelMapper projectMapper;
	
	@Autowired
	private CreativeModelMapper creativeMapper;
	
	@Autowired
	private CreativeMaterialModelMapper creativeMaterialMapper;
	
	@Autowired
	private CreativeImageModelMapper creativeImageMapper;
	
	@Autowired
	private CreativeVideoModelMapper creativeVideoMapper;
	
	@Autowired
	private CreativeInfoflowModelMapper creativeInfoflowMapper;
	
	@Autowired
	private ImageSizeTypeModelMapper imageSizeTypeMapper;
	
	@Transactional
	public void writeCreativeInfoToRedis(String campaignId) throws Exception {
		// 查询推广组下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeMapper.selectByExample(creativeExample);
		// 创意id数组
		List<String> creativeIds = new ArrayList<String>();
		//如果推广组下无创意，
		if(creatives == null || creatives.isEmpty()){
			throw new Exception();
		}
		// 将查询出来的创意id放入创意id数组
		for (CreativeModel creative : creatives) {
			String creativeId = creative.getId();
			if (creativeId != null && !creativeId.isEmpty()) {
				creativeIds.add(creativeId);
			}
		}
		// 根据创意id数组查询创意所对应的关联关系表数据
		if (!creativeIds.isEmpty()) {
			CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
			mapExample.createCriteria().andCreativeIdIn(creativeIds);
			List<CreativeMaterialModel> mapModels = creativeMaterialMapper.selectByExample(mapExample);
			if (mapModels != null && !mapModels.isEmpty()) {
				for (CreativeMaterialModel mapModel : mapModels) {
					String mapId = mapModel.getId();
					String creativeType = mapModel.getCreativeType();
					// 图片创意
					if ("1".equals(creativeType)) {
						writeImgCreativeInfoToRedis(mapId);
					// 视频创意
					} else if ("2".equals(creativeType)) {
						writeVideoCreativeInfoToRedis(mapId);
					// 信息流创意
					} else if ("3".equals(creativeType)) {
						writeInfoCreativeInfoToRedis(mapId);
					}
				}
			}
		}
	}
	/**
	 * 图片创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeImgCreativeInfoToRedis(String mapId) throws Exception {
		CreativeImageModelExample imageExaple = new CreativeImageModelExample();
		imageExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeImageModelWithBLOBs> list = creativeImageMapper.selectByExampleWithBLOBs(imageExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeImageModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", model.getType());
			creativeObj.addProperty("ftype", model.getFtype());
			model.getPrice();//此处需要给每个adx填写价格——————————————
			creativeObj.addProperty("ctype", model.getCtype());
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
			}
			creativeObj.addProperty("w", GlobalUtil.parseInt(model.getW(),0));
			creativeObj.addProperty("h", GlobalUtil.parseInt(model.getH(),0));
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.addProperty("imonitorurl", imoUrlStr.toString());
            creativeObj.addProperty("cmonitorurl", cmoUrlStr.toString());
            creativeObj.addProperty("monitorcode", monitorcode);
            model.getSourceUrl();//此处需要修改—————————————————图片地址路径需要加上
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            
			creativeObj.addProperty("exts", "");
			
		}
	}
	
	/**
	 * 视频创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeVideoCreativeInfoToRedis(String mapId) throws Exception {
		CreativeVideoModelExample videoExaple = new CreativeVideoModelExample();
		videoExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeVideoModelWithBLOBs> list = creativeVideoMapper.selectByExampleWithBLOBs(videoExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeVideoModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", model.getType());
			creativeObj.addProperty("ftype", model.getFtype());
			model.getPrice();//此处需要给每个adx填写价格——————————————
			creativeObj.addProperty("ctype", model.getCtype());
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(),""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(),""));
			}
			creativeObj.addProperty("w", GlobalUtil.parseInt(model.getW(),0));
			creativeObj.addProperty("h", GlobalUtil.parseInt(model.getH(),0));
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.addProperty("imonitorurl", imoUrlStr.toString());
            creativeObj.addProperty("cmonitorurl", cmoUrlStr.toString());
            creativeObj.addProperty("monitorcode", monitorcode);
            model.getSourceUrl();//此处需要修改——————————————————图片地址路径需要加上
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            
			creativeObj.addProperty("exts", "");
			
		}
	}
	
	/**
	 * 信息流创意信息写入redis
	 * @param mapId
	 * @throws Exception
	 */
	public void writeInfoCreativeInfoToRedis(String mapId) throws Exception {
		CreativeInfoflowModelExample infoExaple = new CreativeInfoflowModelExample();
		infoExaple.createCriteria().andMapIdEqualTo(mapId);
		List<CreativeInfoflowModelWithBLOBs> list = creativeInfoflowMapper.selectByExampleWithBLOBs(infoExaple);
		if (list == null || list.isEmpty()) {
			throw new Exception();
		}
		for (CreativeInfoflowModelWithBLOBs model : list) {
			JsonObject creativeObj = new JsonObject();
			creativeObj.addProperty("mapid", model.getMapId());
			creativeObj.addProperty("groupid", model.getCampaignId());
			creativeObj.addProperty("type", model.getType());
			creativeObj.addProperty("ftype", model.getFtype());
			model.getPrice();//此处需要给每个adx填写价格——————————————
			creativeObj.addProperty("ctype", model.getCtype());
			if ("2".equals(model.getCtype())) {
				creativeObj.addProperty("bundle", GlobalUtil.parseString(model.getMapId(), ""));
				creativeObj.addProperty("apkname", GlobalUtil.parseString(model.getApkName(), ""));
			}
			String icon = model.getIcon();
			if (icon != null) {
				JsonObject iconJson = getImageJson(icon);
				creativeObj.addProperty(icon, iconJson.toString());
			}
			String image1 = model.getImage1();
			String image2 = model.getImage2();
			String image3 = model.getImage3();
			String image4 = model.getImage4();
			String image5 = model.getImage5();
			int imgs = 0;
			//记录图片id（多张信息流大图）
			String []someImage = new String[5];
			//记录哪一个图片不是NULL（如果只有一张大图时）
			String oneImag = "";
			//逐个判断，如果有多图增加“imgs”，如果一个大图增加“w”“h”“ftype”“sourceurl”,没有则不增加
			if (image1 != null && !"".equals(image1)) {
				imgs += 1;
				oneImag = image1;
				someImage[0] = image1;
			}
			if (image2 != null && !"".equals(image2)) {
				imgs += 1;
				oneImag = image2;
				someImage[1] = image2;
			}
			if (image3 != null && !"".equals(image3)) {
				imgs += 1;
				oneImag = image3;
				someImage[2] = image3;
			}
			if (image4 != null && !"".equals(image4)) {
				imgs += 1;
				oneImag = image4;
				someImage[3] = image4;
			}
			if (image5 != null && !"".equals(image5)) {
				imgs += 1;
				oneImag = image5;
				someImage[4] = image5;
			}
			if (imgs == 1) {
				
				ImageSizeTypeModel ist = selectImages(oneImag);
				creativeObj.addProperty("w", GlobalUtil.parseInt(ist.getWidth(),0));
				creativeObj.addProperty("h", GlobalUtil.parseInt(ist.getHeight(),0));
				creativeObj.addProperty("ftype", ist.getCode());
				creativeObj.addProperty("sourceurl", ist.getPath());//需要拼接图片服务器地址——————————————
			} else if (imgs > 1) {
				JsonArray bigImages = new JsonArray(); 
				for(String str : someImage){
					// sourceurl需要拼接图片服务器地址——————————————
					bigImages.add(selectImages(str).toString());
				}
				creativeObj.addProperty("imgs", bigImages.toString());
			}
			creativeObj.addProperty("curl", GlobalUtil.parseString(model.getCurl(),""));
			creativeObj.addProperty("landingurl", GlobalUtil.parseString(model.getLandingUrl(),""));
			
			String[] tempImonitorUrl = GlobalUtil.parseString(model.getImonitorUrl(), "").split("##");//数据库查询时用"##"连接
            String[] tempCmonitorUrl = GlobalUtil.parseString(model.getCmonitorUrl(), "").split("##");
			JsonArray imoUrlStr = new JsonArray();
			JsonArray cmoUrlStr = new JsonArray();
            String monitorcode = "";
            for (int i = 0; i < tempImonitorUrl.length; i++) {
                imoUrlStr.add(tempImonitorUrl[i]);
                monitorcode += MONITOR_TEMPLATES.replace("{index}", "" + i).replace("{imonitorurl}", tempImonitorUrl[i]);
            }
            for (int j = 0; j < tempCmonitorUrl.length; j++) {
                cmoUrlStr.add(tempCmonitorUrl[j]);
            }
            creativeObj.addProperty("imonitorurl", imoUrlStr.toString());
            creativeObj.addProperty("cmonitorurl", cmoUrlStr.toString());
            creativeObj.addProperty("monitorcode", monitorcode);
            creativeObj.addProperty("cid", GlobalUtil.parseString(model.getProjectId(),""));
            creativeObj.addProperty("title", GlobalUtil.parseString(model.getTitle(),""));
            creativeObj.addProperty("description", GlobalUtil.parseString(model.getDescription(),""));
            creativeObj.addProperty("rating", GlobalUtil.parseString(model.getRating(),""));
            creativeObj.addProperty("ctatext", "");//数据库缺少此字段——————————————
            
			creativeObj.addProperty("exts", "");
			
		}
	}
	
	/**
	 * 获取图片信息json
	 * @param imageId
	 * @return
	 */
	private JsonObject getImageJson(String imageId) throws Exception {
		JsonObject json = new JsonObject();
		ImageSizeTypeModel model = selectImages(imageId);
		json.addProperty("w", GlobalUtil.parseInt(model.getWidth(),0));
		json.addProperty("h", GlobalUtil.parseInt(model.getHeight(),0));
		json.addProperty("ftype", model.getCode());
		json.addProperty("sourceurl", model.getPath());
		return json;
	}
	
	/**
	 * 查询图片的尺寸、类型信息
	 * @param imageId
	 * @return
	 */
	private ImageSizeTypeModel selectImages(String imageId) throws Exception {
		ImageSizeTypeModelExample example = new ImageSizeTypeModelExample();
		example.createCriteria().andIdEqualTo(imageId);
		List<ImageSizeTypeModel> list = imageSizeTypeMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return null;
		}else{
			for (ImageSizeTypeModel model : list) {
				return model;
			}
		}
		return null;
	}
	
	/**
	 * 将活动基本信息写入redis
	 * @param campaignId
	 * @throws Exception
	 */
	public void writeGroupInfoToRedis(String campaignId) throws Exception {
		CampaignModel campaignModel = campaignMapper.selectByPrimaryKey(campaignId);
		String ProjectId = campaignModel.getProjectId();
		ProjectModel projectModel = projectMapper.selectByPrimaryKey(ProjectId);
		String advertiserId = projectModel.getAdvertiserId();
		AdvertiserModel advertiserModel = advertiserMapper.selectByPrimaryKey(advertiserId);
		JsonObject groupInfo = new JsonObject();
		JsonArray catArr = new JsonArray();
		JsonArray adxArr = new JsonArray();
		JsonArray auctiontypeArr = new JsonArray();
		int industryId = Integer.parseInt(advertiserModel.getIndustryId());//此处需要int类型，数据库确实36为uuid——————————————
		catArr.add(industryId);
		groupInfo.addProperty("cat", catArr.toString());
		groupInfo.addProperty("advcat", industryId);
		String adomain = GlobalUtil.parseString(advertiserModel.getSiteUrl(),"");
		groupInfo.addProperty("adomain", adomain.replace("http://www.", "").replace("www.", ""));
		//添加投放adx————————————————————
		//添加“auctiontype”（将广告主审核返回值添加到相应adx）————————————————————
		
		groupInfo.addProperty("redirect", 0);//不重定向创意的curl
		groupInfo.addProperty("effectmonitor", 1);//需要效果监测
		
		groupInfo.addProperty("exts", "");//adx对应需要的值————————————————————————
		
	}
	
	public void writeGroupTargetToRedis(String campaignId) throws Exception {
		JsonObject targetJSON = new JsonObject(); // 项目定向信息
		JsonObject deviceJSON = new JsonObject(); // 设备信息定向
		JsonObject appJSON = new JsonObject(); // 应用程序信息定向
		JsonObject impJSON = new JsonObject(); // 广告位展现形式定向

		
	}
	
	public void writeGroupFrequencyToRedis(String campaignId) throws Exception {
		
		
	}
	
}
