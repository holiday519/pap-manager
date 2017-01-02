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
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CreativeAddBean;
import com.pxene.pap.domain.beans.CreativeUpdateBean;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Image;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Image.Add;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Infoflow;
import com.pxene.pap.domain.beans.CreativeUpdateBean.Video;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.domain.model.basic.AdxModelExample;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.ImageModel;
import com.pxene.pap.domain.model.basic.ImageTmplModel;
import com.pxene.pap.domain.model.basic.ImageTypeModel;
import com.pxene.pap.domain.model.basic.ImageTypeModelExample;
import com.pxene.pap.domain.model.basic.InfoflowModel;
import com.pxene.pap.domain.model.basic.SizeModel;
import com.pxene.pap.domain.model.basic.SizeModelExample;
import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.domain.model.basic.VideoTmplModel;
import com.pxene.pap.domain.model.basic.VideoTypeModel;
import com.pxene.pap.domain.model.basic.VideoTypeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.CreativeMaterialDao;
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
	private CreativeDao creativeDao;
	
	@Autowired
	private InfoflowDao infoflowDao;
	
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
	
	@Autowired
	private ImageTmplDao imageTmplDao;
	
	@Autowired
	private VideoTmplDao videoTmplDao;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeAddBean bean) throws Exception {
		String creativeId = UUID.randomUUID().toString();
		bean.setId(creativeId);
		
		com.pxene.pap.domain.beans.CreativeAddBean.Image[] images = bean.getImages();
		com.pxene.pap.domain.beans.CreativeAddBean.Video[] videos = bean.getVideos();
		com.pxene.pap.domain.beans.CreativeAddBean.Infoflow[] infoflows = bean.getInfoflows();
		
		CreativeMaterialModel cmModel = new CreativeMaterialModel();
		//添加图片创意
		if (images != null && images.length > 0) {
			for (com.pxene.pap.domain.beans.CreativeAddBean.Image image : images) {
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
			for (com.pxene.pap.domain.beans.CreativeAddBean.Video video : videos) {
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
		if (infoflows != null && infoflows.length > 0) {
			for (com.pxene.pap.domain.beans.CreativeAddBean.Infoflow info : infoflows) {
				cmModel = new CreativeMaterialModel();
				String infoId = UUID.randomUUID().toString();
				Float price = info.getPrice();
				//添加信息流创意表信息
				InfoflowModel model = modelMapper.map(info, InfoflowModel.class);
				model.setId(infoId);
				infoflowDao.insertSelective(model);
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
		Infoflow infoflows = bean.getInfoflows();
		
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
		if (infoflows != null) {
			com.pxene.pap.domain.beans.CreativeUpdateBean.Infoflow.Add[] add = infoflows.getAdd();
			String[] delete = infoflows.getDelete();
			if (delete != null && delete.length>0) {
				for (String infoId : delete) {
					InfoflowModel infoModel = infoflowDao.selectByPrimaryKey(infoId);
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
					infoflowDao.deleteByPrimaryKey(infoId);
					//删除关联关系
					deleteCreativeMaterial(creativeId, infoId);
				}
			}
			if (add != null && add.length > 0) {
				for (com.pxene.pap.domain.beans.CreativeUpdateBean.Infoflow.Add info : add) {
					cmModel = new CreativeMaterialModel();
					String infoId = UUID.randomUUID().toString();
					Float price = info.getPrice();
					//添加信息流创意表信息
					InfoflowModel model = modelMapper.map(info, InfoflowModel.class);
					model.setId(infoId);
					infoflowDao.insertSelective(model);
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
		
		List<CreativeMaterialModel> cmList = creativeMaterialDao.selectByExample(cmExample);
		if (cmList != null && !cmList.isEmpty()) {
			// 删除各个素材
			for (CreativeMaterialModel cm : cmList) {
				String cmId = cm.getMaterialId();//素材ID
				String creativeType = cm.getCreativeType();//素材类型
				
				if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {//图片
					ImageModel imageModel = imageDao.selectByPrimaryKey(cmId);
					if (imageModel != null) {
						deleteImageMaterialById(imageModel.getId());
					}
					imageDao.deleteByPrimaryKey(cmId);
				} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {//视频
					VideoModel videoModel = videoDao.selectByPrimaryKey(cmId);
					if (videoModel != null) {
						deleteVideoMaterialById(videoModel.getId());
					}
					videoDao.deleteByPrimaryKey(cmId);
				} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {//信息流
					InfoflowModel infoModel = infoflowDao.selectByPrimaryKey(cmId);
					if (infoModel != null) {
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
					}
					//删除信息流素材表数据
					infoflowDao.deleteByPrimaryKey(cmId);
				}
			}
		}
		// 删除关联关系表中数据
		creativeMaterialDao.deleteByExample(cmExample);
		// 删除创意数据
		creativeDao.deleteByPrimaryKey(creativeId);
	}

	/**
	 * 添加素材
	 * @param tmplId
	 * @param file
	 * @throws Exception
	 */
	public String addMaterial(String tmplId, MultipartFile file) throws Exception {
		MediaBean mediaBean = FileUtils.checkFile(file);
		String id = "";
		
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
			
			if (tmplWidth < width || tmplHeight < height || maxVolume < volume) {
				throw new IllegalArgumentException();
			}
			
			id = addImage(imageBean, file);
			
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
			
			if (tmplWidth < width || tmplHeight < height || maxVolume < volume || maxTimelength < timelength) {
				throw new IllegalArgumentException();
			}
			
			id = addVideo(videoBean, file);
			
		}
		
		return id;
	}
	
	
	/**
	 * 上传图片
	 * @param imageBean
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String addImage(ImageBean imageBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/image";
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
		return id;
	}
	
	/**
	 * 添加视频
	 * @param videoBean
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public String addVideo(VideoBean videoBean, MultipartFile file) throws Exception {
		String id = UUID.randomUUID().toString();
		String dir = upload + "creative/video";
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
