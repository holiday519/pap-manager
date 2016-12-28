package com.pxene.pap.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CreativeAddBean;
import com.pxene.pap.domain.beans.CreativeAddBean.BaseImageBean;
import com.pxene.pap.domain.beans.CreativeAddBean.BaseInfoFlowBean;
import com.pxene.pap.domain.beans.CreativeAddBean.BaseVideoBean;
import com.pxene.pap.domain.beans.CreativeUpdateBean;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Image;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Image.Add;
import com.pxene.pap.domain.beans.CreativeUpdateBean.InfoFlow;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Video;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.domain.model.basic.AdxModelExample;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.ImageModel;
import com.pxene.pap.domain.model.basic.ImageTypeModel;
import com.pxene.pap.domain.model.basic.ImageTypeModelExample;
import com.pxene.pap.domain.model.basic.InfoFlowModel;
import com.pxene.pap.domain.model.basic.SizeModel;
import com.pxene.pap.domain.model.basic.SizeModelExample;
import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.domain.model.basic.VideoTypeModel;
import com.pxene.pap.domain.model.basic.VideoTypeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
import com.pxene.pap.repository.basic.ImageDao;
import com.pxene.pap.repository.basic.ImageTypeDao;
import com.pxene.pap.repository.basic.InfoFlowDao;
import com.pxene.pap.repository.basic.SizeDao;
import com.pxene.pap.repository.basic.VideoDao;
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
	private CreativeDao creativeDao;
	
	@Autowired
	private InfoFlowDao infoFlowDao;
	
	@Autowired
	private CreativeMaterialDao creativeMaterialDao;
	
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
	private AuditCreativeBaiduService auditCreativeBaiduService;
	
	@Autowired
	private ImageDao imageDao;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeAddBean bean) throws Exception {
		String creativeId = UUID.randomUUID().toString();
		
		BaseImageBean[] images = bean.getImages();
		BaseVideoBean[] videos = bean.getVideos();
		BaseInfoFlowBean[] infoFlows = bean.getInfoFlows();
		
		CreativeMaterialModel cmModel = new CreativeMaterialModel();
		//添加图片创意
		if (images != null && images.length > 0) {
			for (BaseImageBean image : images) {
				cmModel = new CreativeMaterialModel();
				String imageId = image.getId();
				Float price = image.getPrice();
				cmModel.setCreativeId(creativeId);
				cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_IMAGE);
				cmModel.setPrice(new BigDecimal(price));
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(imageId);
				creativeMaterialDao.insertSelective(cmModel);
			}
		}
		//添加视频创意
		if (videos != null && videos.length > 0) {
			for (BaseVideoBean video : videos) {
				cmModel = new CreativeMaterialModel();
				String videoId = video.getId();
				String imageId = video.getImageId();//图片id
				//如果有图片，将图片Id保存到视频创意表中
				if (!StringUtil.isEmpty(imageId)) {
					VideoModel videoModel = new VideoModel();
					videoModel.setId(videoId);
					videoModel.setImageId(imageId);
					videoDao.updateByPrimaryKeySelective(videoModel);
				}
				Float price = video.getPrice();
				cmModel.setCreativeId(creativeId);
				cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_VIDEO);
				cmModel.setPrice(new BigDecimal(price));
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(videoId);
				creativeMaterialDao.insertSelective(cmModel);
			}
		}
		//添加信息流创意
		if (infoFlows != null && infoFlows.length > 0) {
			for (BaseInfoFlowBean info : infoFlows) {
				cmModel = new CreativeMaterialModel();
				String infoId = UUID.randomUUID().toString();
				Float price = info.getPrice();
				//添加信息流创意表信息
				InfoFlowModel model = modelMapper.map(info, InfoFlowModel.class);
				model.setId(infoId);
				infoFlowDao.insertSelective(model);
				//添加关联关系
				cmModel.setCreativeId(creativeId);
				cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_INFOFLOW);
				cmModel.setId(UUID.randomUUID().toString());
				cmModel.setMaterialId(infoId);
				cmModel.setPrice(new BigDecimal(price));
				creativeMaterialDao.insertSelective(cmModel);
			}
		}
		//添加创意表数据
		CreativeModel model = new CreativeModel();
		model.setId(creativeId);
		model.setCampaignId(bean.getCampaignId());
		model.setName(bean.getName());
		model.setRemark(bean.getRemark());
		creativeDao.insertSelective(model);
	}
	
	/**
	 * 编辑创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCreative(String creativeId, CreativeUpdateBean bean) throws Exception {
		
		Image images = bean.getImages();
		Video videos = bean.getVideos();
		InfoFlow infoFlows = bean.getInfoFlows();
		
		CreativeMaterialModel cmModel = new CreativeMaterialModel();
		if (images != null) {
			Add[] add = images.getAdd();//新添加的图片素材
			String[] delete = images.getDelete();//需要删除的图片素材
			if (delete != null && delete.length>0) {
				for (String imageId : delete) {
					//删除图片服务器上素材
					deleteImageMaterialById(imageId);
					//删除素材表数据
					imageDao.deleteByPrimaryKey(imageId);
					//删除关联关系
					deleteCreativeMaterial(creativeId, imageId);
				}
			}
			//添加新增的图片素材
			if (add != null && add.length>0) {
				for (Add image : add) {
					cmModel = new CreativeMaterialModel();
					String imageId = image.getId();
					Float price = image.getPrice();
					cmModel.setCreativeId(creativeId);
					cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_IMAGE);
					cmModel.setPrice(new BigDecimal(price));
					cmModel.setId(UUID.randomUUID().toString());
					cmModel.setMaterialId(imageId);
					creativeMaterialDao.insertSelective(cmModel);
				}
			}
		}
		if (videos != null) {
			com.pxene.pap.domain.beans.CreativeUpdateBean.Video.Add[] add = videos.getAdd();//新添加的视频素材
			String[] delete = videos.getDelete();//需要删除的视频素材
			if (delete != null && delete.length>0) {
				for (String videoId : delete) {
					//删除图片服务器上素材
					deleteVideoMaterialById(videoId);
					//删除素材表数据
					videoDao.deleteByPrimaryKey(videoId);
					//删除关联关系
					deleteCreativeMaterial(creativeId, videoId);
				}
			}
			//添加新增的图片素材
			if (add != null && add.length>0) {
				for (com.pxene.pap.domain.beans.CreativeUpdateBean.Video.Add video : add) {
					cmModel = new CreativeMaterialModel();
					String videoId = video.getId();
					Float price = video.getPrice();
					cmModel.setCreativeId(creativeId);
					cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_VIDEO);
					cmModel.setPrice(new BigDecimal(price));
					cmModel.setId(UUID.randomUUID().toString());
					cmModel.setMaterialId(videoId);
					creativeMaterialDao.insertSelective(cmModel);
				}
			}
		}
		if (infoFlows != null) {
			com.pxene.pap.domain.beans.CreativeUpdateBean.InfoFlow.Add[] add = infoFlows.getAdd();
			String[] delete = infoFlows.getDelete();
			if (delete != null && delete.length>0) {
				for (String infoId : delete) {
					InfoFlowModel infoModel = infoFlowDao.selectByPrimaryKey(infoId);
					String icon = infoModel.getIconId();
					String image1 = infoModel.getImage1Id();
					String image2 = infoModel.getImage1Id();
					String image3 = infoModel.getImage1Id();
					String image4 = infoModel.getImage1Id();
					String image5 = infoModel.getImage1Id();
					deleteImageMaterialById(icon);
					deleteImageMaterialById(image1);
					deleteImageMaterialById(image2);
					deleteImageMaterialById(image3);
					deleteImageMaterialById(image4);
					deleteImageMaterialById(image5);
					//删除信息流素材表数据
					infoFlowDao.deleteByPrimaryKey(infoId);
					//删除关联关系
					deleteCreativeMaterial(creativeId, infoId);
				}
			}
			if (add != null && add.length > 0) {
				for (com.pxene.pap.domain.beans.CreativeUpdateBean.InfoFlow.Add info : add) {
					cmModel = new CreativeMaterialModel();
					String infoId = UUID.randomUUID().toString();
					Float price = info.getPrice();
					//添加信息流创意表信息
					InfoFlowModel model = modelMapper.map(info, InfoFlowModel.class);
					model.setId(infoId);
					infoFlowDao.insertSelective(model);
					//添加关联关系
					cmModel.setCreativeId(creativeId);
					cmModel.setCreativeType(StatusConstant.CREATIVE_TYPE_INFOFLOW);
					cmModel.setId(UUID.randomUUID().toString());
					cmModel.setMaterialId(infoId);
					cmModel.setPrice(new BigDecimal(price));
					creativeMaterialDao.insertSelective(cmModel);
				}
			}
		}
		CreativeModel model = new CreativeModel();
		model.setId(creativeId);
		model.setCampaignId(bean.getCampaignId());
		model.setName(bean.getName());
		model.setRemark(bean.getRemark());
		creativeDao.updateByPrimaryKeySelective(model);
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
	@Transactional
	public void  deleteCreativeMaterial(String creativeId, String mapId) throws Exception {
		CreativeMaterialModelExample example = new CreativeMaterialModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId).andMaterialIdEqualTo(mapId);
		creativeMaterialDao.deleteByExample(example);
	}
	
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
		
		CreativeMaterialModelExample cmExample = new CreativeMaterialModelExample();
		cmExample.createCriteria().andCreativeIdEqualTo(creativeId);
		// 删除信息流创意；图片和视频不用删除
		List<CreativeMaterialModel> cmList = creativeMaterialDao.selectByExample(cmExample);
		if (cmList != null && !cmList.isEmpty()) {
			for (CreativeMaterialModel cm : cmList) {
				String cmId = cm.getMaterialId();
				infoFlowDao.deleteByPrimaryKey(cmId);
			}
		}
		// 删除关联关系表中数据
		creativeMaterialDao.deleteByExample(cmExample);
		// 删除创意数据
		creativeDao.deleteByPrimaryKey(creativeId);
	}

	/**
	 * 上传图片
	 * @param file
	 * @return
	 */
	@Transactional
	public String addImage(MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/image";
		ImageBean imageBean = (ImageBean) FileUtils.uploadFile(dir, id, file);
		String name = imageBean.getName();
		String path = imageBean.getPath().replace(upload, "");
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
		model.setPath(path);
		model.setVolume(volume);
		model.setTypeId(typeId);
		model.setSizeId(sizeId);
		try {
			imageDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		return id;
	}
	
	/**
	 * 添加视频
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String addVideo(MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/video";
		VideoBean videoBean = (VideoBean) FileUtils.uploadFile(dir, id, file);
		String name = videoBean.getName();
		String path = videoBean.getPath().replace(upload, "");
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
		model.setPath(path);
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
		return id;
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
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(id);
		if (creativeModel == null) {
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
			}else if (AdxKeyConstant.ADX_TANX_VALUE.equals(adx.getId())) {
				
			}
		}
	}

	/**
	 * 同步创意第三方审核结果
	 * @param id
	 * @throws Exception
	 */
	public void synchronize(String id) throws Exception {
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(id);
		if (creativeModel == null) {
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
			}else if (AdxKeyConstant.ADX_TANX_VALUE.equals(adx.getId())) {
				
			}
		}
	}
}
