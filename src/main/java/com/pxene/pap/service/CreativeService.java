package com.pxene.pap.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.FileUtils;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.model.basic.CreativeMaterialModel;
import com.pxene.pap.domain.model.basic.CreativeMaterialModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.ImageModel;
import com.pxene.pap.domain.model.basic.ImageTypeModel;
import com.pxene.pap.domain.model.basic.ImageTypeModelExample;
import com.pxene.pap.domain.model.basic.SizeModel;
import com.pxene.pap.domain.model.basic.SizeModelExample;
import com.pxene.pap.domain.model.basic.VideoModel;
import com.pxene.pap.domain.model.basic.VideoTypeModel;
import com.pxene.pap.domain.model.basic.VideoTypeModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
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
	private ImageDao imageDao;
	
	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeBean bean) throws Exception {
		
		CreativeModel model = modelMapper.map(bean, CreativeModel.class);
		String creativeId = UUID.randomUUID().toString();
		bean.setId(creativeId);
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据添加
		creativeDao.insertSelective(model);
		BeanUtils.copyProperties(model, bean);
	}
	
	/**
	 * 编辑创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCreative(String creativeId, CreativeBean bean) throws Exception {
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}
		
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB == null || StringUtils.isEmpty(creativeInDB.getId())) {
			throw new NotFoundException();
		}
		
		bean.setId(creativeId);
		CreativeModel creative = modelMapper.map(bean, CreativeModel.class);
		//先删除关联关系
		deleteCreativeMaterial(creativeId);
		//添加创意——素材关联关系
		addCreativeMaterial(bean);
		//创意表数据修改
		creativeDao.updateByPrimaryKeySelective(creative);
	}
	
	/**
	 * 删除创意——素材关联关系
	 * @param creativeId
	 */
	@Transactional
	public void  deleteCreativeMaterial(String creativeId) throws Exception {
		CreativeMaterialModelExample example = new CreativeMaterialModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		creativeMaterialDao.deleteByExample(example);
	}
	
	/**
	 * 添加创意——素材关联关系
	 * @param bean
	 */
	@Transactional
	public void addCreativeMaterial(CreativeBean bean) throws Exception {
		String creativeId = bean.getId();
		List<Float> prices = bean.getPrice();
		List<String> materialIds = bean.getMaterialIds();
		List<String> types = bean.getCreativeType();
		if (materialIds != null && !materialIds.isEmpty()) {
			for (int i = 0; i < materialIds.size(); i++) {
				String mapid = UUID.randomUUID().toString();
				String materialId = materialIds.get(i);
				String type = types.get(i);
				Float price = prices.get(i);
				CreativeMaterialModel cmModel = new CreativeMaterialModel();
				cmModel.setId(mapid);
				cmModel.setCreativeId(creativeId);
				cmModel.setMaterialId(materialId);
				cmModel.setCreativeType(type);
				cmModel.setPrice(new BigDecimal(price));
				creativeMaterialDao.insertSelective(cmModel);
			}
		}
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
			throw new NotFoundException();
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
		String path = imageBean.getPath();
		String type = imageBean.getType();
		Float volume = imageBean.getVolume();
		ImageTypeModelExample itExample = new ImageTypeModelExample();
		itExample.createCriteria().andNameEqualTo(type);
		//查询typeID
		List<ImageTypeModel> imageTypes = imageTypeDao.selectByExample(itExample);
		if (imageTypes == null || imageTypes.isEmpty()) {
			throw new NotFoundException();
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
		String path = videoBean.getPath();
		String type = videoBean.getType();
		Float volume = videoBean.getVolume();
		String size = videoBean.getSize();//视频图片的id
		int timeLength = videoBean.getTimelength();
		VideoTypeModelExample videoExample = new VideoTypeModelExample();
		videoExample.createCriteria().andNameEqualTo(type);
		//查询typeID
		List<VideoTypeModel> videoTypes = videoTypeDao.selectByExample(videoExample);
		if (videoTypes == null || videoTypes.isEmpty()) {
			throw new NotFoundException();
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
			throw new NotFoundException();
		}
		String id = null;
		for (SizeModel mol : sizes) {
			id = mol.getId();
		}
		return id;
	}
}
