package com.pxene.pap.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import com.pxene.pap.domain.models.*;
import com.pxene.pap.repository.basic.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.common.RedisHelper;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.constant.PhrasesConstant;
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
import com.pxene.pap.domain.models.AppTmplModel;
import com.pxene.pap.domain.models.AppTmplModelExample;
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
	private AppTmplDao appTmplDao;
	
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
	private TmplService tmplService;

	@Autowired
	private AdxCostDao adxCostDao;


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
	
//	/**
//	 * 删除图片服务器上的图片素材
//	 * @param id
//	 * @throws Exception
//	 */
//	@Transactional
//	public void deleteImageMaterialById(String id) throws Exception{
//		if (!StringUtils.isEmpty(id)) {
//			ImageModel imageModel = imageDao.selectByPrimaryKey(id);
//			if (imageModel != null) {
//				String path = uploadDir + imageModel.getPath();
//				
//				// 删除图片服务器上的素材
//				//org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
//				doDeleteFile(path);
//			}
//		}
//	}
//	/**
//	 * 删除图片服务器上的视频素材
//	 * @param id
//	 * @throws Exception
//	 */
//	@Transactional
//	public void deleteVideoMaterialById(String id) throws Exception{
//		if (!StringUtils.isEmpty(id)) {
//			VideoModel videoModel = videoDao.selectByPrimaryKey(id);
//			if (videoModel != null) {
//				String path = uploadDir + videoModel.getPath();
//				
//				// 删除图片服务器上的素材
//				//org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
//				doDeleteFile(path);
//			}
//		}
//	}
	
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
		// FIXME : 开始投放不能删、redis中是否有数据--有数据不能删除；在开始时间内，或者该创意在redis中有数据不能删
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
			throw new IllegalArgumentException();
		}
		
		// FIXME : 改成in查 --- OK 
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(Arrays.asList(creativeIds));
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		if (creativeModels == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		
	    // FIXME : 开始投放不能删、redis中是否有数据--有数据不能删除；在开始时间内，或者该创意在redis中有数据不能删
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
		
		// FIXME : 删除创意审核信息 --- OK 
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
	@Transactional
	public void auditCreative(String id) throws Exception {
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		if (creative == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		String status = getCreativeAuditStatus(id);
		if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_WATING.equals(status)) {
			return;
		}
		
		// 查询adx列表，判断是哪个adx
		List<Map<String, String>> adxes = launchService.getAdxByCreative(creative);
		// 审核创意
		for (Map<String, String> adx : adxes) {
			String adxId = adx.get("adxId");
			if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
				momoAuditService.auditCreative(id);
			}
			if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
				inmobiAuditService.auditCreative(id);
			}
			if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
                autohomeAuditService.auditCreative(id);
            }
		}
	}

	/**
	 * 同步创意第三方审核结果
	 * @param id
	 * @throws Exception
	 */
	@Transactional
	public void synchronizeCreative(String id) throws Exception {
		CreativeModel creative = creativeDao.selectByPrimaryKey(id);
		if (creative == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		String status = getCreativeAuditStatus(id);
		if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(status) 
				|| StatusConstant.ADVERTISER_AUDIT_NOCHECK.equals(status)) {
			return;
		}
		
		// 查询adx列表
		List<Map<String, String>> adxes = launchService.getAdxByCreative(creative);
		//同步结果
		for (Map<String, String> adx : adxes) {
			CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
			creativeAuditExample.createCriteria().andCreativeIdEqualTo(id).andAdxIdEqualTo(adx.get("adxId"));
			List<CreativeAuditModel> audits = creativeAuditDao.selectByExample(creativeAuditExample);
			if (audits == null || audits.isEmpty()) {
				throw new ServerFailureException(PhrasesConstant.CREATIVE_AUDIT_NULL);
			} else {
				String adxId = adx.get("adxId");
				if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
					momoAuditService.synchronizeCreative(id);
				}
				if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
					inmobiAuditService.synchronizeCreative(id);
				}
				if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
				    autohomeAuditService.synchronizeCreative(id);
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
	@Transactional
	public List<BasicDataBean> listCreatives(String campaignId, String name, String type, Long startDate, Long endDate) throws Exception {
		List<BasicDataBean> result = new ArrayList<BasicDataBean>();
		CreativeModelExample example = new CreativeModelExample();
		
		// 按更新时间进行倒序排序
        example.setOrderByClause("update_time DESC");
	
        // FIXME : 不用检验campaignId，因为campaignId必填 
        if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		} else if (StringUtils.isEmpty(name)) {
			example.createCriteria().andCampaignIdEqualTo(campaignId);
		} else if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andCampaignIdEqualTo(campaignId).andNameLike("%" + name + "%");
		}
        
		List<CreativeModel> creatives = creativeDao.selectByExample(example);
		if (creatives != null && !creatives.isEmpty()) {
			CreativeBean base = null;
			ImageCreativeBean image = null;
			VideoCreativeBean video = null;
			InfoflowCreativeBean info = null;
			for (CreativeModel creative : creatives) {
//				String appId = getAppId(creative.getTmplId());
//				String appName = getAppName(appId);
				Map<String, String> appInfo = getAppInfo(creative);
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
							image.setPrice(creative.getPrice().floatValue());
							
							image.setImageId(creative.getMaterialId());
							image.setImagePath(imageModel.getPath());
							
							image.setAppId(appInfo.get("appIds"));  
							image.setAppName(appInfo.get("appNames")); 
							image.setEnable(creative.getEnable());
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
									image.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
								}
							}
							result.add(image);
						}
					}
				} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
					VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creative.getMaterialId());
					if (videoMaterialModel == null ) {
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
						video.setPrice(creative.getPrice().floatValue());
						
						video.setImageId(imageId);
						video.setImagePath(getImagePath(imageId));
						video.setVideoId(creative.getMaterialId());
						video.setVideoPath(videoModel.getPath());
						
						video.setAppId(appInfo.get("appIds"));  
						video.setAppName(appInfo.get("appNames")); 
						video.setEnable(creative.getEnable());
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
								video.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
							}
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
						info.setPrice(creative.getPrice().floatValue());
						
						info.setAppId(appInfo.get("appIds"));  
						info.setAppName(appInfo.get("appNames")); 
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
								info.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
							}
						}
						result.add(info);
					}
				} else {
					base = modelMapper.map(creative, CreativeBean.class);
					base.setAppId(appInfo.get("appIds"));  
					base.setAppName(appInfo.get("appNames")); 
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
					} else {
						throw new IllegalArgumentException();
					}
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
							//修正成本
							base.setAdxCost(dataBean.getAdxCost());
						}
					}
					base.setStatus(getCreativeAuditStatus(creative.getId()));
					base.setMaterialPaths(materialPaths);
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
	@Transactional
	public BasicDataBean getCreative(String id, Long startDate, Long endDate) throws Exception {
		// FIXME : 优化代码，如可以在每个类型中直接返回image/video/info/base等
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
		Map<String, String> appInfo = getAppInfo(creative);
		String appId = appInfo.get("appIds");
		String appName = appInfo.get("appNames");
		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			//如果创意类型是图片
			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			// FIXME : imageMaterialModel是否为空  --- OK 
			if (imageMaterialModel == null ) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());			
			if (imageModel != null) {
				image = new ImageCreativeBean();				
				image.setId(creative.getId());       //创意id
				image.setType(type);                 //创意类型
				image.setCampaignId(campaignId);     //活动id
				
				image.setStatus(getCreativeAuditStatus(creative.getId())); //创意审核状态
				image.setPrice(creative.getPrice().floatValue());          //创意价格
				image.setTmplId(creative.getTmplId());                     //模板ID
				image.setAppId(appId);                           //appId
				image.setAppName(appName);                       //app名称
				image.setEnable(creative.getEnable());           //创意的状态
				
				image.setImageId(creative.getMaterialId());   //图片id
				image.setImagePath(imageModel.getPath());     //图片路径
				
				
				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						image.setImpressionAmount(dataBean.getImpressionAmount()); //展现数
						image.setClickAmount(dataBean.getClickAmount());           //点击数
						image.setTotalCost(dataBean.getTotalCost());               //总花费
						image.setJumpAmount(dataBean.getJumpAmount());             //二跳数
						image.setImpressionCost(dataBean.getImpressionCost());     //展现成本
						image.setClickCost(dataBean.getClickCost());               //点击成本
						image.setClickRate(dataBean.getClickRate());               //点击率
						image.setJumpCost(dataBean.getJumpCost());                 //二跳成本
						image.setAdxCost(dataBean.getAdxCost());		//修正成本
					}
				}
				bean = image;
			}
		} else if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			// 如果创意类型是视频
			VideoMaterialModel videoMaterialModel = videoMaterialDao.selectByPrimaryKey(creative.getMaterialId());
			if (videoMaterialModel == null ) {
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
				video.setPrice(creative.getPrice().floatValue());          //创意价格
				video.setTmplId(creative.getTmplId());                     //模板Id
				video.setAppId(appId);                                     //AppID
				video.setAppName(appName);                                 //app名称
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
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						video.setImpressionAmount(dataBean.getImpressionAmount());    //展现数
						video.setClickAmount(dataBean.getClickAmount());              //点击数
						video.setTotalCost(dataBean.getTotalCost());                  //总花费
						video.setJumpAmount(dataBean.getJumpAmount());                //二跳数
						video.setImpressionCost(dataBean.getImpressionCost());        //展现成本
						video.setClickCost(dataBean.getClickCost());                  //点击成本
						video.setClickRate(dataBean.getClickRate());                  //点击率
						video.setJumpCost(dataBean.getJumpCost());                    //二跳成本
						video.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
					}
				}
				bean = video;
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
				info.setPrice(creative.getPrice().floatValue());           //创意价格
				info.setTmplId(creative.getTmplId());                      //模板Id
				info.setAppId(appId);                                      //AppID
				info.setAppName(appName);                                  //app名称
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
				//查询投放数据
				if (startDate != null && endDate != null) {
					String creativeId = creative.getId();
					List<String> idList = new ArrayList<String>();
					idList.add(creativeId);
					BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
					if (dataBean != null) {
						info.setImpressionAmount(dataBean.getImpressionAmount());     //展现数
						info.setClickAmount(dataBean.getClickAmount());               //点击数
						info.setTotalCost(dataBean.getTotalCost());                   //总花费
						info.setJumpAmount(dataBean.getJumpAmount());                 //二跳数
						info.setImpressionCost(dataBean.getImpressionCost());         //展现成本
						info.setClickCost(dataBean.getClickCost());                   //点击成本
						info.setClickRate(dataBean.getClickRate());                   //点击率
						info.setJumpCost(dataBean.getJumpCost());                     //二跳成本
						info.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
					}
				}
				bean = info;
			}
		} else {
			//否则
			base = modelMapper.map(creative, CreativeBean.class);
			base.setAppId(appId);
			base.setAppName(appName);
			//查询投放数据
			if (startDate != null && endDate != null) {
				String creativeId = creative.getId();
				List<String> idList = new ArrayList<String>();
				idList.add(creativeId);
				BasicDataBean dataBean = getCreativeDatas(idList, startDate, endDate);
				if (dataBean != null) {
					base.setImpressionAmount(dataBean.getImpressionAmount());        //展现数
					base.setClickAmount(dataBean.getClickAmount());                  //点击数
					base.setTotalCost(dataBean.getTotalCost());                      //总花费
					base.setJumpAmount(dataBean.getJumpAmount());                    //二跳数
					base.setImpressionCost(dataBean.getImpressionCost());            //展现成本
					base.setClickCost(dataBean.getClickCost());                      //点击成本
					base.setClickRate(dataBean.getClickRate());                      //点击率
					base.setJumpCost(dataBean.getJumpCost());                        //二跳成本
					base.setAdxCost(dataBean.getAdxCost()); 		  //修正成本
				}
			}
			base.setStatus(getCreativeAuditStatus(creative.getId()));                //创意的审核状态
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
//	public List<MaterialListBean> listCreativeMaterials(String campaignId, long startDate, long endDate) throws Exception {
//		List<MaterialListBean> result = new ArrayList<MaterialListBean>();
//		
//		CreativeModelExample cmExample = new CreativeModelExample();
//		cmExample.createCriteria().andCampaignIdEqualTo(campaignId);
//		List<CreativeModel> cmList = creativeDao.selectByExample(cmExample);
//		if (cmList == null || cmList.isEmpty()) {
//			throw new ResourceNotFoundException();
//		}
//		MaterialListBean bean;
//		for (CreativeModel cmModel : cmList) {
//			bean = new MaterialListBean();
//			String mapId = cmModel.getId();
//			bean.setMapId(mapId);
//			String creativeType = cmModel.getType();
//			String tmplId = cmModel.getTmplId();
//			String materialId = cmModel.getMaterialId();
//			bean.setPrice(cmModel.getPrice().floatValue());
//			bean.setId(materialId);
//			bean.setType(creativeType);
//			//根据素材类型不同，获取不同的素材路径
//			if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(creativeType)) {
//				ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(materialId);
//				ImageModel imageModel = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
//				if (imageModel != null) {
//					String path = imageModel.getPath();
//					bean.setPath(path);
//				}
//			} else if (StatusConstant.CREATIVE_TYPE_VIDEO.equals(creativeType)) {
//				VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(materialId);
//				VideoModel videoModel = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
//				if (videoModel != null) {
//					String path = videoModel.getPath();
//					bean.setPath(path);
//				}
//			} else if (StatusConstant.CREATIVE_TYPE_INFOFLOW.equals(creativeType)) {
//				InfoflowMaterialModel infoflowModel = infoflowDao.selectByPrimaryKey(materialId);
//				if (!StringUtils.isEmpty(infoflowModel.getIconId())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getIconId());
//					if (imageModel != null) {
//						String iconPath = imageModel.getPath();
//						bean.setIconPath(iconPath);
//					}
//				}
//				if (!StringUtils.isEmpty(infoflowModel.getImage1Id())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage1Id());
//					if (imageModel != null) {
//						String image1Path = imageModel.getPath();
//						bean.setImage1Path(image1Path);
//					}
//				}
//				if (!StringUtils.isEmpty(infoflowModel.getImage2Id())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage2Id());
//					if (imageModel != null) {
//						String image2Path = imageModel.getPath();
//						bean.setImage2Path(image2Path);
//					}
//				}
//				if (!StringUtils.isEmpty(infoflowModel.getImage3Id())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage3Id());
//					if (imageModel != null) {
//						String image3Path = imageModel.getPath();
//						bean.setImage3Path(image3Path);
//					}
//				}
//				if (!StringUtils.isEmpty(infoflowModel.getImage4Id())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage4Id());
//					if (imageModel != null) {
//						String image4Path = imageModel.getPath();
//						bean.setImage4Path(image4Path);
//					}
//				}
//				if (!StringUtils.isEmpty(infoflowModel.getImage5Id())) {
//					ImageModel imageModel = imageDao.selectByPrimaryKey(infoflowModel.getImage5Id());
//					if (imageModel != null) {
//						String image5Path = imageModel.getPath();
//						bean.setImage5Path(image5Path);
//					}
//				}
//			}
//			
//			
//			//查询app
//			List<AppModel> appModels = getAppForMaterial(tmplId);
//			List<App> appList = new ArrayList<MaterialListBean.App>();
//			App app = null;
//			if (appModels!=null && !appModels.isEmpty()) {
//				for (AppModel appModel :appModels) {
//					app = new App();
//					app.setAppId(appModel.getAppId());
//					app.setAppname(appModel.getAppName());
//					app.setPkgName(appModel.getPkgName());
//					app.setId(appModel.getId());
//					appList.add(app);
//				}
//			}
//			if (!appList.isEmpty()) {
//				App[] apps = new App[appList.size()];
//				for (int i=0;i< appList.size();i++) {
//					apps[i] = appList.get(i);
//				}
//				bean.setApps(apps);
//			}
//			
//			//查询素材名称
//			String materialName = getMaterialName(materialId, creativeType);
//			bean.setName(materialName);
//			List<String> list = new ArrayList<String>();
//			list.add(bean.getId());
//			//查询点击、展现等数据
////			CreativeDataBean dataBean = creativeAllDataService.listCreativeData(list, startDate, endDate);
////			Long impressionAmount = dataBean.getImpressionAmount();
////			Long clickAmount = dataBean.getClickAmount();
////			Float cost = dataBean.getCost();
////			Long jumpAmount = dataBean.getJumpAmount();
//			result.add(bean);
//		}
//		
//		return result;
//	}
	
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
		CreativeAuditModelExample example = new CreativeAuditModelExample();
		example.createCriteria().andCreativeIdEqualTo(creativeId);
		List<CreativeAuditModel> models = creativeAuditDao.selectByExample(example);
		String status = StatusConstant.CREATIVE_AUDIT_NOCHECK;
		boolean successFlag = false;
		for (CreativeAuditModel model : models) {
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(model.getStatus())) {
				status = StatusConstant.CREATIVE_AUDIT_SUCCESS;
				successFlag  = true;
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
	 * 获取App信息
	 * @param tmplId
	 * @return 
	 * @throws Exception
	 */
//	private List<AppModel> getAppForMaterial(String tmplId) throws Exception {
//		AppTmplModelExample atExample = new AppTmplModelExample();
//		atExample.createCriteria().andTmplIdEqualTo(tmplId);
//		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(atExample);
//		List<String> appIdList = new ArrayList<String>();
//		if (appTmpls != null && !appTmpls.isEmpty()) {
//			for (AppTmplModel appTmpl : appTmpls) {
//				String appId = appTmpl.getAppId();
//				if (!appIdList.contains(appId)) {
//					appIdList.add(appId);
//				}
//			}
//		}
//		List<AppModel> appModels = null;
//		if (!appIdList.isEmpty()) {
//			AppModelExample example = new AppModelExample();
//			example.createCriteria().andIdIn(appIdList);
//			appModels = appDao.selectByExample(example);
//		}
//		
//		return appModels;
//	}
	
	/**
	 * 根据素材Id和素材type查询素材名称
	 * @param materialId
	 * @param type
	 * @return
	 * @throws Exception
	 */
//	private String getMaterialName(String materialId, String type) throws Exception {
//		if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
//			ImageMaterialModel imageMaterialModel = imageMaterialDao.selectByPrimaryKey(materialId);
//			ImageModel model = imageDao.selectByPrimaryKey(imageMaterialModel.getImageId());
//			if (model != null) {
////				String sizeId = model.getSizeId();
////				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
////				if (sizeModel != null) {
//					Integer width = model.getWidth();
//					Integer height = model.getHeight();
//					return width + "x" + height;
////				}
//			}
//		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
//			
//			VideoMaterialModel videoMaterialModel = videoeMaterialDao.selectByPrimaryKey(materialId);
//			VideoModel model = videoDao.selectByPrimaryKey(videoMaterialModel.getVideoId());
//			if (model != null) {
////				String sizeId = model.getSizeId();
////				SizeModel sizeModel = sizeDao.selectByPrimaryKey(sizeId);
////				if (sizeModel != null) {
//					Integer width = model.getWidth();
//					Integer height = model.getHeight();
//					return width + "x" + height;
////				}
//			}
//		} else if (StatusConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
//			InfoflowMaterialModel model = infoflowDao.selectByPrimaryKey(materialId);
//			if (model != null) {
//				return model.getTitle();
//			}
//		}
//		
//		return null;
//	}
	
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (String creativeId : creativeIds) {
			Map<String, String> map = redisHelper3.hget("creativeDataDay_" + creativeId);//获取map集合
			Set<String> hkeys = redisHelper3.hkeys("creativeDataDay_" + creativeId);//获取所有key
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
								    float totalCost = bean.getTotalCost() + (Float.parseFloat(value) / 100);  //将Redis中取出的价格（分）转换成价格（元）
									bean.setTotalCost(totalCost);
								}
							}
						}else if(hkey.indexOf(day + "_adx_") > -1){
							String value = map.get(hkey);
							//判断值是否存在
							if (!StringUtils.isEmpty(value)) {
								String[] temp = hkey.split(day+"_adx_");
								if(temp.length==2){
									String[] arry2 = temp[1].split("@");
									if(arry2.length ==2 && arry2[1].equals("e")){
										Date date =sdf.parse(day);
										AdxCostModelExample adxCostModelExample = new AdxCostModelExample();
										adxCostModelExample.createCriteria().andAdxIdEqualTo(arry2[0]).andStartDateLessThan(date).andEndDateGreaterThan(date);
										List<AdxCostModel> adxCostModels = adxCostDao.selectByExample(adxCostModelExample);
										if(adxCostModels!=null && adxCostModels.size()>0){
											float ratio = adxCostModels.get(0).getRatio();
											float adxCost = bean.getAdxCost()+(Float.parseFloat(value)* ratio / 100);
											bean.setAdxCost(adxCost);
										}
									}
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
			Map<String, String> map = redisHelper3.hget("creativeDataHour_" + creativeId);//获取map集合
			Set<String> hkeys = redisHelper3.hkeys("creativeDataHour_" + creativeId);//获取所有key
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
									float totalCost = bean.getTotalCost() + (Float.parseFloat(value) / 100);  //将Redis中取出的价格（分）转换成价格（元）
                                    bean.setTotalCost(totalCost);
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
	
	/**
     * 根据配置文件中的设置，来绝定上传文件至本地文件服务器或远程文件服务器
     * @param env   SpringBoot Environment配置
     * @param file  multipart/form-data对象
     * @return
	 * @throws IOException 
     */
    private String doUpload(String uploadDir, String fileName, MultipartFile file) throws IOException
    {
        if ("local".equalsIgnoreCase(uploadMode))
        {
            return FileUtils.uploadFileToLocal(uploadDir, fileName, file);
        }
        else
        {
            return FileUtils.uploadFileToRemote(host, port, username, password, uploadDir, fileName, file);
        }
    }
	
//	private void doDeleteFile(String path) throws IOException
//	{
//	    if ("local".equalsIgnoreCase(uploadDir))
//        {
//            org.apache.commons.io.FileUtils.deleteQuietly(new File(path));
//        }
//        else
//        {
//            scpUtils.delete(path);
//        }
//	}
	
	/**
	 * 修改已到过期时间的创意审核状态为“已过期”————————定时器
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
	
//	/**
//	 * 获取app的ID
//	 * @param tmplId 模板id
//	 * @return
//	 */
//	public String getAppId(String tmplId) {
//		AppTmplModelExample apptmplExample = new AppTmplModelExample();
//		apptmplExample.createCriteria().andTmplIdEqualTo(tmplId);
//		List<AppTmplModel> appTmpl = appTmplDao.selectByExample(apptmplExample);
//		String appId = appTmpl.get(0).getAppId();
//		return appId;
//	}
//	/**
//	 * 获取app的名称
//	 * @param appId 
//	 * @return
//	 */
//	public String getAppName(String appId) {		
//		AppModel app = appDao.selectByPrimaryKey(appId);
//		String appName = app.getAppName();
//		return appName;
//	}
	
	private Map<String, String> getAppInfo(CreativeModel creative) throws Exception {
		String campaignId = creative.getCampaignId();
		String tmplId = creative.getTmplId();
		
		//获取活动下的APPId
        List<String> appIdList = tmplService.getAppidByCampaignId(campaignId);
		
		Map<String, String> result = new HashMap<String, String>();
		String appIds = "";
		String appNames = "";
		
		AppTmplModelExample appTmplExample = new AppTmplModelExample();
		appTmplExample.createCriteria().andTmplIdEqualTo(tmplId);
		List<AppTmplModel> appTmpls = appTmplDao.selectByExample(appTmplExample);
		for (AppTmplModel tmpl : appTmpls) {
			String appId = tmpl.getAppId();
			if (appIdList.contains(appId)) {
				AppModel app = appDao.selectByPrimaryKey(appId);
				appIds = appIds + appId + ",";
				appNames = appNames + app.getAppName() + ",";
			}
		}
		if (appIds.length() > 1 && appNames.length() > 1) {
			result.put("appIds", appIds.substring(0, appIds.length()-1));
			result.put("appNames", appNames.substring(0, appNames.length()-1));
		}
		return result;
	}
	
	/**
	 * 修改创意
	 * @param id
	 * @param imageBean
	 * @throws Exception   
	 */
	@Transactional
	public void updateCreative(String id,CreativeBean creativeBean) throws Exception{
		CreativeModel creativeModel = creativeDao.selectByPrimaryKey(id);
		if (creativeModel == null) {
			// 如果没有该对象不能修改创意
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		// 修改创意
		String type = creativeModel.getType(); //创意类型
		String materialId = creativeModel.getMaterialId(); //素材ID
		// 图片   
		if (CodeTableConstant.CREATIVE_TYPE_IMAGE.equals(type)) {
			ImageMaterialModel imageMeterial = imageMaterialDao.selectByPrimaryKey(materialId);
			if (imageMeterial == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);			
			}
			ImageCreativeBean imageBean = (ImageCreativeBean)creativeBean;
			creativeModel = modelMapper.map(imageBean, CreativeModel.class);
			ImageMaterialModel imageMaterialModel = modelMapper.map(imageBean, ImageMaterialModel.class);
			imageMaterialModel.setId(materialId);
			imageMaterialDao.updateByPrimaryKeySelective(imageMaterialModel);
			launchService.writeImgCreativeInfo(creativeModel);
		}
		// 视频 
		if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			VideoMaterialModel videoMaterial = videoMaterialDao.selectByPrimaryKey(materialId);
			if (videoMaterial == null) {
				throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
			}
			VideoCreativeBean videoBean = (VideoCreativeBean)creativeBean;
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
			InfoflowCreativeBean infoBean = (InfoflowCreativeBean)creativeBean;
			creativeModel = modelMapper.map(infoBean, CreativeModel.class);
			InfoflowMaterialModel infoflowMaterialModel = modelMapper.map(infoBean, InfoflowMaterialModel.class);
			// 放入ID，用于更新关联关系表中数据
			infoflowMaterialModel.setId(materialId);
			// 更新信息流
			infoMaterialDao.updateByPrimaryKeySelective(infoflowMaterialModel);
			launchService.writeInfoflowCreativeInfo(creativeModel);
		}
		// 放入ID，用于更新关联关系表中数据
		creativeModel.setId(id);
		// 更新创意表信息
		creativeDao.updateByPrimaryKeySelective(creativeModel);
	}
	
	/**
	 * 批量同步创意
	 * @param creativeIds 创意ids
	 * @throws Exception
	 */
	@Transactional
	public void synchronizeCreatives(String[] creativeIds) throws Exception {
		if (creativeIds.length == 0) {
			throw new IllegalArgumentException();
		}
		// 根据创意id列表查询创意信息
		List<String> creativeIdsList = Arrays.asList(creativeIds);
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(creativeIdsList);
		// 判断创意是否为空
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		// FIXME ： 同步的创意应该是审核中  --- OK 
		// FIXME : 判断查出的个数与传来的creativeIds的个数是否相同 --- OK
		if (creativeModels == null || creativeModels.isEmpty() || creativeIds.length > creativeModels.size()) {
			throw new ResourceNotFoundException();
		}
		for (CreativeModel creative : creativeModels) {
			String creativeId = creative.getId();
			String status = getCreativeAuditStatus(creativeId);
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(status) 
					|| StatusConstant.ADVERTISER_AUDIT_NOCHECK.equals(status)) {
				continue;
			}
			// 查询adx列表，一个创意可以由多个ADX审核  
			List<Map<String,String>> adxes = launchService.getAdxByCreative(creative);
			
			// 同步审核结果
			for (Map<String,String> adx : adxes) {
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
				}
			}
			// 如果审核通过，则将创意id写入门到redis的mapids中
			launchService.writeOneCreativeId(creative.getCampaignId(), creativeId);
		}
	}
	
	/**
	 * 批量审核创意
	 * @param creativeIds 创意ids
	 * @throws Exception
	 */
	@Transactional
	public void auditCreatives(String[] creativeIds) throws Exception {
		// 根据创意id列表查询创意信息
		List<String> creativeIdsList = Arrays.asList(creativeIds);
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andIdIn(creativeIdsList);
		// FIXME : 审核的创意是只有未审核和审核未通过
		// 判断创意是否为空
		List<CreativeModel> creativeModels = creativeDao.selectByExample(creativeExample);
		if (creativeModels == null || creativeModels.isEmpty() || creativeIds.length > creativeModels.size()) {
			// 如果创意信息为空
			throw new ResourceNotFoundException();
		}							
		
		// 审核创意
		for (CreativeModel creative : creativeModels) {
			// FIXME : 不需要再查询创意实体CreativeModel --- OK
			// 创意id
			String creativeId = creative.getId();
			String status = getCreativeAuditStatus(creativeId);
			if (StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_WATING.equals(status)) {
				continue;
			}
			List<Map<String,String>> adxes = launchService.getAdxByCreative(creative);
			//根据不同的ADX到不同的广告审核平台审核
			for (Map<String,String> adx : adxes) {
				// 获取ADX的Id，根据ADXID判断属于哪个ADX
				String adxId = adx.get("adxId");
				// 审核：未审核和审核未通过的创意
				// 1.审核的状态
				List<String> statusList = new ArrayList<String>();
				statusList.add(StatusConstant.CREATIVE_AUDIT_NOCHECK);
				statusList.add(StatusConstant.CREATIVE_AUDIT_FAILURE);
				// 2.查询审核信息
				CreativeAuditModelExample creativeAuditExample = new CreativeAuditModelExample();
				creativeAuditExample.createCriteria().andCreativeIdEqualTo(creativeId).andAdxIdEqualTo(adxId).andStatusIn(statusList);
				List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditExample);
				if (creativeAudits == null || creativeAudits.isEmpty()) {
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
				} else {
					// 如果创意审核信息不为空

					// 如果审核未通过的创意审核信息，则提交对应的平台进行审核
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
				}
			}
		}
	}
	
	/**
	 * 编辑创意的状态，启动/暂停创意
	 * @param creativeId
	 * @param map
	 * @throws Exception
	 */
	@Transactional
	public void updateCreativeStatus(String creativeId,Map<String,String> map) throws Exception {
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
			// 如果在投放时间并且创意已经审核通过，则将创意id写入门redis的dsp_groupid_mapids_中
			// 1.查询创意审核信息
			CreativeAuditModelExample creativeAuditEx = new CreativeAuditModelExample();
			creativeAuditEx.createCriteria().andCreativeIdEqualTo(creativeId);
			List<CreativeAuditModel> creativeAudits = creativeAuditDao.selectByExample(creativeAuditEx);
			if (creativeAudits != null && !creativeAudits.isEmpty()) {
				// 如果创意审核信息存在
				// 2.判断该创意是否审核通过
				for (CreativeAuditModel creativeAudit : creativeAudits) {
					if (campaignService.isOnLaunchDate(campaignId) && StatusConstant.CREATIVE_AUDIT_SUCCESS.equals(creativeAudit.getStatus())) {
						// FIXME : 单独写入创意ID --- OK
						launchService.writeOneCreativeId(campaignId, creativeId);
					}
				}
				
			}			
		}
	}
	
	/**
	 * 查询导入的素材
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<MediaBean> listCreativeMaterials(Integer width,Integer height,String type,String[] formats,String projectId,String campaignId) throws Exception {
		ImageModelExample imageEx = null;
		List<ImageModel> images = null;
		VideoModelExample videoEx = null;
		List<VideoModel> videos = null;
		List<MediaBean> result = new ArrayList<MediaBean>();
		// 转换类型
		List<String> formatList = Arrays.asList(formats);			
		
		// 1.满足宽、高、规格要求的图片/视频集合
		if (CodeTableConstant.CREATIVE_TYPE_VIDEO.equals(type)) {
			// 如果是视频，查询视频素材
			videoEx = new VideoModelExample();
			videoEx.createCriteria().andWidthEqualTo(width).andHeightEqualTo(height).andFormatIn(formatList);
			videos = new ArrayList<VideoModel>();
			videos = videoDao.selectByExample(videoEx);
		} else {
			// 如果是图片或信息流，查询图片素材
			imageEx = new ImageModelExample();
			imageEx.createCriteria().andWidthEqualTo(width).andHeightEqualTo(height).andFormatIn(formatList);
			images = new ArrayList<ImageModel>();
			images = imageDao.selectByExample(imageEx);
		}
		
		// 2.判读项目id/活动id是否为空
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
			// 3.如果项目id或活动id不为空，通过项目id/活动id查询图片、视频的ids集合
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
			// 4.当项目id/活动id不为空时根据类型取交集返回结果
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

}
