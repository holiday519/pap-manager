package com.pxene.pap.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CreativeAddBean;
import com.pxene.pap.domain.beans.CreativeAddBean.Image;
import com.pxene.pap.domain.beans.CreativeAddBean.Infoflow;
import com.pxene.pap.domain.beans.CreativeAddBean.Video;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CreativeImageBean;
import com.pxene.pap.domain.beans.CreativeInfoflowBean;
import com.pxene.pap.domain.beans.CreativeVideoBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MaterialListBean;
import com.pxene.pap.domain.beans.MaterialListBean.App;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;
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
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.ImageTypeModel;
import com.pxene.pap.domain.models.ImageTypeModelExample;
import com.pxene.pap.domain.models.InfoflowModel;
import com.pxene.pap.domain.models.SizeModel;
import com.pxene.pap.domain.models.SizeModelExample;
import com.pxene.pap.domain.models.VideoModel;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.domain.models.VideoTypeModel;
import com.pxene.pap.domain.models.VideoTypeModelExample;
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
import com.pxene.pap.repository.basic.ImageTmplDao;
import com.pxene.pap.repository.basic.ImageTypeDao;
import com.pxene.pap.repository.basic.InfoflowDao;
import com.pxene.pap.repository.basic.SizeDao;
import com.pxene.pap.repository.basic.VideoDao;
import com.pxene.pap.repository.basic.VideoTmplDao;
import com.pxene.pap.repository.basic.VideoTypeDao;

@Service
public class CreativeService extends BaseService {
	
	private static String upload;
	
	@Autowired
	public CreativeService(Environment env)
	{
		/**
		 * 获取图片上传路径
		 */
		upload = env.getProperty("pap.fileserver.upload.dir");
	}
	
	@Autowired
	private InfoflowDao infoflowDao;
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private ImageTypeDao imageTypeDao;
	
