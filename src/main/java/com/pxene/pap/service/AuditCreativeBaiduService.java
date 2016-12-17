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
import com.pxene.pap.repository.mapper.basic.AdxModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeMaterialModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeImageModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeInfoflowModelMapper;
import com.pxene.pap.repository.mapper.basic.view.CreativeVideoModelMapper;
import com.pxene.pap.repository.mapper.basic.view.ImageSizeTypeModelMapper;

public class AuditCreativeBaiduService {

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
	
	@Autowired
	private AdxModelMapper adxMapper;
	
	public void audit(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeMapper.selectByPrimaryKey(creativeId);
		if (creativeInDB==null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		
		AdxModel adxModel = adxMapper.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
    	String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("dspId") == null || json.get("token") == null) {
			throw new NotFoundException("baidu : 缺少私密key");//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
		
		CreativeMaterialModelExample mapExample = new CreativeMaterialModelExample();
		mapExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeMaterialModel> mapModels = creativeMaterialMapper.selectByExample(mapExample);
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

}
