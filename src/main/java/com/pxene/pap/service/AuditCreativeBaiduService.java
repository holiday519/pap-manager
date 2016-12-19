package com.pxene.pap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.exception.NotFoundException;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.view.CreativeImageDao;
import com.pxene.pap.repository.basic.view.CreativeInfoflowDao;
import com.pxene.pap.repository.basic.view.CreativeVideoDao;
import com.pxene.pap.repository.basic.view.ImageSizeTypeDao;


public class AuditCreativeBaiduService {

	@Autowired
	private CreativeDao creativeDao;
	
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
					auditImgCreative(mapId);
				// 视频创意
				} else if ("2".equals(creativeType)) {
					auditVideoCreative(mapId);
				// 信息流创意
				} else if ("3".equals(creativeType)) {
					auditInfoCreative(mapId);
				}
			}
		}
		
	}
	/**
	 * 审核图片创意
	 * @param mapId
	 * @throws Exception
	 */
	public void auditImgCreative(String mapId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		//调用接口----百度第一次审核  与再次审核还用这个字段  但是值在再次审核时更新了
		String cexamineurl = adxModel.getCexamineUrl();
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
    	if (json.get("dspId") == null || json.get("token") == null) {
			throw new NotFoundException("baidu : 缺少私密key");//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
    	//将私密key转成json格式
		String dspId = json.get("dspId").getAsString();
		String token = json.get("token").getAsString();
		
		
	}
	/**
	 * 审核视频创意
	 * @param mapId
	 * @throws Exception
	 */
	public void auditVideoCreative(String mapId) throws Exception {

	}
	/**
	 * 审核信息流创意
	 * @param mapId
	 * @throws Exception
	 */
	public void auditInfoCreative(String mapId) throws Exception {

	}


	public void getAdvAuditValueByMapId (String mapId) {
		
	}
}
