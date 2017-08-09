package com.pxene.pap.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import com.pxene.pap.repository.basic.*;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.FileUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.RedisKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.CreativeBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.ImageCreativeBean;
import com.pxene.pap.domain.beans.InfoflowCreativeBean;
import com.pxene.pap.domain.beans.MediaBean;
import com.pxene.pap.domain.beans.VideoBean;
import com.pxene.pap.domain.beans.VideoCreativeBean;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeAuditModel;
import com.pxene.pap.domain.models.CreativeAuditModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
import com.pxene.pap.domain.models.ImageMaterialModel;
import com.pxene.pap.domain.models.ImageModel;
import com.pxene.pap.domain.models.ImageModelExample;
import com.pxene.pap.domain.models.ImageTmplModel;
import com.pxene.pap.domain.models.InfoflowMaterialModel;
import com.pxene.pap.domain.models.InfoflowTmplModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.domain.models.VideoMaterialModel;
import com.pxene.pap.domain.models.VideoModel;
import com.pxene.pap.domain.models.VideoModelExample;
import com.pxene.pap.domain.models.VideoTmplModel;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.exception.ServerFailureException;

@Service
public class CreativeService extends BaseService {
	
	private static String uploadDir;
	
	private static String uploadMode;
	
	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	@Autowired
	private RedisHelper redisHelper3;
	
	
	@Autowired
	public CreativeService(Environment env)
	{
        uploadMode = env.getProperty("pap.fileserver.mode", "local");
        
        if ("local".equals(uploadMode))
        {
            uploadDir = env.getProperty("pap.fileserver.local.upload.dir");
        }
        else
        {
            uploadDir = env.getProperty("pap.fileserver.remote.upload.dir");
            
            host = env.getProperty("pap.fileserver.remote.host");
            port = Integer.parseInt(env.getProperty("pap.fileserver.remote.port", "22"));
            username = env.getProperty("pap.fileserver.remote.username");
            password = env.getProperty("pap.fileserver.remote.password");
        }
	}
	
	
    @PostConstruct
    public void selectRedis()
    {
        redisHelper3.select("redis.tertiary.");
    }
    
	
	@Autowired
	private CreativeDao creativeDao;
	
	@Autowired
	private InfoflowMaterialDao infoflowDao;
	
	@Autowired
	private InfoflowTmplDao infoflowTmplDao;
	
	@Autowired
	private VideoDao videoDao;
	
	@Autowired
	private AdxDao adxDao;
	
	@Autowired
	private CreativeAuditDao creativeAuditDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private ImageTmplDao imageTmplDao;
	
	@Autowired
	private VideoTmplDao videoTmplDao;	
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private ImageMaterialDao imageMaterialDao;
	
	@Autowired
	private VideoMaterialDao videoMaterialDao;
	
	@Autowired
	private InfoflowMaterialDao infoMaterialDao;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private LaunchService launchService;
	
	@Autowired
	private MomoAuditService momoAuditService;
	
	@Autowired
	private InmobiAuditService inmobiAuditService;
		
	@Autowired
	private AutohomeAuditService autohomeAuditService;
	
	@Autowired
	private BaiduAuditService baiduAuditService;
	
	@Autowired
	private TencentAuditService tencentAuditService;
	
	@Autowired
	private AdviewAuditService adviewAuditService;
	
	@Autowired
	private TmplService tmplService;

	@Autowired
	private AdxCostDao adxCostDao;
	
	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private DataService dataService;	

	/**
	 * 创建创意
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCreative(CreativeBean bean) throws Exception {
		// 新建创意
		String type = bean.getType();
		CreativeModel creativeModel = null;
		// 图片
		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageCreativeBean iBean = (ImageCreativeBean)bean;
			iBean.setId(UUIDGenerator.getUUID());
			String materialId = UUIDGenerator.getUUID();
			iBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(iBean, CreativeModel.class);
			ImageMaterialModel imageMaterialModel = modelMapper.map(iBean, ImageMaterialModel.class);
			imageMaterialModel.setId(materialId);
			imageMaterialDao.insert(imageMaterialModel);
		}
		// 视频
		if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			VideoCreativeBean vBean = (VideoCreativeBean)bean;
			vBean.setId(UUIDGenerator.getUUID());
			String materialId = UUIDGenerator.getUUID();
			vBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(vBean, CreativeModel.class);
			VideoMaterialModel videoMaterialModel = modelMapper.map(vBean, VideoMaterialModel.class);
			videoMaterialModel.setId(materialId);
			videoMaterialDao.insert(videoMaterialModel);
		}
		// 信息流
		if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			InfoflowCreativeBean ifBean = (InfoflowCreativeBean)bean;
			ifBean.setId(UUIDGenerator.getUUID());
			String materialId = UUIDGenerator.getUUID();
			ifBean.setMaterialId(materialId);
			creativeModel = modelMapper.map(ifBean, CreativeModel.class);
			InfoflowMaterialModel infoflowModel = modelMapper.map(ifBean, InfoflowMaterialModel.class);
			infoflowModel.setId(materialId);
			infoMaterialDao.insert(infoflowModel);
		}
		// 创意默认是关闭
		creativeModel.setEnable(StatusConstant.CREATIVE_ISNOT_ENABLE); 
		creativeDao.insertSelective(creativeModel);
	}
	
	/**
	 * 删除创意
	 * @param creativeId
	 * @throws Exception
	 */
	@Transactional
	public void deleteCreative(String creativeId) throws Exception {
		CreativeModel creativeInDB = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		String campaignId = creativeInDB.getCampaignId();
		if (campaignService.isBeginLaunchDate(campaignId) || launchService.isHaveCreativeInfo(creativeId)) {
			throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_BEGIN);
		}
		
