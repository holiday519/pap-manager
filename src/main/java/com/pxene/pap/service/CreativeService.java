package com.pxene.pap.service;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.common.JedisUtils;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.ImageCreativeBean;
import com.pxene.pap.domain.beans.InfoflowCreativeBean;
import com.pxene.pap.domain.beans.MaterialListBean;
import com.pxene.pap.domain.beans.MaterialListBean.App;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.beans.VideoCreativeBean;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppTmplModel;
import com.pxene.pap.domain.models.AppTmplModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.VideoMaterialModel;
import com.pxene.pap.domain.models.VideoModel;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.AppTmplDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeAuditDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageMaterialDao;
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.InfoflowMaterialDao;
import com.pxene.pap.repository.basic.VideoDao;
import com.pxene.pap.repository.basic.VideoMaterialDao;
import com.pxene.pap.repository.basic.VideoTmplDao;

@Service
public class CreativeService extends BaseService {
	
	private final String UPLOAD;
	
	@Autowired
	public CreativeService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		UPLOAD = env.getProperty("pap.fileserver.upload.dir");
	}
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private InfoflowMaterialDao infoflowDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private AuditCreativeBaiduService auditCreativeBaiduService;
	
	@Autowired
	private AuditCreativeAdviewService auditCreativeAdviewService;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private ImageTmplDao imageTmplDao;
	
	@Autowired
	private VideoTmplDao videoTmplDao;
	
	@Autowired
	private AppTmplDao appTmplDao;
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	
	@Autowired
	private VideoMaterialDao videoeMaterialDao;
	
	@Autowired
	private InfoflowMaterialDao infoMaterialDao;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeBean bean) throws Exception {
		String type = bean.getType();
		CreativeModel creativeModel = null;
		
		// 创建创意的时候，要根据模板判断上传的创意是否符合模板要求，不符合要抛出异常
		
		// 图片
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageCreativeBean iBean = (ImageCreativeBean)bean;
			iBean.setId(UUID.randomUUID().toString());
			String materialId = UUID.randomUUID().toString();
			iBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(iBean, CreativeModel.class);
			ImageMaterialModel imageMaterialModel = modelMapper.map(iBean, ImageMaterialModel.class);
			imageMaterialModel.setId(materialId);
			imageMaterialDao.insert(imageMaterialModel);
		}
		// 视频
		if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			VideoCreativeBean vBean = (VideoCreativeBean)bean;
			vBean.setId(UUID.randomUUID().toString());
			String materialId = UUID.randomUUID().toString();
			vBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(vBean, CreativeModel.class);
			VideoMaterialModel videoMaterialModel = modelMapper.map(vBean, VideoMaterialModel.class);
			videoMaterialModel.setId(materialId);
			videoeMaterialDao.insert(videoMaterialModel);
		}
		// 信息流
		if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowCreativeBean ifBean = (InfoflowCreativeBean)bean;
			ifBean.setId(UUID.randomUUID().toString());
			String materialId = UUID.randomUUID().toString();
			ifBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(ifBean, CreativeModel.class);
			InfoflowMaterialModel infoflowModel = modelMapper.map(ifBean, InfoflowMaterialModel.class);
			infoflowModel.setId(materialId);
			infoMaterialDao.insert(infoflowModel);
		}
		
		creativeDao.insertSelective(creativeModel);
	}
	
	/**
	 * 删除图片服务器上的图片素材
	 * @param id
	 * @throws Exception
	 */
	@Transactional
	public void deleteImageMaterialById(String id) throws Exception{
		if (!StringUtils.isEmpty(id)) {
			ImageModel imageModel = imageDao.selectByPrimaryKey(id);
			if (imageModel != null) {
				String path = UPLOAD + imageModel.getPath();
				// 删除图片服务器上的素材
				org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
			}
		}
	}
	/**
	 * 删除图片服务器上的视频素材
	 * @param id
	 * @throws Exception
	 */
	@Transactional
	public void deleteVideoMaterialById(String id) throws Exception{
		if (!StringUtils.isEmpty(id)) {
			VideoModel videoModel = videoDao.selectByPrimaryKey(id);
			if (videoModel != null) {
				String path = UPLOAD + videoModel.getPath();
				// 删除图片服务器上的素材
				org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
			}
		}
	}
	
	/**
	 *  删除创意——素材关联关系
	 * @param creativeId
	 * @param mapId
	 * @throws Exception
	 */
//	@Transactional
//	public void  deleteCreativeMaterial(String creativeId, String mapId) throws Exception {
//		CreativeMaterialModelExample example = new CreativeMaterialModelExample();
//		example.createCriteria().andCreativeIdEqualTo(creativeId).andMaterialIdEqualTo(mapId);
//		creativeMaterialDao.deleteByExample(example);
//	}
	
	/**
	 * 删除创意
	 * @param creativeId
	 * @throws Exception
	 */
	@Transactional
	public void deleteCreative(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB == null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new ResourceNotFoundException();
		}
		
		String campaignId = creativeInDB.getCampaignId();
		if (!StringUtils.isEmpty(campaignId)) {
			//只有活动是等待中时才可删除创意
			CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
			if (StatusConstant.CAMPAIGN_PROCEED.equals(model.getStatus())) {
				throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_START);
			}
		}
		
		//删除素材以及删除图片服务器上文件逻辑（暂无需执行）