	@Autowired
	private VideoTypeDao videoTypeDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private SizeDao sizeDao;
	
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
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeAddBean bean) throws Exception {
		String campaignId = bean.getCampaignId();
		
		Image[] images = bean.getImages();
		Video[] videos = bean.getVideos();
		Infoflow[] infoflows = bean.getInfoflows();
		
		CreativeModel cmModel = new CreativeModel();
		//添加图片创意
		if (images != null && images.length > 0) {
			for (Image image : images) {
				cmModel = new CreativeModel();
				String imageId = image.getId();
				String tmplId = image.getTmplId();
				cmModel.setType(StatusConstant.CREATIVE_TYPE_IMAGE);
				cmModel.setTmplId(tmplId);
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(imageId);
				cmModel.setCampaignId(campaignId);
				cmModel.setPrice(new BigDecimal(image.getPrice()));
				creativeDao.insertSelective(cmModel);
			}
		}
		//添加视频创意
		if (videos != null && videos.length > 0) {
			for (Video video : videos) {
				cmModel = new CreativeModel();
				String videoId = video.getId();
				String imageId = video.getImageId();//图片id
				//如果有图片，将图片Id保存到视频创意表中
				if (!StringUtil.isEmpty(imageId)) {
					VideoModel videoModel = new VideoModel();
					videoModel.setId(videoId);
					videoModel.setImageId(imageId);
					videoDao.updateByPrimaryKeySelective(videoModel);
				}
				String tmplId = video.getTmplId();
				cmModel.setType(StatusConstant.CREATIVE_TYPE_VIDEO);
				cmModel.setTmplId(tmplId);
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(videoId);
				cmModel.setCampaignId(campaignId);
				cmModel.setPrice(new BigDecimal(video.getPrice()));
				creativeDao.insertSelective(cmModel);
			}
		}
		//添加信息流创意
		if (infoflows != null && infoflows.length > 0) {
			for (Infoflow info : infoflows) {
				cmModel = new CreativeModel();
				String infoId = UUID.randomUUID().toString();
				String tmplId = info.getTmplId();
				//添加信息流创意表信息
				InfoflowModel model = modelMapper.map(info, InfoflowModel.class);
				model.setId(infoId);
				infoflowDao.insertSelective(model);
				
				cmModel.setType(StatusConstant.CREATIVE_TYPE_INFOFLOW);
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(infoId);
				cmModel.setTmplId(tmplId);
				cmModel.setCampaignId(campaignId);
				cmModel.setPrice(new BigDecimal(info.getPrice()));
				creativeDao.insertSelective(cmModel);
			}
		}
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
				String path = upload + imageModel.getPath();
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
				String path = upload + videoModel.getPath();
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
			if (!StatusConstant.CAMPAIGN_WATING.equals(model.getStatus())) {
				throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_HAS_START_NOT_DELETE_CREATIVE);
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
				if (!StatusConstant.CAMPAIGN_WATING.equals(model.getStatus())) {
					throw new IllegalArgumentException(PhrasesConstant.CAMPAIGN_HAS_START_NOT_DELETE_CREATIVE);
				}
			}
		}
		// 删除创意数据
		creativeDao.deleteByExample(ex);
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
	public Map<String, String> addMaterial(String tmplId, MultipartFile file) throws Exception {
		MediaBean mediaBean = FileUtils.checkFile(file);
		Map<String, String> result = null;
		
		if (mediaBean instanceof ImageBean){
			ImageBean imageBean = (ImageBean) FileUtils.checkFile(file);
			ImageTmplModel tmplModel = imageTmplDao.selectByPrimaryKey(tmplId);
			if (tmplModel==null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			String sizeId = tmplModel.getSizeId();
			SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
			Integer tmplWidth = sizeModel.getWidth();//模版宽限制
			Integer tmplHeight = sizeModel.getHeight();//模版高限制
			Float maxVolume = tmplModel.getMaxVolume();//模版最大体积限制
			int height = imageBean.getHeight();//文件高
			int width = imageBean.getWidth();//文件宽
			Float volume = imageBean.getVolume();//文件体积限制
			
			if (tmplWidth != width || tmplHeight != height || maxVolume < volume) {
				throw new IllegalArgumentException(PhrasesConstant.TEMPLET_NOT_MAP_SIZE);
			}
			
			result = addImage(imageBean, file);
			
		}else if (mediaBean instanceof VideoBean) {
			VideoBean videoBean = (VideoBean) FileUtils.checkFile(file);
			VideoTmplModel tmplModel = videoTmplDao.selectByPrimaryKey(tmplId);
			if (tmplModel==null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			String sizeId = tmplModel.getSizeId();
			SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
			Integer tmplWidth = sizeModel.getWidth();//模版宽限制
			Integer tmplHeight = sizeModel.getHeight();//模版高限制
			Float maxVolume = tmplModel.getMaxVolume();//模版最大体积限制
			Integer maxTimelength = tmplModel.getMaxTimelength();//模版最大时长
			int height = videoBean.getHeight();//文件高
			int width = videoBean.getWidth();//文件宽
			Float volume = videoBean.getVolume();//文件体积限制
			int timelength = videoBean.getTimelength();//文件时长
			
			if (tmplWidth != width || tmplHeight != height || maxVolume < volume || maxTimelength < timelength) {
				throw new IllegalArgumentException(PhrasesConstant.TEMPLET_NOT_MAP_SIZE);
			}
			
			result = addVideo(videoBean, file);
			
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
	public  Map<String, String> addImage(ImageBean imageBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/image/";
		String path = FileUtils.uploadFile(dir, id, file);//上传
		String name = imageBean.getName();
//		String path = imageBean.getPath().replace(upload, "");
		String type = imageBean.getType();
		Float volume = imageBean.getVolume();
		ImageTypeModelExample itExample = new ImageTypeModelExample();
		itExample.createCriteria().andNameEqualTo(type);
		//查询typeID
		List<ImageTypeModel> imageTypes = imageTypeDao.selectByExample(itExample);
		if (imageTypes == null || imageTypes.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		String typeId = null;
		for (ImageTypeModel mol : imageTypes) {
			typeId = mol.getId();
		}
		//查询尺寸ID
		int height = imageBean.getHeight();
		int width = imageBean.getWidth();
		String sizeId = getImageSizeId(width, height);
		//添加图片信息
		ImageModel model = new ImageModel();
		model.setId(id);
		model.setName(name);
		model.setPath(path.replace(upload, ""));
		model.setVolume(volume);
		model.setTypeId(typeId);
		model.setSizeId(sizeId);
		try {
			imageDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(upload, ""));
		return map;
	}
	
	/**
	 * 添加视频
	 * @param videoBean
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> addVideo(VideoBean videoBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/video/";
		String path = FileUtils.uploadFile(dir, id, file);//上传
		String name = videoBean.getName();
//		String path = videoBean.getPath().replace(upload, "");
		String type = videoBean.getType();
		Float volume = videoBean.getVolume();
		String size = videoBean.getSize();//视频图片的id
		int timeLength = videoBean.getTimelength();
		VideoTypeModelExample videoExample = new VideoTypeModelExample();
		videoExample.createCriteria().andNameEqualTo(type);
		//查询typeID
		List<VideoTypeModel> videoTypes = videoTypeDao.selectByExample(videoExample);
		if (videoTypes == null || videoTypes.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		String typeId = null;
		for (VideoTypeModel mol : videoTypes) {
			typeId = mol.getId();
		}
		//查询尺寸ID
		int height = videoBean.getHeight();
		int width = videoBean.getWidth();
		String sizeId = getImageSizeId(width, height);
		
		VideoModel model = new VideoModel();
		//添加视频信息
		model.setId(id);
		model.setName(name);
		model.setPath(path.replace(upload, ""));
		model.setVolume(volume);
		model.setTypeId(typeId);
		model.setSizeId(sizeId);
		model.setTimeLength(timeLength);
		model.setImageId(size);
		try {
			videoDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(upload, ""));
		return map;
	}
	
	/**
	 * 根据长和宽查询尺寸ID
	 * @param width
	 * @param height
	 * @return
	 */
	private String getImageSizeId(int width, int height) {
		SizeModelExample sizeExample = new SizeModelExample();
		sizeExample.createCriteria().andHeightEqualTo(height).andWidthEqualTo(width);
		List<SizeModel> sizes = sizeDao.selectByExample(sizeExample);
		if (sizes==null || sizes.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		String id = null;
		for (SizeModel mol : sizes) {
			id = mol.getId();
		}
		return id;
	}

	/**
	 * 创意提交第三方审核
	 * @param id
	 * @throws Exception
	 */
	public void auditCreative(String id) throws Exception {
		CreativeModelExample mapExample = new CreativeModelExample();
		mapExample.createCriteria().andCampaignIdEqualTo(id);
		List<CreativeModel> mapModels = creativeDao.selectByExample(mapExample);
		if (mapModels==null || mapModels.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//审核创意
		for (AdxModel adx : adxs) {
			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {
				//百度
				auditCreativeBaiduService.audit(id);
			}else if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adx.getId())) {
				auditCreativeAdviewService.audit(id);
			}
		}
	}

	/**
	 * 同步创意第三方审核结果
	 * @param id
	 * @throws Exception
	 */
	public void synchronize(String id) throws Exception {
		CreativeModelExample mapExample = new CreativeModelExample();
		mapExample.createCriteria().andCampaignIdEqualTo(id);
		List<CreativeModel> mapModels = creativeDao.selectByExample(mapExample);
		if (mapModels==null || mapModels.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//同步结果
		for (AdxModel adx : adxs) {
			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {
				//百度
				auditCreativeBaiduService.synchronize(id);
			}else if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adx.getId())) {
				auditCreativeAdviewService.synchronize(id);
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
	public List<BasicDataBean> selectCreatives(String campaignId,String name, String type, Long beginTime, Long endTime) {
		List<BasicDataBean> result = new ArrayList<BasicDataBean>();
		CreativeModelExample example = new CreativeModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(example);
		if (creatives != null && !creatives.isEmpty()) {
			CreativeImageBean image = null;
			CreativeVideoBean video = null;
			CreativeInfoflowBean info = null;
			for (CreativeModel creative : creatives) {
				if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
					ImageModel imageModel = imageDao.selectByPrimaryKey(creative.getMaterialId());
					if (imageModel != null) {
						image = new CreativeImageBean();
						
						image.setId(creative.getId());
						image.setType(type);
						image.setCampaignId(campaignId);
						image.setName(creative.getName());
						
						image.setStatus(getCreativeAuditStatus(creative.getId()));
						image.setPrice(creative.getPrice().floatValue());
						
						image.setImageId(creative.getMaterialId());
						image.setImagePath(imageModel.getPath());
						result.add(image);
					}
				} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					VideoModel videoModel = videoDao.selectByPrimaryKey(creative.getMaterialId());
					if (videoModel != null) {
						video = new CreativeVideoBean();
						video.setId(creative.getId());
						video.setType(type);
						video.setCampaignId(campaignId);
						video.setName(creative.getName());
						video.setStatus(getCreativeAuditStatus(creative.getId()));
						video.setPrice(creative.getPrice().floatValue());
						
						String imageId = videoModel.getImageId();
						video.setImageId(imageId);
						video.setImagePath(getImagePath(imageId));
						video.setVideoId(creative.getMaterialId());
						video.setVideoPath(videoModel.getPath());
						result.add(video);
					}
				} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
					InfoflowModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
					if (infoflowModel != null) {
						info = new CreativeInfoflowBean();
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
						result.add(info);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 列出所有素材
	 * @param name
	 * @param creativeId
	 * @return
	 * @throws Exception
	 */
	public List<MaterialListBean> selectCreativeMaterials(String campaignId, long beginTime, long endTime) throws Exception {
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
				ImageModel imageModel = imageDao.selectByPrimaryKey(materialId);
				if (imageModel != null) {
					String path = imageModel.getPath();
					bean.setPath(path);
				}
			} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
				VideoModel videoModel = videoDao.selectByPrimaryKey(materialId);
				if (videoModel != null) {
					String path = videoModel.getPath();
					bean.setPath(path);
				}
			} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
				InfoflowModel infoflowModel = infoflowDao.selectByPrimaryKey(materialId);
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
//			CreativeDataBean dataBean = creativeAllDataService.listCreativeData(list, beginTime, endTime);
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
	 * 活动审核状态
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
			ImageModel model = imageDao.selectByPrimaryKey(materialId);
			if (model != null) {
				String sizeId = model.getSizeId();
				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
				if (sizeModel != null) {
					Integer width = sizeModel.getWidth();
					Integer height = sizeModel.getHeight();
					return width + "x" + height;
				}
			}
		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			VideoModel model = videoDao.selectByPrimaryKey(materialId);
			if (model != null) {
				String sizeId = model.getSizeId();
				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
				if (sizeModel != null) {
					Integer width = sizeModel.getWidth();
					Integer height = sizeModel.getHeight();
					return width + "x" + height;
				}
			}
		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			InfoflowModel model = infoflowDao.selectByPrimaryKey(materialId);
			if (model != null) {
				return model.getTitle();
			}
		}
		
		return null;
	}
	
}