		// 删除创意审核表数据
		// 1.查询创意id
		CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
		creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditEx);
		// 2.一条创意对应可以对应多条审核信息，都删除
		for (CreativeAuditModel creativeAudit : creativeAudits) {
			// 创意审核id
			String creativeAuditId = creativeAudit.getId();
			// 删除
			creativeAuditDao.deleteByPrimaryKey(creativeAuditId);
		}	
		
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
		if (creativeIds.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(Arrays.asList(creativeIds));
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		if (creativeModels == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		// 判断哪些创意不可以删除 ：已经开始投放；或是当某个创意ID，存在回收数据时，不能删除创意
		for (CreativeModel creative : creativeModels) {
			// 获取活动id
			String campaignId = creative.getCampaignId();
			// 创意id
			String creativeId = creative.getId();
			if (campaignService.isBeginLaunchDate(campaignId) || launchService.isHaveCreativeInfo(creativeId)) {
				throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_BEGIN);
			}
		}
		
		CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
		creativeAuditExample.createCriteria().andCreativeIdIn(Arrays.asList(creativeIds));
		creativeAuditDao.deleteByExample(creativeAuditExample);
		
		// 删除创意数据		
		creativeDao.deleteByExample(creativeExample);
	}
	
	/**
	 * 修改创意价格
	 * @param id
	 * @param price
	 * @throws Exception
	 */
	@Transactional
	public void updateCreativePrice(String id, Map<String, String> map) throws Exception{
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		if (creative == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
		String price = map.get("price");
		creative.setPrice(Float.parseFloat(price));
		creativeDao.updateByPrimaryKey(creative);
		if (launchService.isHaveLaunched(creative.getCampaignId())) {
			launchService.updateCreativePrice(id);
		}
	}
	
	
	/**
	 * 添加素材
	 * @param tmplId
	 * @param file
	 * @throws Exception
	 */
	@Transactional
	public Map<String, String> uploadMaterial(String tmplId, MultipartFile file) throws Exception {
		MediaBean mediaBean = FileUtils.checkFile(file);  // 解析传来图片的宽高
		Map<String, String> result = null;
		
		if (mediaBean instanceof ImageBean) {
			ImageBean imageBean = (ImageBean) FileUtils.checkFile(file);
			ImageTmplModel tmpl = imageTmplDao.selectByPrimaryKey(tmplId);
			if (tmpl == null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			Integer tmplWidth = tmpl.getWidth(); //模版宽限制
			Integer tmplHeight = tmpl.getHeight(); //模版高限制
			Float maxVolume = tmpl.getMaxVolume(); //模版最大体积限制
			int height = imageBean.getHeight(); //文件高
			int width = imageBean.getWidth(); //文件宽
			Float volume = imageBean.getVolume(); //文件体积限制
			
			if (tmplWidth != width || tmplHeight != height || maxVolume < volume) {
				throw new IllegalArgumentException(PhrasesConstant.TEMPLET_NOT_MAP_SIZE);
			}
			
			result = uploadImage(imageBean, file);
		} else if (mediaBean instanceof VideoBean) {
			VideoBean videoBean = (VideoBean) FileUtils.checkFile(file);
			VideoTmplModel tmpl = videoTmplDao.selectByPrimaryKey(tmplId);
			if (tmpl == null) {
				throw new ResourceNotFoundException(PhrasesConstant.TEMPLET_NOT_FUOUND);
			}
			Integer tmplWidth = tmpl.getWidth(); //模版宽限制
			Integer tmplHeight = tmpl.getHeight(); //模版高限制
			Float maxVolume = tmpl.getMaxVolume(); //模版最大体积限制
			Integer maxTimelength = tmpl.getMaxTimelength(); //模版最大时长
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
	private Map<String, String> uploadImage(ImageBean imageBean, MultipartFile file) throws Exception {
		String id = UUIDGenerator.getUUID();
		String dir = uploadDir + "creative/image/";
		
		String path = doUpload(dir, id, file);
		String type = imageBean.getFormat();
		Float volume = imageBean.getVolume();
		//查询尺寸ID
		int height = imageBean.getHeight();
		int width = imageBean.getWidth();
		//添加图片信息
		ImageModel model = new ImageModel();
		model.setId(id);
		model.setPath(path.replace(uploadDir, ""));
		model.setFormat(type);
		model.setVolume(Double.parseDouble(String.valueOf(volume)));
		model.setWidth(width);
		model.setHeight(height);
		imageDao.insertSelective(model);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(uploadDir, ""));
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
		String id = UUIDGenerator.getUUID();
		String dir = uploadDir + "creative/video/";
		String path = doUpload(dir, id, file);
		
		String type = videoBean.getFormat();
		Float volume = videoBean.getVolume();
		//查询尺寸ID
		int height = videoBean.getHeight();
		int width = videoBean.getWidth();
		
		VideoModel model = new VideoModel();
		//添加视频信息
		model.setId(id);
		model.setPath(path.replace(uploadDir, ""));
		model.setFormat(type);
		model.setWidth(width);
		model.setHeight(height);
		model.setVolume(Double.parseDouble(String.valueOf(volume)));
		model.setTimeLength(videoBean.getTimelength());
		videoDao.insertSelective(model);	
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("path", path.replace(uploadDir, ""));
		return map;
	}
	
	/**
	 * 创意提交第三方审核
	 * @param id
	 * @throws Exception
	 */
//	@Transactional
//	public void auditCreative(String id) throws Exception {
//		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
//		if (creative == null) {
//			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
//		}
//		String status = getCreativeAuditStatus(id);
//		if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_WATING.equals(status)) {
//			return;
//		}
//		
//		// 查询adx列表，判断是哪个adx
//		List<Map<String, String>> adxes = launchService.getAdxByCreative(creative);
//		// 审核创意
//		for (Map<String, String> adx : adxes) {
//			String adxId = adx.get("adxId");
//			if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
//				momoAuditService.auditCreative(id);
//			}
//			if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
//				inmobiAuditService.auditCreative(id);
//			}
//			if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
//                autohomeAuditService.auditCreative(id);
//            }
//			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
//			    baiduAuditService.auditCreative(id);
//			}
//		}
//	}

	/**
	 * 同步创意第三方审核结果
	 * @param id
	 * @throws Exception
	 */
//	@Transactional
//	public void synchronizeCreative(String id) throws Exception {
//		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
//		if (creative == null) {
//			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
//		}
//		String status = getCreativeAuditStatus(id);
//		if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(status) 
//				|| StatusConstant.ADVERTISER_AUDIT_NOCHECK.equals(status)) {
//			return;
//		}
//		
//		// 查询adx列表
//		List<Map<String, String>> adxes = launchService.getAdxByCreative(creative);
//		//同步结果
//		for (Map<String, String> adx : adxes) {
//			CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
//			creativeAuditExample.createCriteria().andCreativeIdEqualTo(id).andAdxIdEqualTo(adx.get("adxId"));
//			List<CreativeAuditModel> audits = creativeAuditDao.selectByExample(creativeAuditExample);
//			if (audits == null || audits.isEmpty()) {
//				throw new ServerFailureException(PhrasesConstant.CREATIVE_AUDIT_NULL);
//			} else {
//				String adxId = adx.get("adxId");
//				if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
//					momoAuditService.synchronizeCreative(id);
//				}
//				if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
//					inmobiAuditService.synchronizeCreative(id);
//				}
//				if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
//				    autohomeAuditService.synchronizeCreative(id);
//                }
//				if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
//	                baiduAuditService.synchronizeCreative(id);
//	            }
//			}
//		}
//	}
	
	/**
	 * 批量查询创意
	 * @param name
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<BasicDataBean> listCreatives(String campaignId, String name, String type, Long startDate, Long endDate) throws Exception {
		List<BasicDataBean> result = new ArrayList<BasicDataBean>();
		CreativeModelExample example = new CreativeModelExample();
		
		// 按更新时间进行倒序排序
		example.setOrderByClause("update_time DESC");

		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andCampaignIdEqualTo(campaignId).andNameLike("%" + name + "%");
		} else {
			example.createCriteria().andCampaignIdEqualTo(campaignId);
		}

		List<CreativeModel> creatives = creativeDao.selectByExample(example);
		if (creatives != null && !creatives.isEmpty()) {
			CreativeBean base = null;
			ImageCreativeBean image = null;
			VideoCreativeBean video = null;
			InfoflowCreativeBean info = null;
			for (CreativeModel creative : creatives) {
				Map<String, String> adxInfo = launchService.getAdxByCreative(creative);
				String adxId = adxInfo.get("adxId");
				String adxName = adxInfo.get("adxName");
				CreativeAuditModel creativeAuditModel = getCreativeAuditModelByCreativeId(creative.getId());
				if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
					ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
					if (imageMaterialModel != null) {
						ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
						if (imageModel != null) {
							image = new ImageCreativeBean();
							
							image.setId(creative.getId());
							image.setType(type);
							image.setCampaignId(campaignId);
							
							image.setStatus(getCreativeAuditStatus(creative.getId()));
							//设置message
							if (creativeAuditModel != null) {
								image.setMessage(creativeAuditModel.getMessage());
							} else {
								image.setMessage("");
							}
							image.setPrice(creative.getPrice().floatValue());

							image.setImageId(creative.getMaterialId());
							image.setImagePath(imageModel.getPath());

							image.setAdxId(adxId);
							image.setAdxName(adxName);
							image.setEnable(creative.getEnable());

							//查询投放数据
							if (startDate != null && endDate != null) {
								String creativeId = creative.getId();
								CreativeBean data = (CreativeBean) dataService.getCreativeData(creativeId, startDate, endDate);
								BeanUtils.copyProperties(data, image, "id", "type", "status", "campaignId", "campaignName", "price",
										"tmplId", "materialId", "remark", "adxId", "adxName", "enable", "startDate", "endDate",
										"materialPaths", "imageId", "imagePath", "message");
							}
							result.add(image);
						}
					}
				} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creative.getMaterialId());
					if (videoMaterialModel == null) {
						throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
					}
					String imageId = videoMaterialModel.getImageId();
					VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
					if (videoModel != null) {
						video = new VideoCreativeBean();
						video.setId(creative.getId());
						video.setType(type);
						video.setCampaignId(campaignId);
						video.setStatus(getCreativeAuditStatus(creative.getId()));
						//设置message
						if (creativeAuditModel != null) {
							video.setMessage(creativeAuditModel.getMessage());
						} else {
							video.setMessage("");
						}
						video.setPrice(creative.getPrice().floatValue());

						video.setImageId(imageId);
						video.setImagePath(getImagePath(imageId));
						video.setVideoId(creative.getMaterialId());
						video.setVideoPath(videoModel.getPath());

						video.setAdxId(adxId);
						video.setAdxName(adxName);
						video.setEnable(creative.getEnable());

						//查询投放数据
						if (startDate != null && endDate != null) {
							String creativeId = creative.getId();
							CreativeBean data = (CreativeBean) dataService.getCreativeData(creativeId, startDate, endDate);
							BeanUtils.copyProperties(data, video, "id", "type", "status", "campaignId", "campaignName", "price",
									"tmplId", "materialId", "remark", "adxId", "adxName", "enable", "startDate", "endDate",
									"materialPaths", "videoId", "videoPath", "imageId", "imagePath", "message");
						}
						result.add(video);
					}
				} else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
					InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
					// 查询信息流模板信息
					InfoflowTmplModel InfoflowTmpl = infoflowTmplDao.selectByPrimaryKey(creative.getTmplId());
					if (infoflowModel != null) {
						info = new InfoflowCreativeBean();
						info.setId(creative.getId());
						info.setType(type);
						info.setCampaignId(campaignId);
						info.setStatus(getCreativeAuditStatus(creative.getId()));
						//设置message
						if (creativeAuditModel != null) {
							info.setMessage(creativeAuditModel.getMessage());
						} else {
							info.setMessage("");
						}
						info.setPrice(creative.getPrice().floatValue());

						info.setAdxId(adxId);
						info.setAdxName(adxName);
						info.setEnable(creative.getEnable());

						info.setTitle(infoflowModel.getTitle());                   //标题
						info.setDescription(infoflowModel.getDescription());       //描述
						info.setCtaDescription(infoflowModel.getCtaDescription()); //CTA描述
						info.setMustDescription(InfoflowTmpl.getMustDescription()); //描述是否必填
						info.setMustCtaDescription(InfoflowTmpl.getMustCtaDescription()); //CTA描述是否必填
						info.setHaveDescription(InfoflowTmpl.getHaveDescription());  // 是否有描述
						info.setHaveCtaDescription(InfoflowTmpl.getHaveCtaDescription()); // 是否有CTA描述


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
						//查询投放数据
						if (startDate != null && endDate != null) {
							String creativeId = creative.getId();
							CreativeBean data = (CreativeBean) dataService.getCreativeData(creativeId, startDate, endDate);
							BeanUtils.copyProperties(data, info, "id", "type", "status", "campaignId", "campaignName", "price",
									"tmplId", "materialId", "remark", "adxId", "adxName", "enable", "startDate", "endDate",
									"materialPaths", "infoflowId", "title", "description", "mustDescription", "ctaDescription",
									"mustCtaDescription", "iconId", "iconPath", "image1Id", "image2Id", "image3Id", "image4Id",
									"image5Id", "image1Path", "image2Path", "image3Path", "image4Path", "image5Path",
									"haveDescription", "haveCtaDescription", "message");
						}
						result.add(info);
					}
				} else {
					base = modelMapper.map(creative, CreativeBean.class);
					base.setAdxId(adxId);
					base.setAdxName(adxName);
					String creativeType = creative.getType();
					String[] materialPaths = null;
					// 创意显示活动周期
					CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
					Date campaignStartDate = campaign.getStartDate();
					Date campaignEndDate = campaign.getEndDate();
					base.setStartDate(campaignStartDate);
					base.setEndDate(campaignEndDate);

					if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
						ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
						if (imageMaterialModel != null) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
							if (imageModel != null) {
								String path = imageModel.getPath();
								materialPaths = new String[1];
								materialPaths[0] = path;
							}
						}
					} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
						VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creative.getMaterialId());
						if (videoMaterialModel != null) {
							materialPaths = new String[2];
							VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
							if (videoModel != null) {
								String imageId = videoMaterialModel.getImageId();
								if (!StringUtils.isEmpty(imageId)) {
									ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
									materialPaths = new String[2];
									materialPaths[0] = videoModel.getPath();
									materialPaths[1] = imageModel.getPath();
								} else {
									materialPaths = new String[1];
									materialPaths[0] = videoModel.getPath();
								}
							}
						}
					} else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
						InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
						List<String> imgList = new ArrayList<String>();
						String iconId = infoflowModel.getIconId();
						String image1Id = infoflowModel.getImage1Id();
						String image2Id = infoflowModel.getImage2Id();
						String image3Id = infoflowModel.getImage3Id();
						String image4Id = infoflowModel.getImage4Id();
						String image5Id = infoflowModel.getImage5Id();
						if (!StringUtils.isEmpty(iconId)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(iconId);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						if (!StringUtils.isEmpty(image1Id)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(image1Id);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						if (!StringUtils.isEmpty(image2Id)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(image2Id);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						if (!StringUtils.isEmpty(image3Id)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(image3Id);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						if (!StringUtils.isEmpty(image4Id)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(image4Id);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						if (!StringUtils.isEmpty(image5Id)) {
							ImageModel imageModel = imageDao.selectByPrimaryKey(image5Id);
							if (imageModel != null) {
								imgList.add(imageModel.getPath());
							}
						}
						materialPaths = new String[imgList.size()];
						materialPaths = imgList.toArray(materialPaths);
						base.setTitle(infoflowModel.getTitle());
						base.setDescription(infoflowModel.getDescription());
					} else {
						throw new IllegalArgumentException();
					}
					//查询投放数据
					if (startDate != null && endDate != null) {
						String creativeId = creative.getId();
						CreativeBean data = (CreativeBean) dataService.getCreativeData(creativeId, startDate, endDate);
						BeanUtils.copyProperties(data, base, "id", "type", "status", "campaignId", "campaignName", "price",
								"tmplId", "materialId", "remark", "adxId", "adxName", "enable", "startDate", "endDate",
								"materialPaths", "message", "title", "description");
					}
					base.setStatus(getCreativeAuditStatus(creative.getId()));
					//设置message
					if (creativeAuditModel != null) {
						base.setMessage(creativeAuditModel.getMessage());
					} else {
						base.setMessage("");
					}
					base.setMaterialPaths(materialPaths);

					result.add(base);
				}
			}
		}

		return result;
	}

	/**
	 * 查询单个创意
	 *
	 * @param id
	 * @return
	 */
	@Transactional
	public BasicDataBean getCreative(String id, Long startDate, Long endDate) throws Exception {
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		if (creative == null) {
			throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
		}
		String campaignId = creative.getCampaignId();
		String type = creative.getType();
		CreativeBean base = null;
		ImageCreativeBean image = null;
		VideoCreativeBean video = null;
		InfoflowCreativeBean info = null;
		Map<String, String> adxInfo = launchService.getAdxByCreative(creative);
		String adxId = adxInfo.get("adxId");
		String adxName = adxInfo.get("adxName");
		//查询审核信息
		CreativeAuditModel creativeAuditModel = getCreativeAuditModelByCreativeId(creative.getId());

		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			//如果创意类型是图片
			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			if (imageMaterialModel == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
			if (imageModel != null) {
				image = new ImageCreativeBean();
				image.setId(creative.getId());       //创意id
				image.setType(type);                 //创意类型
				image.setCampaignId(campaignId);     //活动id

				image.setStatus(getCreativeAuditStatus(creative.getId())); //创意审核状态
				//设置message
				if (creativeAuditModel != null) {
					image.setMessage(creativeAuditModel.getMessage());
				} else {
					image.setMessage("");
				}
				image.setPrice(creative.getPrice().floatValue());          //创意价格
				image.setTmplId(creative.getTmplId());                     //模板ID
				image.setAdxId(adxId);
				image.setAdxName(adxName);
				image.setEnable(creative.getEnable());           //创意的状态

				image.setImageId(imageModel.getId());   //图片id
				image.setImagePath(imageModel.getPath());     //图片路径


				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					CreativeBean dataBean = dataService.getCreativeData(creativeId, startDate, endDate);
					if (dataBean != null) {
						image.setImpressionAmount(dataBean.getImpressionAmount()); //展现数
						image.setClickAmount(dataBean.getClickAmount());           //点击数
						image.setTotalCost(dataBean.getTotalCost());               //总花费
						image.setJumpAmount(dataBean.getJumpAmount());             //二跳数
						image.setImpressionCost(dataBean.getImpressionCost());     //展现成本
						image.setClickCost(dataBean.getClickCost());               //点击成本
						image.setClickRate(dataBean.getClickRate());               //点击率
						image.setJumpCost(dataBean.getJumpCost());                 //二跳成本
						image.setAdxCost(dataBean.getAdxCost());        //修正成本
					}
				}
				return image;
			}
		} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			// 如果创意类型是视频
			VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			if (videoMaterialModel == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			String imageId = videoMaterialModel.getImageId();
			VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
			if (videoModel != null) {
				video = new VideoCreativeBean();
				video.setId(creative.getId());                             //创意id
				video.setType(type);                                       //创意类型
				video.setCampaignId(campaignId);                           //活动id
				video.setStatus(getCreativeAuditStatus(creative.getId())); //创意审核状态
				//设置message
				if (creativeAuditModel != null) {
					video.setMessage(creativeAuditModel.getMessage());
				} else {
					video.setMessage("");
				}
				video.setPrice(creative.getPrice().floatValue());          //创意价格
				video.setTmplId(creative.getTmplId());                     //模板Id
				video.setAdxId(adxId);
				video.setAdxName(adxName);
				video.setEnable(creative.getEnable());                     //创意状态

				video.setImageId(imageId);                                 //图片id
				video.setImagePath(getImagePath(imageId));                 //图片路径
				video.setVideoId(creative.getMaterialId());                //视频id
				video.setVideoPath(videoModel.getPath());                  //视频路径

				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					CreativeBean dataBean = dataService.getCreativeData(creativeId, startDate, endDate);
					if (dataBean != null) {
						video.setImpressionAmount(dataBean.getImpressionAmount());    //展现数
						video.setClickAmount(dataBean.getClickAmount());              //点击数
						video.setTotalCost(dataBean.getTotalCost());                  //总花费
						video.setJumpAmount(dataBean.getJumpAmount());                //二跳数
						video.setImpressionCost(dataBean.getImpressionCost());        //展现成本
						video.setClickCost(dataBean.getClickCost());                  //点击成本
						video.setClickRate(dataBean.getClickRate());                  //点击率
						video.setJumpCost(dataBean.getJumpCost());                    //二跳成本
						video.setAdxCost(dataBean.getAdxCost());          //修正成本
					}
				}

				return video;
			}
		} else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			// 如果创意类型是信息流
			// 查询信息流素材信息
			InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creative.getMaterialId());
			// 查询信息流模板信息
			InfoflowTmplModel InfoflowTmpl = infoflowTmplDao.selectByPrimaryKey(creative.getTmplId());
			if (infoflowModel != null) {
				info = new InfoflowCreativeBean();
				info.setId(creative.getId());                              //创意id
				info.setType(type);                                        //创意类型
				info.setCampaignId(campaignId);                            //活动id
				info.setStatus(getCreativeAuditStatus(creative.getId()));  //创意的审核状态
				//设置message
				if (creativeAuditModel != null) {
					info.setMessage(creativeAuditModel.getMessage());
				} else {
					info.setMessage("");
				}
				info.setPrice(creative.getPrice().floatValue());           //创意价格
				info.setTmplId(creative.getTmplId());                      //模板Id
				info.setAdxId(adxId);
				info.setAdxName(adxName);
				info.setEnable(creative.getEnable());                      //创意状态
				info.setTitle(infoflowModel.getTitle());                   //标题
				info.setDescription(infoflowModel.getDescription());       //描述
				info.setCtaDescription(infoflowModel.getCtaDescription()); //CTA描述
				info.setMustDescription(InfoflowTmpl.getMustDescription()); //描述是否必填
				info.setMustCtaDescription(InfoflowTmpl.getMustCtaDescription()); //CTA描述是否必填
				info.setHaveDescription(InfoflowTmpl.getHaveDescription());  // 是否有描述
				info.setHaveCtaDescription(InfoflowTmpl.getHaveCtaDescription()); // 是否有CTA描述

				if (!StringUtils.isEmpty(infoflowModel.getIconId())) {
					info.setIconId(infoflowModel.getIconId());                  //图标id
					info.setIconPath(getImagePath(infoflowModel.getIconId()));  //图标路径
				}
				if (!StringUtils.isEmpty(infoflowModel.getImage1Id())) {
					info.setImage1Id(infoflowModel.getImage1Id());                   //大图1的id
					info.setImage1Path(getImagePath(infoflowModel.getImage1Id()));   //大图1路径
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

				return info;
			}
		} else {
			//否则
			base = modelMapper.map(creative, CreativeBean.class);
			base.setAdxId(adxId);
			base.setAdxName(adxName);
			//查询投放数据
			if (startDate != null && endDate != null) {
				String creativeId = creative.getId();
				List<String> idList = new ArrayList<String>();
				idList.add(creativeId);
				CreativeBean dataBean = dataService.getCreativeData(creativeId, startDate, endDate);
				if (dataBean != null) {
					base.setImpressionAmount(dataBean.getImpressionAmount());        //展现数
					base.setClickAmount(dataBean.getClickAmount());                  //点击数
					base.setTotalCost(dataBean.getTotalCost());                      //总花费
					base.setJumpAmount(dataBean.getJumpAmount());                    //二跳数
					base.setImpressionCost(dataBean.getImpressionCost());            //展现成本
					base.setClickCost(dataBean.getClickCost());                      //点击成本
					base.setClickRate(dataBean.getClickRate());                      //点击率
					base.setJumpCost(dataBean.getJumpCost());                        //二跳成本
					base.setAdxCost(dataBean.getAdxCost());          //修正成本
				}
			}
			base.setStatus(getCreativeAuditStatus(creative.getId()));                //创意的审核状态
			//设置message
			if (creativeAuditModel != null) {
				base.setMessage(creativeAuditModel.getMessage());
			} else {
				base.setMessage("");
			}
			base.setStatus(getCreativeAuditStatus(creative.getId())); //创意的审核状态

			return base;
		}
		return new BasicDataBean();
	}


	/**
	 * 根据图片素材id查询图片路径
	 *
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
	 *
	 * @param creativeId
	 * @return
	 */
	public String getCreativeAuditStatus(String creativeId) {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> models = creativeAuditDao.selectByExample(example);
		String status = StatusConstant.CREATIVE_AUDIT_NOCHECK;
		boolean successFlag = false;
		for (CreativeAuditModel model : models) {
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(model.getStatus())) {
				status = StatusConstant.CREATIVE_AUDIT_SUCCESS;
				successFlag = true;
				break;
			}
		}
		if (!successFlag) {
			boolean watingFlag = false;
			for (CreativeAuditModel model : models) {
				if (StatusConstant.CREATIVE_AUDIT_WATING.equals(model.getStatus())) {
					status = StatusConstant.CREATIVE_AUDIT_WATING;
					watingFlag = true;
					break;
				}
			}
			if (!watingFlag) {
				for (CreativeAuditModel model : models) {
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
	 * 根据配置文件中的设置，来绝定上传文件至本地文件服务器或远程文件服务器
	 *
	 * @param env  SpringBoot Environment配置
	 * @param file multipart/form-data对象
	 * @return
	 * @throws IOException
	 */
	private String doUpload(String uploadDir, String fileName, MultipartFile file) throws IOException {
		if ("local".equalsIgnoreCase(uploadMode)) {
			return FileUtils.uploadFileToLocal(uploadDir, fileName, file);
		} else {
			return FileUtils.uploadFileToRemote(host, port, username, password, uploadDir, fileName, file);
		}
	}

	/**
	 * 修改已到过期时间的创意审核状态为“已过期”————————定时器
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateExpityDate() throws Exception {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		List<CreativeAuditModel> list = creativeAuditDao.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			for (CreativeAuditModel model : list) {
				Date expiryDate = model.getExpiryDate();
				if (expiryDate != null) {
					DateTime expiry = new DateTime(expiryDate);
					expiry.toString("yyyy-MM-dd");
					String now = new DateTime().toString("yyyy-MM-dd");
					if (expiry.equals(now)) {
						model.setStatus(StatusConstant.CREATIVE_AUDIT_EXPITY);
						creativeAuditDao.updateByPrimaryKeySelective(model);
					}
				}
			}
		}
	}

//	private Map<String, String> getAppInfo(CreativeModel creative) throws Exception {
//		String campaignId = creative.getCampaignId();
//		String tmplId = creative.getTmplId();
//		
//		//获取活动下的APPId
//        List<String> appIdList = tmplService.getAppidByCampaignId(campaignId);
//		
//		Map<String, String> result = new HashMap<String, String>();
//		String appIds = "";
//		String appNames = "";
//		
//		AppTmplModelExample appTmplExample = new AppTmplModelExample();
//		appTmplExample.createCriteria().andTmplIdEqualTo(tmplId);
//		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplExample);
//		for (AppTmplModel tmpl : appTmpls) {
//			String appId = tmpl.getAppId();
//			if (appIdList.contains(appId)) {
//				AppModel app = appDao.selectByPrimaryKey(appId);
//				appIds = appIds + appId + ",";
//				appNames = appNames + app.getAppName() + ",";
//			}
//		}
//		if (appIds.length() > 1 && appNames.length() > 1) {
//			result.put("appIds", appIds.substring(0, appIds.length()-1));
//			result.put("appNames", appNames.substring(0, appNames.length()-1));
//		}
//		return result;
//	}

	/**
	 * 修改创意
	 *
	 * @param id
	 * @param creativeBean
	 * @throws Exception
	 */
	@Transactional
	public void updateCreative(String id, CreativeBean creativeBean) throws Exception {
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(id);
		if (creativeModel == null) {
			// 如果没有该对象不能修改创意
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		//获取活动id
		String campaignId = creativeModel.getCampaignId();
		//是否暂停活动，更改创意状态为未审核的标识
		boolean flag = false;
		// 修改创意
		String type = creativeModel.getType(); //创意类型
		String materialId = creativeModel.getMaterialId(); //素材ID
		// 图片   
		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageMaterialModel imageMeterial = imageMaterialDao.selectByPrimaryKey(materialId);
			if (imageMeterial == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			ImageCreativeBean imageBean = (ImageCreativeBean) creativeBean;
			creativeModel = modelMapper.map(imageBean, CreativeModel.class);
			ImageMaterialModel imageMaterialModel = modelMapper.map(imageBean, ImageMaterialModel.class);
			imageMaterialModel.setId(materialId);
			imageMaterialDao.updateByPrimaryKeySelective(imageMaterialModel);
			launchService.writeImgCreativeInfo(creativeModel);
			//判断除了了价格外，是否还更改了其他，如果更改了，则要在下面停止项目的投放
			if ((imageMeterial.getImageId() == null && imageMaterialModel.getImageId() != null) || (imageMeterial.getImageId() != null && !imageMeterial.getImageId().equals(imageMaterialModel.getImageId()))) {
				flag = true;
			}
		}
		// 视频 
		if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			VideoMaterialModel videoMaterial = videoMaterialDao.selectByPrimaryKey(materialId);
			if (videoMaterial == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			VideoCreativeBean videoBean = (VideoCreativeBean) creativeBean;
			creativeModel = modelMapper.map(videoBean, CreativeModel.class);
			VideoMaterialModel videoMaterialModel = modelMapper.map(videoBean, VideoMaterialModel.class);
			videoMaterialModel.setId(materialId);
			videoMaterialDao.updateByPrimaryKeySelective(videoMaterialModel);
			launchService.writeVideoCreativeInfo(creativeModel);
		}
		// 信息流
		if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(type)) {
			// 查询信息流信息
			InfoflowMaterialModel infoflowMaterial = infoMaterialDao.selectByPrimaryKey(materialId);
			if (infoflowMaterial == null) {
				// 如果信息流信息为空
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			InfoflowCreativeBean infoBean = (InfoflowCreativeBean) creativeBean;
			creativeModel = modelMapper.map(infoBean, CreativeModel.class);
			InfoflowMaterialModel infoflowMaterialModel = modelMapper.map(infoBean, InfoflowMaterialModel.class);
			// 放入ID，用于更新关联关系表中数据
			infoflowMaterialModel.setId(materialId);
			// 更新信息流
			infoMaterialDao.updateByPrimaryKeySelective(infoflowMaterialModel);
			launchService.writeInfoflowCreativeInfo(creativeModel);
			//判断除了了价格外，是否还更改了其他，如果更改了，则要在下面停止项目的投放
			if (!infoflowMaterial.getTitle().equals(infoflowMaterialModel.getTitle()) ||
					(infoflowMaterial.getDescription() == null && infoflowMaterialModel.getDescription() != null) || (infoflowMaterial.getDescription() != null && !infoflowMaterial.getDescription().equals(infoflowMaterialModel.getDescription()))
					|| (infoflowMaterial.getIconId() == null && infoflowMaterialModel.getIconId() != null) || (infoflowMaterial.getIconId() != null && !infoflowMaterial.getIconId().equals(infoflowMaterialModel.getIconId()))
					|| (infoflowMaterial.getImage1Id() == null && infoflowMaterialModel.getImage1Id() != null) || (infoflowMaterial.getImage1Id() != null && !infoflowMaterial.getImage1Id().equals(infoflowMaterialModel.getImage1Id()))
					|| (infoflowMaterial.getImage2Id() == null && infoflowMaterialModel.getImage2Id() != null) || (infoflowMaterial.getImage2Id() != null && !infoflowMaterial.getImage2Id().equals(infoflowMaterialModel.getImage2Id()))
					|| (infoflowMaterial.getImage3Id() == null && infoflowMaterialModel.getImage3Id() != null) || (infoflowMaterial.getImage3Id() != null && !infoflowMaterial.getImage3Id().equals(infoflowMaterialModel.getImage3Id()))
					|| (infoflowMaterial.getImage4Id() == null && infoflowMaterialModel.getImage4Id() != null) || (infoflowMaterial.getImage4Id() != null && !infoflowMaterial.getImage4Id().equals(infoflowMaterialModel.getImage4Id()))
					|| (infoflowMaterial.getImage5Id() == null && infoflowMaterialModel.getImage5Id() != null) || (infoflowMaterial.getImage5Id() != null && !infoflowMaterial.getImage5Id().equals(infoflowMaterialModel.getImage5Id()))
					) {
				flag = true;
			}
		}
		// 放入ID，用于更新关联关系表中数据
		creativeModel.setId(id);

		if (flag) {
			//停止创意投放
			launchService.removeOneCreativeId(campaignId, id);
			//把状态改为未审核
			updateCreativeAuditStatusByCreativeId(id, StatusConstant.CREATIVE_AUDIT_NOCHECK);
		}
		// 更新创意表信息
		creativeDao.updateByPrimaryKeySelective(creativeModel);
	}

	/**
	 * 更改创意的审核状态为指定状态
	 *
	 * @param creativeId 创意id
	 * @param status     状态值
	 */
	public void updateCreativeAuditStatusByCreativeId(String creativeId, String status) {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> auditsInDB = creativeAuditDao.selectByExample(example);

		// 如果创意审核表信息已存在，更新数据库中原记录的状态
		if (auditsInDB != null && !auditsInDB.isEmpty()) {
			updateCreativeAuditStatus(auditsInDB, status);
		}
	}

	/**
	 * 更改活动下面的所有创意审核状态为指定状态
	 *
	 * @param campaignId
	 * @param status
	 */
	public void updateCreativeAuditStatusByCampaignId(String campaignId, String status) {
		//查询活动下面的所有创意
		CreativeModelExample creativeModelExample = new CreativeModelExample();
		creativeModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeModelExample);
		List<String> creativeIds = new ArrayList<String>();
		if (creativeModels != null && !creativeModels.isEmpty()) {
			for (CreativeModel creativeModel : creativeModels) {
				creativeIds.add(creativeModel.getId());
			}
		}
		if (creativeIds.isEmpty()) {
			return;
		}
		//根据创意id查询创意审核
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdIn(creativeIds);
		List<CreativeAuditModel> auditsInDB = creativeAuditDao.selectByExample(example);

		// 如果创意审核表信息已存在，更新数据库中原记录的状态
		if (auditsInDB != null && !auditsInDB.isEmpty()) {
			updateCreativeAuditStatus(auditsInDB, status);
		}
	}

	/**
	 * 更改创意审核状态为指定状态
	 *
	 * @param auditsInDB
	 * @param status
	 */
	public void updateCreativeAuditStatus(List<CreativeAuditModel> auditsInDB, String status) {
		for (CreativeAuditModel auditInDB : auditsInDB) {
			if (!auditInDB.getStatus().equals(status)) {
				auditInDB.setStatus(status);
				creativeAuditDao.updateByPrimaryKeySelective(auditInDB);
			}
		}
	}

	/**
	 * 批量同步创意
	 *
	 * @param creativeIds 创意ids
	 * @throws Exception
	 */
	@Transactional
	public void synchronizeCreatives(String[] creativeIds) throws Exception {
		if (creativeIds.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 根据创意id列表查询创意信息
		List<String> creativeIdsList = Arrays.asList(creativeIds);
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(creativeIdsList);
		// 判断创意是否为空
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		if (creativeModels == null || creativeIds.length > creativeModels.size()) {
			throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
		}
		for (CreativeModel creative : creativeModels) {
			String creativeId = creative.getId();
			String status = getCreativeAuditStatus(creativeId);
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.CREATIVE_AUDIT_FAILURE.equals(status)
					|| StatusConstant.CREATIVE_AUDIT_NOCHECK.equals(status)) {
				continue;
			}
			// 查询adx列表，一个创意可以由多个ADX审核  
			Map<String, String> adx = launchService.getAdxByCreative(creative);

			// 同步审核结果
			// 获取adxId
			String adxId = adx.get("adxId");
			// 判断创意审核表中该创意的审核信息是否为空，不为空判断是由哪个adx广告平台审核--对应同步其审核结果
			// 1.查询广告主审核信息：根据创意id + adxId + 审核状态为审核中
			CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
			creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(adxId);
			List<CreativeAuditModel> creativeAudit = creativeAuditDao.selectByExample(creativeAuditExample);
			// 2.判断广告主信息是否为空
			if (creativeAudit == null || creativeAudit.isEmpty()) {
				// 如果创意审核信息为空
				throw new ServerFailureException(PhrasesConstant.CREATIVE_AUDIT_NULL);
			} else if (StatusConstant.CREATIVE_AUDIT_WATING.equals(creativeAudit.get(0).getStatus())) {
				// 则创意审核信息不为空，判断哪个ADX审核
				if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
					// 如果ADX为陌陌，则同步陌陌审核结果
					momoAuditService.synchronizeCreative(creativeId);
				}
				if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
					// 如果ADX为inmobi，则同步inmobi的审核结果
					inmobiAuditService.synchronizeCreative(creativeId);
				}
				if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
					// 如果ADX属于汽车之家，则同步汽车之家审核结果
					autohomeAuditService.synchronizeCreative(creativeId);
				}
				if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
					baiduAuditService.synchronizeCreative(creativeId);
				}
				if (AdxKeyConstant.ADX_TENCENT_VALUE.equals(adxId)) {
					tencentAuditService.synchronizeCreative(creativeId);
				}
				if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adxId)) {
					adviewAuditService.synchronizeCreative(creativeId);
				}
			}

			// 如果审核通过，则创意没有map基本信息将单个创意基本信息写入到redis中；将创意id写入门到redis的mapids中，
			String campaignId = creative.getCampaignId();
			if (StatusConstant.CREATIVE_IS_ENABLE.equals(creative.getEnable())) {
				writeCreativeInfoAndIdToRedis(campaignId, creativeId);
			}
		}
	}

	/**
	 * 批量审核创意
	 *
	 * @param creativeIds 创意ids
	 * @throws Exception
	 */
	@Transactional
	public void auditCreatives(String[] creativeIds) throws Exception {
		if (creativeIds.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 根据创意id列表查询创意信息
		List<String> creativeIdsList = Arrays.asList(creativeIds);
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(creativeIdsList);
		// 判断创意是否为空
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		if (creativeModels == null || creativeIds.length > creativeModels.size()) {
			// 如果创意信息为空
			throw new ResourceNotFoundException(PhrasesConstant.CREATIVE_NOT_FOUND);
		}

		// 审核创意
		for (CreativeModel creative : creativeModels) {
			// 创意id
			String creativeId = creative.getId();
			String status = getCreativeAuditStatus(creativeId);
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.CREATIVE_AUDIT_WATING.equals(status) || StatusConstant.CREATIVE_AUDIT_FAILURE.equals(status)) {
				continue;
			}
			Map<String, String> adx = launchService.getAdxByCreative(creative);
			// 根据不同的ADX到不同的广告审核平台审核
			// 获取ADX的Id，根据ADXID判断属于哪个ADX
			String adxId = adx.get("adxId");
			// 如果数据库中没有该创意为未审核的创意审核信息，则提交对应的平台进行审核
			if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
				// 如果ADX属于陌陌，则提交陌陌审核
				momoAuditService.auditCreative(creativeId);
			}
			if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
				// 如果ADX属于inmobi，则提交inmobi审核
				inmobiAuditService.auditCreative(creativeId);
			}
			if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
				// 如果ADX属于汽车之家，则提交汽车之家审核
				autohomeAuditService.auditCreative(creativeId);
			}
			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
				baiduAuditService.auditCreative(creativeId);
			}
			if (AdxKeyConstant.ADX_TENCENT_VALUE.equals(adxId)) {
				tencentAuditService.auditCreative(creativeId);
			}
			if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adxId)) {
				adviewAuditService.auditCreative(creativeId);
			}
		}
	}

	/**
	 * 编辑创意的状态，启动/暂停创意
	 *
	 * @param creativeId
	 * @param map
	 * @throws Exception
	 */
	@Transactional
	public void updateCreativeStatus(String creativeId, Map<String, String> map) throws Exception {
		// 获取传来的创意状态
		String enable = map.get("enable");
		// 判断传来的状态是否为空 
		if (StringUtils.isEmpty(enable)) {
			throw new IllegalArgumentException();
		}
		// 判断要修改的创意信息是否为空
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(creativeId);
		if (creativeModel == null) {
			// 如果要修改的创意信息为空，则抛出对象不存在异常
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		String campaignId = creativeModel.getCampaignId();
		if (StatusConstant.CREATIVE_ISNOT_ENABLE.equals(enable)) {
			// 暂停
			creativeModel.setEnable(StatusConstant.CREATIVE_ISNOT_ENABLE);
			// 更新数据库的状态
			creativeDao.updateByPrimaryKeySelective(creativeModel);
			// 将创意id从redis的dsp_groupid_mapids_中删除
			if (launchService.isHaveLaunched(campaignId)) {
				// 如果已经投放过，则将其从mapids中删除id
				launchService.removeOneCreativeId(campaignId, creativeId);
			}
		}
		if (StatusConstant.CREATIVE_IS_ENABLE.equals(enable)) {
			// 启动
			creativeModel.setEnable(StatusConstant.CREATIVE_IS_ENABLE);
			// 更新数据库中状态
			creativeDao.updateByPrimaryKeySelective(creativeModel);
			// 创意审核通过 && 创意开关打开 && 项目打开 && 活动打开 && 当前活动在投放日期内
			// 创意id不在mapids中,将单个创意id添加到mapids中；创意没有map基本信息，将单个创意基本信息写入到redis中
			writeCreativeInfoAndIdToRedis(campaignId, creativeId);
		}
	}

	/**
	 * 查询导入的素材
	 *
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MediaBean> listCreativeMaterials(Integer width, Integer height, String type, String advertiserId, String[] formats, String projectId, String campaignId) throws Exception {
		List<ImageModel> images = null;
		List<VideoModel> videos = null;
		List<MediaBean> result = new ArrayList<MediaBean>();
		if (formats.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 转换类型
		List<String> formatList = Arrays.asList(formats);

		// 1.查询广告主下的素材：根据广告id查询项目，项目id查询活动，活动可获得用过的素材id
		Set<String> advertiserMaterialIds = new HashSet<String>();
		// 根据广告主id查询广告主下的项目
		ProjectModelExample projectEx = new ProjectModelExample();
		projectEx.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<ProjectModel> projects = projectDao.selectByExample(projectEx);
		if (projects != null && !projects.isEmpty()) {
			for (ProjectModel project : projects) {
				// 根据项目id查询项目下的活动
				CampaignModelExample campaignEx = new CampaignModelExample();
				campaignEx.createCriteria().andProjectIdEqualTo(project.getId());
				List<CampaignModel> campaigns = campaignDao.selectByExample(campaignEx);
				if (campaigns != null && !campaigns.isEmpty()) {
					for (CampaignModel campaign : campaigns) {
						if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
							// 如果是视频，查询广告主下的视频
							Set<String> videoMaterialIds = listVideoIdByCampaign(campaign.getId());
							if (videoMaterialIds != null && !videoMaterialIds.isEmpty()) {
								for (String materialId : videoMaterialIds) {
									advertiserMaterialIds.add(materialId);
								}
							}
						} else {
							// 如果是图片，查询广告主下的图片
							Set<String> imageMaterialIds = listImageIdByCampaign(campaign.getId());
							if (imageMaterialIds != null && !imageMaterialIds.isEmpty()) {
								for (String materialId : imageMaterialIds) {
									advertiserMaterialIds.add(materialId);
								}
							}
						}
					}
				}
			}
		}

		// 2.满足宽、高、规格要求的并且属于某一个广告主的图片/视频集合：取出满足要求的素材与属于广告主的素材取交集
		if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			// 如果是视频，查询视频素材
			VideoModelExample videoEx = new VideoModelExample();
			videoEx.createCriteria().andWidthEqualTo(width).andHeightEqualTo(height).andFormatIn(formatList);
			// 判断视频是否属于广告主
			List<VideoModel> videoModels = videoDao.selectByExample(videoEx);
			if (videoModels != null && !videoModels.isEmpty()) {
				videos = new ArrayList<VideoModel>();
				for (VideoModel videoModel : videoModels) {
					String videoId = videoModel.getId();
					if (advertiserMaterialIds.contains(videoId)) {
						// 如果广告主下的视频id包含满足宽、高、规格要求的视频id，则将这个视频信息放到list中						
						VideoModel video = videoDao.selectByPrimaryKey(videoId);
						videos.add(video);
					}

				}
			}
		} else {
			// 如果是图片或信息流，查询图片素材
			ImageModelExample imageEx = new ImageModelExample();
			imageEx.createCriteria().andWidthEqualTo(width).andHeightEqualTo(height).andFormatIn(formatList);
			// 判断图片是否属于广告主
			List<ImageModel> imageModels = imageDao.selectByExample(imageEx);
			if (imageModels != null && !imageModels.isEmpty()) {
				images = new ArrayList<ImageModel>();
				for (ImageModel imageModel : imageModels) {
					String imageId = imageModel.getId();
					if (advertiserMaterialIds.contains(imageId)) {
						// 如果广告主下的图片id包含满足宽、高、规格要求的图片id，则将这个图片信息放到list中						
						ImageModel image = imageDao.selectByPrimaryKey(imageId);
						images.add(image);
					}
				}
			}
		}

		// 3.判读项目id/活动id是否为空
		Set<String> materialIds = new HashSet<String>();
		if (StringUtils.isEmpty(projectId) && StringUtils.isEmpty(campaignId)) {
			if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
				MediaBean mediaBean = null;
				if (videos != null && !videos.isEmpty()) {
					// 如果视频素材不为空
					for (VideoModel video : videos) {
						mediaBean = new MediaBean();
						mediaBean.setId(video.getId());
						mediaBean.setId(video.getPath());
						result.add(mediaBean);
					}
				}
			} else {
				MediaBean mediaBean = null;
				if (images != null && !images.isEmpty()) {
					// 如果图片素材不为空
					for (ImageModel image : images) {
						mediaBean = new MediaBean();
						mediaBean.setId(image.getId());
						mediaBean.setPath(image.getPath());
						result.add(mediaBean);
					}
				}
			}
		} else {
			// 4.如果项目id或活动id不为空，通过项目id/活动id查询图片、视频的ids集合
			if (!StringUtils.isEmpty(projectId) && StringUtils.isEmpty(campaignId)) {
				// 如果项目id不为空，活动id为空，根据项目id查询使用过的素材
				CampaignModelExample campaginEx = new CampaignModelExample();
				campaginEx.createCriteria().andProjectIdEqualTo(projectId);
				List<CampaignModel> campaigns = campaignDao.selectByExample(campaginEx);
				if (campaigns != null && !campaigns.isEmpty()) {
					for (CampaignModel campaign : campaigns) {
						if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
							Set<String> setMaterialIds = listVideoIdByCampaign(campaign.getId());
							if (setMaterialIds != null && !setMaterialIds.isEmpty()) {
								for (String materialId : setMaterialIds) {
									materialIds.add(materialId);
								}
							}
						} else {
							Set<String> setMaterialIds = listImageIdByCampaign(campaign.getId());
							if (setMaterialIds != null && !setMaterialIds.isEmpty()) {
								for (String materialId : setMaterialIds) {
									materialIds.add(materialId);
								}
							}
						}
					}
				}

			} else if (!StringUtils.isEmpty(campaignId)) {
				if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					// 如果项目id不为空，活动id不为，根据活动id查询使用过的素材
					materialIds = listVideoIdByCampaign(campaignId);
				} else {
					// 如果项目id不为空，活动id不为，根据活动id查询使用过的素材
					materialIds = listImageIdByCampaign(campaignId);
				}
			}
			// 5.当项目id/活动id不为空时根据类型取交集返回结果
			if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
				// 如果是视频
				MediaBean mediaBean = null;
				if (videos != null && !videos.isEmpty()) {
					// 如果视频素材不为空
					for (VideoModel video : videos) {
						if (materialIds.contains(video.getId())) {
							mediaBean = new MediaBean();
							mediaBean.setId(video.getId());
							mediaBean.setId(video.getPath());
							result.add(mediaBean);
						}
					}
				}
			} else {
				// 如果是图片或信息流
				MediaBean mediaBean = null;
				if (images != null && !images.isEmpty()) {
					for (ImageModel image : images) {
						if (materialIds.contains(image.getId())) {
							mediaBean = new MediaBean();
							mediaBean.setId(image.getId());
							mediaBean.setPath(image.getPath());
							result.add(mediaBean);
						}
					}
				}
			}
		}

		return result;
	}


	/**
	 * 根据活动id查询活动下的图片id的集合
	 *
	 * @param campaignId
	 * @return
	 * @throws Exception
	 */
	public Set<String> listImageIdByCampaign(String campaignId) throws Exception {
		Set<String> imageIds = new HashSet<String>();
		CreativeModelExample creativeEx = new CreativeModelExample();
		creativeEx.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeEx);
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				// 查询创意使用的素材
				String materialId = creative.getMaterialId();
				String type = creative.getType();
				if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
					// 图片
					ImageMaterialModel imageMaterial = imageMaterialDao.selectByPrimaryKey(materialId);
					// 图片素材的id
					String imageId = imageMaterial.getImageId();
					// 将图片的id作为查询素材的条件之一
					if (imageId != null && !imageId.isEmpty()) {
						imageIds.add(imageId);
					}
				} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					// 视频
					VideoMaterialModel videoMaterial = videoMaterialDao.selectByPrimaryKey(materialId);
					// 视频素材的id				
					String imageId = videoMaterial.getImageId();
					if (imageId != null && !imageId.isEmpty()) {
						imageIds.add(imageId);
					}
				} else {
					// 信息流
					InfoflowMaterialModel infoflowMaterial = infoMaterialDao.selectByPrimaryKey(materialId);
					// 图片素材的id
					String iconId = infoflowMaterial.getIconId();
					if (iconId != null && !iconId.isEmpty()) {
						imageIds.add(iconId);
					}
					String image1Id = infoflowMaterial.getImage1Id();
					if (image1Id != null && !image1Id.isEmpty()) {
						imageIds.add(image1Id);
					}
					String image2Id = infoflowMaterial.getImage2Id();
					if (image2Id != null && !image2Id.isEmpty()) {
						imageIds.add(image2Id);
					}
					String image3Id = infoflowMaterial.getImage3Id();
					if (image3Id != null && !image3Id.isEmpty()) {
						imageIds.add(image3Id);
					}
					String image4Id = infoflowMaterial.getImage4Id();
					if (image4Id != null && !image4Id.isEmpty()) {
						imageIds.add(image4Id);
					}
					String image5Id = infoflowMaterial.getImage5Id();
					if (image5Id != null && !image5Id.isEmpty()) {
						imageIds.add(image5Id);
					}
				}
			}
		}
		return imageIds;
	}

	/**
	 * 根据活动id查询活动下的图片id的集合
	 *
	 * @param campaignId 活动id
	 * @return
	 * @throws Exception
	 */
	public Set<String> listVideoIdByCampaign(String campaignId) throws Exception {
		Set<String> videoIds = null;
		CreativeModelExample creativeEx = new CreativeModelExample();
		creativeEx.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> creatives = creativeDao.selectByExample(creativeEx);
		if (creatives != null && !creatives.isEmpty()) {
			for (CreativeModel creative : creatives) {
				// 查询创意使用的素材
				String materialId = creative.getMaterialId();
				String type = creative.getType();
				if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					// 视频
					VideoMaterialModel videoMaterial = videoMaterialDao.selectByPrimaryKey(materialId);
					// 视频素材的id				
					String videoId = videoMaterial.getVideoId();
					if (videoId != null && !videoId.isEmpty()) {
						videoIds = new HashSet<String>();
						videoIds.add(videoId);
					}
				}
			}
		}
		return videoIds;
	}

	/**
	 * 根据创意查询创意审核
	 *
	 * @param creativeId
	 * @return
	 */
	public CreativeAuditModel getCreativeAuditModelByCreativeId(String creativeId) {
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> models = creativeAuditDao.selectByExample(example);
		if (models != null && !models.isEmpty()) {
			return models.get(0);
		}
		return null;
	}

	/**
	 * 获取素材信息
	 *
	 * @param creativeModel
	 * @return
	 */
	public void getMaterialInfoByCreativeModel(CreativeModel creativeModel, CreativeBean creativeBean) {
		String creativeType = creativeModel.getType();

		String[] materialPaths = null;
		String title = null;
		String description = null;
		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creativeModel.getMaterialId());
			if (imageMaterialModel != null) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
				if (imageModel != null) {
					String path = imageModel.getPath();
					materialPaths = new String[1];
					materialPaths[0] = path;
				}
			}
		} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
			VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creativeModel.getMaterialId());
			if (videoMaterialModel != null) {
				materialPaths = new String[2];
				VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
				if (videoModel != null) {
					String imageId = videoMaterialModel.getImageId();
					if (!StringUtils.isEmpty(imageId)) {
						ImageModel imageModel = imageDao.selectByPrimaryKey(imageId);
						materialPaths = new String[2];
						materialPaths[0] = videoModel.getPath();
						materialPaths[1] = imageModel.getPath();
					} else {
						materialPaths = new String[1];
						materialPaths[0] = videoModel.getPath();
					}
				}
			}
		} else if (CodeTableConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
			InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(creativeModel.getMaterialId());
			List<String> imgList = new ArrayList<String>();
			String iconId = infoflowModel.getIconId();
			String image1Id = infoflowModel.getImage1Id();
			String image2Id = infoflowModel.getImage2Id();
			String image3Id = infoflowModel.getImage3Id();
			String image4Id = infoflowModel.getImage4Id();
			String image5Id = infoflowModel.getImage5Id();
			if (!StringUtils.isEmpty(iconId)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(iconId);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			if (!StringUtils.isEmpty(image1Id)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(image1Id);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			if (!StringUtils.isEmpty(image2Id)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(image2Id);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			if (!StringUtils.isEmpty(image3Id)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(image3Id);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			if (!StringUtils.isEmpty(image4Id)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(image4Id);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			if (!StringUtils.isEmpty(image5Id)) {
				ImageModel imageModel = imageDao.selectByPrimaryKey(image5Id);
				if (imageModel != null) {
					imgList.add(imageModel.getPath());
				}
			}
			materialPaths = new String[imgList.size()];
			materialPaths = imgList.toArray(materialPaths);

			//
			title = infoflowModel.getTitle();
			description = infoflowModel.getDescription();
		} else {
			throw new IllegalArgumentException();
		}
		if (materialPaths == null) {
			materialPaths = new String[1];
		}
		creativeBean.setTitle(title);
		creativeBean.setDescription(description);
		creativeBean.setMaterialPaths(materialPaths);
	}

	/**
	 * 判断是否写创意id和创意信息到redis中：创意审核通过 && （创意开关打开 &&） 项目打开 && 活动打开 && 当前活动在投放日期内
	 *
	 * @param campaignId 活动id
	 * @param creativeId 创意id
	 * @return
	 * @throws Exception
	 */
	private boolean iswriteCreativeInfoAndId(String campaignId, String creativeId) throws Exception {
		// 查询创意对应的活动信息
		CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
		// 查询创意对应的项目信息
		ProjectModel project = projectDao.selectByPrimaryKey(campaign.getProjectId());
		// 查询创意的审核信息
		CreativeAuditModelExample auditExample = new CreativeAuditModelExample();
		auditExample.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> creativeAudit = creativeAuditDao.selectByExample(auditExample);
		if (creativeAudit != null && !creativeAudit.isEmpty()) {
			// 获取审核的状态
			String auditStatus = creativeAudit.get(0).getStatus();
			return StatusConstant.PROJECT_PROCEED.equals(project.getStatus())
					&& StatusConstant.CAMPAIGN_PROCEED.equals(campaign.getStatus())
					&& campaignService.isOnLaunchDate(campaignId)
					&& auditStatus.equals(StatusConstant.CREATIVE_AUDIT_SUCCESS);
		}
		return false;
	}

	/**
	 * 向redis中写入创意基本信息和单个创意id
	 *
	 * @param campaignId 活动id
	 * @param creativeId 创意id
	 * @throws Exception
	 */
	private void writeCreativeInfoAndIdToRedis(String campaignId, String creativeId) throws Exception {
		if (iswriteCreativeInfoAndId(campaignId, creativeId)) {
			// 创意审核通过 （&& 创意开关打开） && 项目打开 && 活动打开 && 当前活动在投放日期内
			// 将创意id写入redis的mapids
			launchService.writeOneCreativeId(campaignId, creativeId);
			//写入活动下的创意基本信息   dsp_mapid_*		
			if (!launchService.isHaveCreativeInfoInRedis(creativeId)) {
				launchService.writeCreativeInfo(campaignId);
			}
		}
	}

	/**
	 * 获取投放时间在当前时间的创意
	 * @return
	 */
	public List<CreativeModel> getCreativeOfCurrentPuttingTime(){
		List<CreativeModel> creativeModels = null;
		//获取投放时间在当前时间内的活动
		List<CampaignModel> campaignModels = campaignService.getCampaignOfCurrentPuttingTime();
		if(campaignModels != null && !campaignModels.isEmpty()) {
			List<String> campIds = new ArrayList<>();
			for (CampaignModel campaignModel : campaignModels) {
				campIds.add(campaignModel.getId());
			}
			CreativeModelExample creativeModelExample = new CreativeModelExample();
			creativeModelExample.createCriteria().andCampaignIdIn(campIds);
			creativeModels = creativeDao.selectByExample(creativeModelExample);
		}
		return creativeModels;
	}

}