//		List<CreativeMaterialModel> cmList = creativeMaterialDao.selectByExample(cmExample);
//		if (cmList != null && !cmList.isEmpty()) {
//			// 删除各个素材
//			for (CreativeMaterialModel cm : cmList) {
//				String cmId = cm.getMaterialId();//素材ID
//				String creativeType = cm.getCreativeType();//素材类型
//				
//				if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {//图片
//					ImageModel imageModel = imageDao.selectByPrimaryKey(cmId);
//					if (imageModel != null) {
//						deleteImageMaterialById(imageModel.getId());
//					}
//					imageDao.deleteByPrimaryKey(cmId);
//				} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {//视频
//					VideoModel videoModel = videoDao.selectByPrimaryKey(cmId);
//					if (videoModel != null) {
//						deleteVideoMaterialById(videoModel.getId());
//					}
//					videoDao.deleteByPrimaryKey(cmId);
//				} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {//信息流
//					InfoflowModel infoModel = infoflowDao.selectByPrimaryKey(cmId);
//					if (infoModel != null) {
//						String icon = infoModel.getIconId();
//						String image1 = infoModel.getImage1Id();
//						String image2 = infoModel.getImage1Id();
//						String image3 = infoModel.getImage1Id();
//						String image4 = infoModel.getImage1Id();
//						String image5 = infoModel.getImage1Id();
//						deleteImageMaterialById(icon);
//						deleteImageMaterialById(image1);
//						deleteImageMaterialById(image2);
//						deleteImageMaterialById(image3);
//						deleteImageMaterialById(image4);
//						deleteImageMaterialById(image5);
//					}
//					//删除信息流素材表数据
//					infoflowDao.deleteByPrimaryKey(cmId);
//				}
//			}
//		}
		// 删除创意数据
		creativeDao.deleteByPrimaryKey(creativeId);
	}
	
	/**
	 * 批量删除创意
	 * @param creativeIds
	 * @throws Exception
	 */
	@Transactional
	public void deleteCreatives(String[] creativeIds) throws Exception {
		
		List<String> asList = Arrays.asList(creativeIds);
		CreativeModelExample ex = new CreativeModelExample();
		ex.createCriteria().andIdIn(asList);
		
		List<CreativeModel> creativeInDB = creativeDao.selectByExample(ex);
		if (creativeInDB == null || creativeInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (String creativeId : creativeIds) {
			CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
			String campaignId = null;
			if (creativeModel != null) {
				campaignId = creativeModel.getCampaignId();
			}
			if (!StringUtils.isEmpty(campaignId)) {
				//只有活动是等待中时才可删除创意
				CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
				if (StatusConstant.CAMPAIGN_PROCEED.equals(model.getStatus())) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_START);
				}
			}
		}
		// 删除创意数据
		creativeDao.deleteByExample(ex);
	}
	
	/**
	 * 修改创意价格
	 * @param id
	 * @param price
	 * @throws Exception
	 */
	@Transactional
	public void updateCreativePrice(String id, Map<String, String> map) throws Exception{
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(id);
		if (creativeModel == null) {
			throw new ResourceNotFoundException();
		}
		
		// FIXME 为什么要必传action参数？
		/*
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		*/
		
		String price = map.get("price").toString();
		creativeModel.setPrice(Float.parseFloat(price));
		creativeDao.updateByPrimaryKey(creativeModel);
	}
	
	
//	/**
//	 * 批量删除创意下素材
//	 * @param mapIds
//	 * @throws Exception
//	 */
//	@Transactional
//	public void deleteCreativeMaterials(String[] mapIds) throws Exception {
//		for (String mapId : mapIds) {
//			CreativeMaterialModel material = creativeMaterialDao.selectByPrimaryKey(mapId);
//			if (material == null) {
//				continue;
//			}
//			String creativeId = material.getCreativeId();
//			if (!StringUtils.isEmpty(creativeId)) {
//				CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
//				String campaignId = creative.getCampaignId();
//				if (!StringUtils.isEmpty(campaignId)) {
//					//只有活动是等待中时才可删除创意
//					CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
//					if (!StatusConstant.CAMPAIGN_WATING.equals(model.getStatus())) {
//						throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_HAS_START_NOT_DELETE_CREATIVE);
//					}
//				}
//			}
//		}
//		
//		List<String> asList = Arrays.asList(mapIds);
//		CreativeMaterialModelExample ex = new CreativeMaterialModelExample();
//		ex.createCriteria().andIdIn(asList);
//		creativeMaterialDao.deleteByExample(ex);
//	}
//	/**
//	 * 删除创意下素材
//	 * @param creativeId
//	 * @throws Exception
//	 */
//	@Transactional
//	public void deleteCreativeMaterial(String mapId) throws Exception {
//		CreativeMaterialModel material = creativeMaterialDao.selectByPrimaryKey(mapId);
//		String creativeId = material.getCreativeId();
//		if (!StringUtils.isEmpty(creativeId)) {
//			CreativeModel creative = creativeDao.selectByPrimaryKey(creativeId);
//			String campaignId = creative.getCampaignId();
//			if (!StringUtils.isEmpty(campaignId)) {
//				//只有活动是等待中时才可删除创意
//				CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
//				if (!StatusConstant.CAMPAIGN_WATING.equals(model.getStatus())) {
//					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_HAS_START_NOT_DELETE_CREATIVE);
//				}
//			}
//		}
//		
//		creativeMaterialDao.deleteByPrimaryKey(mapId);
//	}

		
	/**
	 * 添加素材
	 * @param tmplId
	 * @param file
	 * @throws Exception
	 */
	public Map<String, String> uploadMaterial(String tmplId, MultipartFile file) throws Exception {
		MediaBean mediaBean = FileUtils.checkFile(file);
		Map<String, String> result = null;
		
		if (mediaBean instanceof ImageBean) {
			ImageBean imageBean = (ImageBean) FileUtils.checkFile(file);
			ImageTmplModel tmplModel = imageTmplDao.selectByPrimaryKey(tmplId);
			if (tmplModel == null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			Integer tmplWidth = tmplModel.getWidth(); //模版宽限制
			Integer tmplHeight = tmplModel.getHeight(); //模版高限制
			Float maxVolume = tmplModel.getMaxVolume(); //模版最大体积限制
			int height = imageBean.getHeight(); //文件高
			int width = imageBean.getWidth(); //文件宽
			Float volume = imageBean.getVolume(); //文件体积限制
			
			if (tmplWidth != width || tmplHeight != height || maxVolume < volume) {
				throw new IllegalArgumentException(PhrasesConstant.TEMPLET_NOT_MAP_SIZE);
			}
			
			result = uploadImage(imageBean, file);
		} else if (mediaBean instanceof VideoBean) {
			VideoBean videoBean = (VideoBean) FileUtils.checkFile(file);
			VideoTmplModel tmplModel = videoTmplDao.selectByPrimaryKey(tmplId);
			if (tmplModel == null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			Integer tmplWidth = tmplModel.getWidth(); //模版宽限制
			Integer tmplHeight = tmplModel.getHeight(); //模版高限制
			Float maxVolume = tmplModel.getMaxVolume(); //模版最大体积限制
			Integer maxTimelength = tmplModel.getMaxTimelength(); //模版最大时长
			int height = videoBean.getHeight(); //文件高
			int width = videoBean.getWidth(); //文件宽
			Float volume = videoBean.getVolume(); //文件体积限制
			int timelength = videoBean.getTimelength(); //文件时长
			
			if (tmplWidth != width || tmplHeight != height || maxVolume < volume || maxTimelength < timelength) {
				throw new IllegalArgumentException(PhrasesConstant.TEMPLET_NOT_MAP_SIZE);
			}
			
			result = uploadVideo(videoBean, file);
		}
		
		return result;
	}
	
	
	/**
	 * 上传图片
	 * @param imageBean
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public Map<String, String> uploadImage(ImageBean imageBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = UPLOAD + "creative/image/";
		String path = FileUtils.uploadFile(dir, id, file);//上传
//		String name = imageBean.getName();
//		String path = imageBean.getPath().replace(upload, "");
		String type = imageBean.getFormat();
		Float volume = imageBean.getVolume();
//		ImageTypeModelExample itExample = new ImageTypeModelExample();
//		itExample.createCriteria().andNameEqualTo(type);
//		//查询typeID
//		List<ImageTypeModel> imageTypes = imageTypeDao.selectByExample(itExample);
//		if (imageTypes == null || imageTypes.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
//		String typeId = null;
//		for (ImageTypeModel mol : imageTypes) {
//			typeId = mol.getId();
//		}
		//查询尺寸ID
		int height = imageBean.getHeight();
		int width = imageBean.getWidth();
//		String sizeId = getImageSizeId(width, height);
		//添加图片信息
		ImageModel model = new ImageModel();
		model.setId(id);
//		model.setName(name);
		model.setPath(path.replace(UPLOAD, ""));
		model.setFormat(type);
		model.setVolume(Double.parseDouble(String.valueOf(volume)));
		model.setWidth(width);
		model.setHeight(height);
//		model.setTypeId(typeId);
//		model.setSizeId(sizeId);
		try {
			imageDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(UPLOAD, ""));
		return map;
	}
	
	/**
	 * 添加视频
	 * @param videoBean
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> uploadVideo(VideoBean videoBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = UPLOAD + "creative/video/";
		String path = FileUtils.uploadFile(dir, id, file);//上传
//		String name = videoBean.getName();
//		String path = videoBean.getPath().replace(upload, "");
		String type = videoBean.getFormat();
		Float volume = videoBean.getVolume();
//		String size = videoBean.getSize();//视频图片的id
//		int timeLength = videoBean.getTimelength();
//		VideoTypeModelExample videoExample = new VideoTypeModelExample();
//		videoExample.createCriteria().andNameEqualTo(type);
//		//查询typeID
//		List<VideoTypeModel> videoTypes = videoTypeDao.selectByExample(videoExample);
//		if (videoTypes == null || videoTypes.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
//		String typeId = null;
//		for (VideoTypeModel mol : videoTypes) {
//			typeId = mol.getId();
//		}
		//查询尺寸ID
		int height = videoBean.getHeight();
		int width = videoBean.getWidth();
//		String sizeId = getImageSizeId(width, height);
		
		VideoModel model = new VideoModel();
		//添加视频信息
		model.setId(id);
//		model.setName(name);
		model.setPath(path.replace(UPLOAD, ""));
		model.setFormat(type);
		model.setWidth(width);
		model.setHeight(height);
		model.setVolume(Double.parseDouble(String.valueOf(volume)));
//		model.setTypeId(typeId);
//		model.setSizeId(sizeId);
		model.setTimeLength(videoBean.getTimelength());
//		model.setImageId(size);
		try {
			videoDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(UPLOAD, ""));
		return map;
	}
	
//	/**
//	 * 根据长和宽查询尺寸ID
//	 * @param width
//	 * @param height
//	 * @return
//	 */
//	private String getImageSizeId(int width, int height) {
//		SizeModelExample sizeExample = new SizeModelExample();
//		sizeExample.createCriteria().andHeightEqualTo(height).andWidthEqualTo(width);
//		List<SizeModel> sizes = sizeDao.selectByExample(sizeExample);
//		if (sizes==null || sizes.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
//		String id = null;
//		for (SizeModel mol : sizes) {
//			id = mol.getId();
//		}
//		return id;
//	}

	/**
	 * 创意提交第三方审核
	 * @param id
	 * @throws Exception
	 */
	public void auditCreative(String id) throws Exception {
/*		CreativeModelExample mapExample = new CreativeModelExample();
		mapExample.createCriteria().andCampaignIdEqualTo(id);
		List<CreativeModel> mapModels = creativeDao.selectByExample(mapExample);
		if (mapModels==null || mapModels.isEmpty()) {
			throw new ResourceNotFoundException();
		}*/
		
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//审核创意
		for (AdxModel adx : adxs) {
//			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {
//				//百度
//				auditCreativeBaiduService.audit(id);
//			}else if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adx.getId())) {
//				auditCreativeAdviewService.audit(id);
//			}
			CreativeAuditModel model = new CreativeAuditModel();
			model.setStatus(StatusConstant.CREATIVE_AUDIT_WATING);
			model.setId(UUID.randomUUID().toString());
			model.setAuditValue("1");
			model.setCreativeId(id);
			model.setAdxId(adx.getId());
			creativeAuditDao.insertSelective(model);
			
		}
	}

	/**
	 * 同步创意第三方审核结果
	 * @param id
	 * @throws Exception
	 */
	public void synchronize(String id) throws Exception {
		CreativeModel mapModel = creativeDao.selectByPrimaryKey(id);
		if (mapModel == null) {
			throw new ResourceNotFoundException();
		}
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//同步结果
		for (AdxModel adx : adxs) {
			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {
				//百度
//				auditCreativeBaiduService.synchronize(id);
				
			}else if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adx.getId())) {
//				auditCreativeAdviewService.synchronize(id);
			}
			CreativeAuditModelExample ex = new CreativeAuditModelExample();
			ex.createCriteria().andAdxIdEqualTo(adx.getId());
			List<CreativeAuditModel> list = creativeAuditDao.selectByExample(ex);
			if (list ==null || list.isEmpty()) {
				CreativeAuditModel model = new CreativeAuditModel();
				model.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
				model.setId(UUID.randomUUID().toString());
				model.setAuditValue("1");
				model.setCreativeId(id);
				model.setAdxId(adx.getId());
				creativeAuditDao.insertSelective(model);
			} else {
				for (CreativeAuditModel ml : list) {
					ml.setStatus(StatusConstant.CREATIVE_AUDIT_SUCCESS);
					creativeAuditDao.updateByPrimaryKeySelective(ml);
				}
			}
		}
	}
	
	/**
	 * 批量查询创意
	 * @param name
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	public List<BasicDataBean> selectCreatives(String campaignId, String name, String type, Long startDate, Long endDate) throws Exception {
		List<BasicDataBean> result = new ArrayList<BasicDataBean>();
		CreativeModelExample example = new CreativeModelExample();
		
		// 按更新时间进行倒序排序
        example.setOrderByClause("update_time DESC");
		
		if (!StringUtils.isEmpty(name) && StringUtils.isEmpty(campaignId)) {
			example.createCriteria().andNameLike("%" + name + "%");
		} else if (StringUtils.isEmpty(name) && !StringUtils.isEmpty(campaignId)) {
			example.createCriteria().andCampaignIdEqualTo(campaignId);
		} else if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(campaignId)) {
			example.createCriteria().andCampaignIdEqualTo(campaignId).andNameLike("%" + name + "%");
		}
		
		List<CreativeModel> creatives = creativeDao.selectByExample(example);
		if (creatives != null && !creatives.isEmpty()) {
			CreativeBean base = null;
			ImageCreativeBean image = null;
			VideoCreativeBean video = null;
			InfoflowCreativeBean info = null;
			for (CreativeModel creative : creatives) {
				if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
					ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
					ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
					if (imageModel != null) {
						image = new ImageCreativeBean();
						
						image.setId(creative.getId());
						image.setType(type);
						image.setCampaignId(campaignId);
						image.setName(creative.getName());
						
						image.setStatus(getCreativeAuditStatus(creative.getId()));
						image.setPrice(creative.getPrice().floatValue());
						
						image.setImageId(creative.getMaterialId());
						image.setImagePath(imageModel.getPath());
						//查询投放数据
						if (startDate != null && endDate != null) {
							String creativeId = creative.getId();
							List<String> idList = new ArrayList<String>();
							idList.add(creativeId);
							BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
							if (dataBean != null) {
								image.setImpressionAmount(dataBean.getImpressionAmount());
								image.setClickAmount(dataBean.getClickAmount());
								image.setTotalCost(dataBean.getTotalCost());
								image.setJumpAmount(dataBean.getJumpAmount());
								image.setImpressionCost(dataBean.getImpressionCost());
								image.setClickCost(dataBean.getClickCost());
								image.setClickRate(dataBean.getClickRate());
								image.setJumpCost(dataBean.getJumpCost());
							}
						}
						result.add(image);
					}
				} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(creative.getMaterialId());
					String imageId = videoMaterialModel.getImageId();
					
					VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
					if (videoModel != null) {
						video = new VideoCreativeBean();
						video.setId(creative.getId());
						video.setType(type);
						video.setCampaignId(campaignId);
						video.setName(creative.getName());
						video.setStatus(getCreativeAuditStatus(creative.getId()));
						video.setPrice(creative.getPrice().floatValue());
						
						video.setImageId(imageId);
						video.setImagePath(getImagePath(imageId));
						video.setVideoId(creative.getMaterialId());
						video.setVideoPath(videoModel.getPath());
						//查询投放数据
						if (startDate != null && endDate != null) {
							String creativeId = creative.getId();
							List<String> idList = new ArrayList<String>();
							idList.add(creativeId);
							BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
							if (dataBean != null) {
								video.setImpressionAmount(dataBean.getImpressionAmount());
								video.setClickAmount(dataBean.getClickAmount());
								video.setTotalCost(dataBean.getTotalCost());
								video.setJumpAmount(dataBean.getJumpAmount());
								video.setImpressionCost(dataBean.getImpressionCost());
								video.setClickCost(dataBean.getClickCost());
								video.setClickRate(dataBean.getClickRate());
								video.setJumpCost(dataBean.getJumpCost());
							}
						}
						result.add(video);
					}
				} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
					InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
					if (infoflowModel != null) {
						info = new InfoflowCreativeBean();
						info.setId(creative.getId());
						info.setType(type);
						info.setCampaignId(campaignId);
						info.setName(creative.getName());
						info.setStatus(getCreativeAuditStatus(creative.getId()));
						info.setPrice(creative.getPrice().floatValue());
						
						if (!StringUtils.isEmpty(infoflowModel.getIconId())) {
							info.setIconId(infoflowModel.getIconId());
							info.setIconPath(getImagePath(infoflowModel.getIconId()));
						}
						if (!StringUtils.isEmpty(infoflowModel.getImage1Id())) {
							info.setImage1Id(infoflowModel.getImage1Id());
							info.setImage1Path(getImagePath(infoflowModel.getImage1Id()));
						}
						if (!StringUtils.isEmpty(infoflowModel.getImage2Id())) {
							info.setImage2Id(infoflowModel.getImage2Id());
							info.setImage2Path(getImagePath(infoflowModel.getImage2Id()));
						}
						if (!StringUtils.isEmpty(infoflowModel.getImage3Id())) {
							info.setImage3Id(infoflowModel.getImage3Id());
							info.setImage3Path(getImagePath(infoflowModel.getImage3Id()));
						}
						if (!StringUtils.isEmpty(infoflowModel.getImage4Id())) {
							info.setImage4Id(infoflowModel.getImage4Id());
							info.setImage4Path(getImagePath(infoflowModel.getImage4Id()));
						}
						if (!StringUtils.isEmpty(infoflowModel.getImage5Id())) {
							info.setImage5Id(infoflowModel.getImage5Id());
							info.setImage5Path(getImagePath(infoflowModel.getImage5Id()));
						}
						info.setAppStar(infoflowModel.getAppStar());
						//查询投放数据
						if (startDate != null && endDate != null) {
							String creativeId = creative.getId();
							List<String> idList = new ArrayList<String>();
							idList.add(creativeId);
							BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
							if (dataBean != null) {
								info.setImpressionAmount(dataBean.getImpressionAmount());
								info.setClickAmount(dataBean.getClickAmount());
								info.setTotalCost(dataBean.getTotalCost());
								info.setJumpAmount(dataBean.getJumpAmount());
								info.setImpressionCost(dataBean.getImpressionCost());
								info.setClickCost(dataBean.getClickCost());
								info.setClickRate(dataBean.getClickRate());
								info.setJumpCost(dataBean.getJumpCost());
							}
						}
						result.add(info);
					}
				} else {
					base = modelMapper.map(creative, CreativeBean.class);
					//查询投放数据
					if (startDate != null && endDate != null) {
						String creativeId = creative.getId();
						List<String> idList = new ArrayList<String>();
						idList.add(creativeId);
						BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
						if (dataBean != null) {
							base.setImpressionAmount(dataBean.getImpressionAmount());
							base.setClickAmount(dataBean.getClickAmount());
							base.setTotalCost(dataBean.getTotalCost());
							base.setJumpAmount(dataBean.getJumpAmount());
							base.setImpressionCost(dataBean.getImpressionCost());
							base.setClickCost(dataBean.getClickCost());
							base.setClickRate(dataBean.getClickRate());
							base.setJumpCost(dataBean.getJumpCost());
						}
					}
					base.setStatus(getCreativeAuditStatus(creative.getId()));
					result.add(base);
				}
			}
		}
		return result;
	}
	
	/**
	 * 查询单个创意
	 * @param id
	 * @return
	 */
	public BasicDataBean getCreative(String id, Long startDate, Long endDate) throws Exception {
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		if (creative == null) {
			throw new ResourceNotFoundException();
		}
		String campaignId = creative.getCampaignId();
		String type = creative.getType();
		BasicDataBean bean = new BasicDataBean();
		CreativeBean base = null;
		ImageCreativeBean image = null;
		VideoCreativeBean video = null;
		InfoflowCreativeBean info = null;
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
			if (imageModel != null) {
				image = new ImageCreativeBean();
				
				image.setId(creative.getId());
				image.setType(type);
				image.setCampaignId(campaignId);
				image.setName(creative.getName());
				
				image.setStatus(getCreativeAuditStatus(creative.getId()));
				image.setPrice(creative.getPrice().floatValue());
				
				image.setImageId(creative.getMaterialId());
				image.setImagePath(imageModel.getPath());
				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						image.setImpressionAmount(dataBean.getImpressionAmount());
						image.setClickAmount(dataBean.getClickAmount());
						image.setTotalCost(dataBean.getTotalCost());
						image.setJumpAmount(dataBean.getJumpAmount());
						image.setImpressionCost(dataBean.getImpressionCost());
						image.setClickCost(dataBean.getClickCost());
						image.setClickRate(dataBean.getClickRate());
						image.setJumpCost(dataBean.getJumpCost());
					}
				}
				bean = image;
			}
		} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			String imageId = videoMaterialModel.getImageId();
			
			VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
			if (videoModel != null) {
				video = new VideoCreativeBean();
				video.setId(creative.getId());
				video.setType(type);
				video.setCampaignId(campaignId);
				video.setName(creative.getName());
				video.setStatus(getCreativeAuditStatus(creative.getId()));
				video.setPrice(creative.getPrice().floatValue());
				
				video.setImageId(imageId);
				video.setImagePath(getImagePath(imageId));
				video.setVideoId(creative.getMaterialId());
				video.setVideoPath(videoModel.getPath());
				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						video.setImpressionAmount(dataBean.getImpressionAmount());
						video.setClickAmount(dataBean.getClickAmount());
						video.setTotalCost(dataBean.getTotalCost());
						video.setJumpAmount(dataBean.getJumpAmount());
						video.setImpressionCost(dataBean.getImpressionCost());
						video.setClickCost(dataBean.getClickCost());
						video.setClickRate(dataBean.getClickRate());
						video.setJumpCost(dataBean.getJumpCost());
					}
				}
				bean = video;
			}
		} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
			if (infoflowModel != null) {
				info = new InfoflowCreativeBean();
				info.setId(creative.getId());
				info.setType(type);
				info.setCampaignId(campaignId);
				info.setName(creative.getName());
				info.setStatus(getCreativeAuditStatus(creative.getId()));
				info.setPrice(creative.getPrice().floatValue());
				
				if (!StringUtils.isEmpty(infoflowModel.getIconId())) {
					info.setIconId(infoflowModel.getIconId());
					info.setIconPath(getImagePath(infoflowModel.getIconId()));
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage1Id())) {
					info.setImage1Id(infoflowModel.getImage1Id());
					info.setImage1Path(getImagePath(infoflowModel.getImage1Id()));
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage2Id())) {
					info.setImage2Id(infoflowModel.getImage2Id());
					info.setImage2Path(getImagePath(infoflowModel.getImage2Id()));
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage3Id())) {
					info.setImage3Id(infoflowModel.getImage3Id());
					info.setImage3Path(getImagePath(infoflowModel.getImage3Id()));
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage4Id())) {
					info.setImage4Id(infoflowModel.getImage4Id());
					info.setImage4Path(getImagePath(infoflowModel.getImage4Id()));
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage5Id())) {
					info.setImage5Id(infoflowModel.getImage5Id());
					info.setImage5Path(getImagePath(infoflowModel.getImage5Id()));
				}
				info.setAppStar(infoflowModel.getAppStar());
				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						info.setImpressionAmount(dataBean.getImpressionAmount());
						info.setClickAmount(dataBean.getClickAmount());
						info.setTotalCost(dataBean.getTotalCost());
						info.setJumpAmount(dataBean.getJumpAmount());
						info.setImpressionCost(dataBean.getImpressionCost());
						info.setClickCost(dataBean.getClickCost());
						info.setClickRate(dataBean.getClickRate());
						info.setJumpCost(dataBean.getJumpCost());
					}
				}
				bean = info;
			}
		} else {
			base = modelMapper.map(creative, CreativeBean.class);
			//查询投放数据
			if (startDate != null && endDate != null) {
				String creativeId = creative.getId();
				List<String> idList = new ArrayList<String>();
				idList.add(creativeId);
				BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
				if (dataBean != null) {
					base.setImpressionAmount(dataBean.getImpressionAmount());
					base.setClickAmount(dataBean.getClickAmount());
					base.setTotalCost(dataBean.getTotalCost());
					base.setJumpAmount(dataBean.getJumpAmount());
					base.setImpressionCost(dataBean.getImpressionCost());
					base.setClickCost(dataBean.getClickCost());
					base.setClickRate(dataBean.getClickRate());
					base.setJumpCost(dataBean.getJumpCost());
				}
			}
			base.setStatus(getCreativeAuditStatus(creative.getId()));
			bean = base;
		}
		return bean;
	}
	
	/**
	 * 列出所有素材
	 * @param name
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<MaterialListBean> selectCreativeMaterials(String campaignId, long startDate, long endDate) throws Exception {
		List<MaterialListBean> result = new ArrayList<MaterialListBean>();
		
		CreativeModelExample cmExample = new CreativeModelExample();
		cmExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> cmList = creativeDao.selectByExample(cmExample);
		if (cmList == null || cmList.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		MaterialListBean bean;
		for (CreativeModel cmModel : cmList) {
			bean = new MaterialListBean();
			String mapId = cmModel.getId();
			bean.setMapId(mapId);
			String creativeType = cmModel.getType();
			String tmplId = cmModel.getTmplId();
			String materialId = cmModel.getMaterialId();
			bean.setPrice(cmModel.getPrice().floatValue());
			bean.setId(materialId);
			bean.setType(creativeType);
			//根据素材类型不同，获取不同的素材路径
			if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
				ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(materialId);
				ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
				if (imageModel != null) {
					String path = imageModel.getPath();
					bean.setPath(path);
				}
			} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
				VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(materialId);
				VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
				if (videoModel != null) {
					String path = videoModel.getPath();
					bean.setPath(path);
				}
			} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
				InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(materialId);
				if (!StringUtils.isEmpty(infoflowModel.getIconId())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getIconId());
					if (imageModel != null) {
						String iconPath = imageModel.getPath();
						bean.setIconPath(iconPath);
					}
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage1Id())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage1Id());
					if (imageModel != null) {
						String image1Path = imageModel.getPath();
						bean.setImage1Path(image1Path);
					}
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage2Id())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage2Id());
					if (imageModel != null) {
						String image2Path = imageModel.getPath();
						bean.setImage2Path(image2Path);
					}
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage3Id())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage3Id());
					if (imageModel != null) {
						String image3Path = imageModel.getPath();
						bean.setImage3Path(image3Path);
					}
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage4Id())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage4Id());
					if (imageModel != null) {
						String image4Path = imageModel.getPath();
						bean.setImage4Path(image4Path);
					}
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage5Id())) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage5Id());
					if (imageModel != null) {
						String image5Path = imageModel.getPath();
						bean.setImage5Path(image5Path);
					}
				}
			}
			
			
			//查询app
			List<AppModel> appModels = getAppForMaterial(tmplId);
			List<App> appList = new ArrayList<MaterialListBean.App>();
			App app = null;
			if (appModels!=null && !appModels.isEmpty()) {
				for (AppModel appModel :appModels) {
					app = new App();
					app.setAppId(appModel.getAppId());
					app.setAppname(appModel.getAppName());
					app.setPkgName(appModel.getPkgName());
					app.setId(appModel.getId());
					appList.add(app);
				}
			}
			if (!appList.isEmpty()) {
				App[] apps = new App[appList.size()];
				for (int i=0;i< appList.size();i++) {
					apps[i] = appList.get(i);
				}
				bean.setApps(apps);
			}
			
			//查询素材名称
			String materialName = getMaterialName(materialId, creativeType);
			bean.setName(materialName);
			List<String> list = new ArrayList<String>();
			list.add(bean.getId());
			//查询点击、展现等数据
//			CreativeDataBean dataBean = creativeAllDataService.listCreativeData(list, startDate, endDate);
//			Long impressionAmount = dataBean.getImpressionAmount();
//			Long clickAmount = dataBean.getClickAmount();
//			Float cost = dataBean.getCost();
//			Long jumpAmount = dataBean.getJumpAmount();
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 根据图片素材id查询图片路径
	 * @param imageId
	 * @return
	 */
	private String getImagePath(String imageId) {
		String path = null;
		if (!StringUtils.isEmpty(imageId)) {
			ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
			if (imageModel != null) {
				path = imageModel.getPath();
			}
		}
		return path;
	}
	
	/**
	 * 创意审核状态
	 * @param creativeId
	 * @return
	 */
	public String getCreativeAuditStatus(String creativeId) {
		CreativeAuditModelExample ex = new CreativeAuditModelExample();
		ex.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(ex);
		String status = StatusConstant.CREATIVE_AUDIT_NOCHECK;
		boolean successFlag = false;
		for (CreativeAuditModel model : list) {
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(model.getStatus())) {
				status = StatusConstant.CREATIVE_AUDIT_SUCCESS;
				successFlag  = true;
				break;
			}
		}
		if (!successFlag) {
			boolean watingFlag = false;
			for (CreativeAuditModel model : list) {
				if (StatusConstant.CREATIVE_AUDIT_WATING.equals(model.getStatus())) {
					status = StatusConstant.CREATIVE_AUDIT_WATING;
					watingFlag = true;
					break;
				}
			}
			if (!watingFlag) {
				for (CreativeAuditModel model : list) {
					if (StatusConstant.CREATIVE_AUDIT_FAILURE.equals(model.getStatus())) {
						status = StatusConstant.CREATIVE_AUDIT_FAILURE;
						break;
					}
				}
			}
		}
		
		return status;
	}
	/**
	 * 获取App信息
	 * @param tmplId
	 * @return 
	 * @throws Exception
	 */
	private List<AppModel> getAppForMaterial(String tmplId) throws Exception {
		AppTmplModelExample atExample = new AppTmplModelExample();
		atExample.createCriteria().andTmplIdEqualTo(tmplId);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(atExample);
		List<String> appIdList = new ArrayList<String>();
		if (appTmpls != null && !appTmpls.isEmpty()) {
			for (AppTmplModel appTmpl : appTmpls) {
				String appId = appTmpl.getAppId();
				if (!appIdList.contains(appId)) {
					appIdList.add(appId);
				}
			}
		}
		List<AppModel> appModels = null;
		if (!appIdList.isEmpty()) {
			AppModelExample example = new AppModelExample();
			example.createCriteria().andIdIn(appIdList);
			appModels = appDao.selectByExample(example);
		}
		
		return appModels;
	}
	
	/**
	 * 根据素材Id和素材type查询素材名称
	 * @param materialId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private String getMaterialName(String materialId, String type) throws Exception {
		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(materialId);
			ImageModel model = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
			if (model != null) {
//				String sizeId = model.getSizeId();
//				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
//				if (sizeModel != null) {
					Integer width = model.getWidth();
					Integer height = model.getHeight();
					return width + "x" + height;
//				}
			}
		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			
			VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(materialId);
			VideoModel model = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
			if (model != null) {
//				String sizeId = model.getSizeId();
//				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
//				if (sizeModel != null) {
					Integer width = model.getWidth();
					Integer height = model.getHeight();
					return width + "x" + height;
//				}
			}
		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			InfoflowMaterialModel model = infoflowDao.selectByPrimaryKey(materialId);
			if (model != null) {
				return model.getTitle();
			}
		}
		
		return null;
	}
	
	/**
	 * 查询创意投放数据
	 * @param creativeIds
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public BasicDataBean getCreativeDatas(List<String> creativeIds, Long startDate, Long endDate) throws Exception {
		BasicDataBean basicData = new BasicDataBean();
		//将属性值变成0
		FormatBeanParams(basicData);
		DateTime begin = new DateTime(startDate);
    	DateTime end = new DateTime(endDate);
    	if (end.toString("yyyy-MM-dd").equals(begin.toString("yyyy-MM-dd"))) {
    		//开始时间结束时间相等时
    		if (begin.toString("HH").equals("00") && end.toString("HH").equals("23")
					&& !begin.toString("yyyy-MM-dd").equals(new DateTime().toString("yyyy-MM-dd"))) {
    			//查看是不是全天(如果是全天，查询天文件；但是时间不可以是今天，因为当天数据还未生成天文件)
				List<String> days = new ArrayList<String>();
				days.add(begin.toString("yyyyMMdd"));
				getDatafromDayTable(creativeIds, days, basicData);
			} else {
				//如果是今天就要查询小时数据
				getDatafromHourTable(creativeIds, begin.toDate(), end.toDate(), basicData);
			}
    	} else {
			String[] days = DateUtils.getDaysBetween(begin.toDate(), end.toDate());
			List<String> daysList = new ArrayList<String>(Arrays.asList(days));
			if (!begin.toString("HH").equals("00") || begin.toString("yyyy-MM-dd").equals(new DateTime().toString("yyyy-MM-dd"))) {
				Date bigHourOfDay = DateUtils.getBigHourOfDay(begin.toDate());
				getDatafromHourTable(creativeIds, begin.toDate(), bigHourOfDay, basicData);
				if (daysList != null && daysList.size() > 0) {
					for (int i = 0; i < daysList.size(); i++) {
						if (daysList.get(i).equals(begin.toString("yyyyMMdd"))) {
							daysList.remove(i);
						}
					}
				}
			}
			if (!end.toString("HH").equals("23") || end.toString("yyyy-MM-dd").equals(new DateTime().toString("yyyy-MM-dd"))) {
				Date smallHourOfDay = DateUtils.getSmallHourOfDay(end.toDate());
				getDatafromHourTable(creativeIds, smallHourOfDay, end.toDate(), basicData);
				if (daysList != null && daysList.size() > 0) {
					for (int i = 0; i < daysList.size(); i++) {
						if (daysList.get(i).equals(end.toString("yyyyMMdd"))) {
							daysList.remove(i);
						}
					}
				}
			}
			getDatafromDayTable(creativeIds, daysList, basicData);
    	}
    	
    	FormatBeanRate(basicData);
		return basicData;
	}
	
	/**
	 * 取天数据
	 * @param creativeIds
	 * @param daysList
	 * @param bean
	 * @throws Exception
	 */
	private void getDatafromDayTable(List<String> creativeIds, List<String> daysList, BasicDataBean bean) throws Exception {
		for (String creativeId : creativeIds) {
			Map<String, String> map = JedisUtils.hget("creativeDataDay_" + creativeId);//获取map集合
			Set<String> hkeys = JedisUtils.hkeys("creativeDataDay_" + creativeId);//获取所有key
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					//必须要符合“日期”+“@”才是创意的数据
					for (String day : daysList) {
						if (hkey.indexOf(day + "@") > -1) {
							String value = map.get(hkey);
							//根据不同的值来整合不同属性值
							if (!StringUtils.isEmpty(value)) {
								if (hkey.indexOf("@m") > 0) {// 展现
									bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@c") > 0) {// 点击
									bean.setClickAmount(bean.getClickAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@j") > 0) {// 二跳
									bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@e") > 0) {// 花费
									bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(value));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 取小时数据
	 * @param creativeIds
	 * @param startDate
	 * @param endDate
	 * @param bean
	 * @throws Exception
	 */
	private void getDatafromHourTable(List<String> creativeIds, Date startDate, Date endDate, BasicDataBean bean) throws Exception {
		String[] hours = DateUtils.getHoursBetween(startDate, endDate);
		String day = new DateTime(startDate).toString("yyyyMMdd");
		for (String creativeId : creativeIds) {
			Map<String, String> map = JedisUtils.hget("creativeDataHour_" + creativeId);//获取map集合
			Set<String> hkeys = JedisUtils.hkeys("creativeDataHour_" + creativeId);//获取所有key
			if (hkeys != null && !hkeys.isEmpty()) {
				for (String hkey : hkeys) {
					//必须要符合“日期”+“小时”+“@”才是创意的数据
					for (String hour : hours) {
						if (hkey.indexOf(day + hour + "@") > -1) {
							String value = map.get(hkey);
							//根据不同的值来整合不同属性值
							if (!StringUtils.isEmpty(value)) {
								if (hkey.indexOf("@m") > 0) {// 展现
									bean.setImpressionAmount(bean.getImpressionAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@c") > 0) {// 点击
									bean.setClickAmount(bean.getClickAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@j") > 0) {// 二跳
									bean.setJumpAmount(bean.getJumpAmount() + Long.parseLong(value));
								} else if (hkey.indexOf("@e") > 0) {// 花费
									bean.setTotalCost(bean.getTotalCost() + Float.parseFloat(value));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 将实例化的bean种属性值变成0
	 * @param bean
	 * @throws Exception
	 */
	private void FormatBeanParams(BasicDataBean bean) throws Exception {
		bean.setImpressionAmount(0L);
		bean.setClickAmount(0L);
		bean.setJumpAmount(0L);
		bean.setTotalCost(0F);
		
		bean.setImpressionCost(0F);
		bean.setClickRate(0F);
		bean.setClickCost(0F);
		bean.setJumpCost(0F);
	}
	
	/**
	 * 格式话“率”
	 * @param bean
	 * @throws Exception
	 */
	private void FormatBeanRate(BasicDataBean bean) throws Exception {
		DecimalFormat format = new DecimalFormat("0.0000");
		if (bean.getClickAmount() > 0) {
	        double percent = (double)bean.getTotalCost() / bean.getClickAmount();
			Float result = Float.parseFloat(format.format(percent));
	        bean.setClickCost(result);
		}
		if (bean.getJumpAmount() > 0) {
			double percent = (double)bean.getTotalCost() / bean.getJumpAmount();
			Float result = Float.parseFloat(format.format(percent));
			bean.setJumpCost(result);
		}
		if (bean.getImpressionAmount() > 0) {
			double percent = (double)bean.getClickAmount() / bean.getImpressionAmount();
	        Float result = Float.parseFloat(format.format(percent));
	        bean.setClickRate(result);
	        
	        percent = (double)bean.getTotalCost() * 1000 / bean.getImpressionAmount();
	        result = Float.parseFloat(format.format(percent));
	        bean.setImpressionCost(result);
		}
	}
}
