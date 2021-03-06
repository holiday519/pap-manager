package com.pxene.pap.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.util.StringUtil;
import com.pxene.pap.common.FileUtils;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.ConfKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.beans.AdvertiserBean.Audit;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdvertiserModelExample;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.domain.models.ProjectModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.IndustryDao;
import com.pxene.pap.repository.basic.KpiDao;
import com.pxene.pap.repository.basic.ProjectDao;

@Service
public class AdvertiserService extends BaseService
{
    @Autowired
    private AdvertiserDao advertiserDao;
    @Autowired
    private CreativeService creativeService;
    @Autowired
    private AdxDao adxDao;
    @Autowired
    private AdvertiserAuditDao advertiserAuditDao;
    @Autowired
    private DataService dataService;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private CampaignDao campaignDao;
	@Autowired
	private CreativeDao creativeDao;
    @Autowired
    private IndustryDao industryDao;
    @Autowired
    private KpiDao kpiDao;
    
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
    
    private static final String TEMP_DIR = "temp/";
    
    private static final String FORMAL_DIR = "formal/";
    
    private String host;
    
    private int port;
    
    private String username;
    
    private String password;
    
    private String uploadMode;
    
    private String uploadDir;
    
    @Autowired
    public AdvertiserService(Environment env)
    {
    	uploadMode = env.getProperty(ConfKeyConstant.FILESERVER_MODE, "local");
        
        if ("local".equals(uploadMode))
        {
        	uploadDir = env.getProperty(ConfKeyConstant.FILESERVER_LOCAL_UPLOAD_DIR);
        } 
        else
        {
        	host = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_HOST);
            port = Integer.parseInt(env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_PORT, "22"));
            username = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_USERNAME);
            password = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_PASSWORD);
            uploadDir = env.getProperty(ConfKeyConstant.FILESERVER_REMOTE_UPLOAD_DIR);
        }
    }
    
    /**
     * 创建广告主
     * @param bean
     * @throws Exception
     */
    @Transactional
    public void createAdvertiser(AdvertiserBean bean) throws Exception
    {
		// 验证名称重复
		AdvertiserModelExample example = new AdvertiserModelExample();
		example.createCriteria().andNameEqualTo(bean.getName());
		List<AdvertiserModel> advertisers = advertiserDao.selectByExample(example);
		if (advertisers != null && !advertisers.isEmpty()) {
			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		
		String[] adxIds = bean.getAdxIds();
		if (adxIds.length == 0) {
			throw new IllegalArgumentException(PhrasesConstant.ADVERTISER_NOTNULL_ADX);
		}
		
		// 先将临时目录中的图片拷贝到正式目录，并将bean中路径替换为正式目录
		copyTempToFormal(bean);
		// 将path替换成正式目录
		AdvertiserModel model = modelMapper.map(bean, AdvertiserModel.class);
		
		model.setId(UUIDGenerator.getUUID());
		advertiserDao.insertSelective(model);

		// 将DAO创建的新对象复制回传输对象中
		BeanUtils.copyProperties(model, bean);

		// 创建广告主时向广告主审核表添加信息，审核平台列表中有几个adx则向广告主审核表中加入几条数据
		// 查询ADX列表
		AdxModelExample adxExample = new AdxModelExample();
		adxExample.createCriteria().andIdIn(Arrays.asList(adxIds));
		List<AdxModel> adxes = adxDao.selectByExample(adxExample);
		for (AdxModel adx : adxes) {
			// 1.如果广告主审核信息为空，则向广告主审核表插入数据
			AdvertiserAuditModel advertiserAudit = new AdvertiserAuditModel();
			advertiserAudit.setId(UUIDGenerator.getUUID());
			advertiserAudit.setAdvertiserId(bean.getId());
			advertiserAudit.setAdxId(adx.getId());
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_NOCHECK);
			advertiserAudit.setEnable(StatusConstant.ADVERTISER_ADX_DISABLE);
			// 2.向广告主审核表插入数据
			advertiserAuditDao.insertSelective(advertiserAudit);
		}
    }

    /**
     * 根据id删除广告主
     * @param id
     * @throws Exception
     */
    @Transactional
    public void deleteAdvertiser(String id) throws Exception
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
        }
        
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(id);
        
        List<ProjectModel> projects = projectDao.selectByExample(example);
        
        // 查看欲操作的广告主名下是否还有创建的项目，如果有，则不可以删除
        if (projects != null && !projects.isEmpty())
        {
            throw new IllegalStatusException(PhrasesConstant.ADVERVISER_HAVE_PROJECT);
        }
        
        // 删除广告主下的资质图片
    	removeImages(id);
    	
		// 删除广告主审核表信息，一个广告主可以有多条审核信息都删除
		AdvertiserAuditModelExample auditExample = new AdvertiserAuditModelExample();
		auditExample.createCriteria().andAdvertiserIdEqualTo(advertiserInDB.getId());
		List<AdvertiserAuditModel> auditModels = advertiserAuditDao.selectByExample(auditExample);
		for (AdvertiserAuditModel auditModel : auditModels) {
			advertiserAuditDao.deleteByPrimaryKey(auditModel.getId());
		}
        
        advertiserDao.deleteByPrimaryKey(id);
    }
    
    /**
     * 批量删除广告主
     * @param ids
     * @throws Exception
     */
    @Transactional
    public void deleteAdvertisers(String[] ids) throws Exception
    {
    	if (ids.length == 0) {
    		throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
    	// 操作前先查询一次数据库，判断指定的资源是否存在
    	AdvertiserModelExample advertiserModelExample = new AdvertiserModelExample();
    	advertiserModelExample.createCriteria().andIdIn(Arrays.asList(ids));
    	List<AdvertiserModel> advertiserInDB = advertiserDao.selectByExample(advertiserModelExample);
    	if (advertiserInDB == null || advertiserInDB.size() < ids.length)
    	{
    		throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
    	}
    	
		for (String id : ids) {
			ProjectModelExample projectModelExample = new ProjectModelExample();
			projectModelExample.createCriteria().andAdvertiserIdEqualTo(id);
			
			List<ProjectModel> projects = projectDao.selectByExample(projectModelExample);
			
			// 查看欲操作的广告主名下是否还有创建的项目，如果有，则不可以删除
			if (projects != null && !projects.isEmpty())
			{
				throw new IllegalStatusException(PhrasesConstant.ADVERVISER_HAVE_PROJECT);
			}
		}
		
		// 删除广告主审核表信息，一个广告主可以有多条审核信息都删除 			
		// 1.查询指定的多个广告主ID的审核信息是否存在
		AdvertiserAuditModelExample auditExample = new AdvertiserAuditModelExample();
		auditExample.createCriteria().andAdvertiserIdIn(Arrays.asList(ids));
		// 2.删除广告主审核信息
		advertiserAuditDao.deleteByExample(auditExample);
		
		advertiserDao.deleteByExample(advertiserModelExample);
    }

//    @Transactional
//    public void patchUpdateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
//    {
//        // 更新操作要求绑定到RequestBody中的资源ID必须为空
//        if (!StringUtils.isEmpty(advertiserBean.getId()))
//        {
//            throw new IllegalArgumentException();
//        }
//        
//        // 操作前先查询一次数据库，判断指定的资源是否存在
//        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
//        if (advertiserInDB == null)
//        {
//            throw new NotFoundException();
//        }
//        
//        // 将传输对象映射成数据库Model
//        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
//        
//        AdvertiserModelExample example = new AdvertiserModelExample();
//        Criteria criteria = example.createCriteria();
//        criteria.andIdEqualTo(id);
//        
//        try
//        {
//            // 拷贝临时文件至正式目录中
//            copyTempToFormal(advertiserModel);
//            
//            advertiserDao.updateByExampleSelective(advertiserModel, example);
//            // 将DAO编辑后的新对象复制回传输对象中
//            BeanUtils.copyProperties(advertiserDao.selectByPrimaryKey(id), advertiserBean);
//        }
//        catch (DuplicateKeyException exception)
//        {
//            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
//            throw new DuplicateEntityException();
//        }
//    }

    /**
     * 编辑更新广告主
     * @param id
     * @param bean
     * @throws Exception
     */
    @Transactional
    public void updateAdvertiser(String id, AdvertiserBean bean) throws Exception
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null) {
            throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
        } else {
        	String nameInDB = advertiserInDB.getName();
        	String name = bean.getName();
        	if (!nameInDB.equals(name)) {
        		// 验证名称重复，排除自己使得同一广告主可用字母大小写不同的同一名称
            	AdvertiserModelExample example = new AdvertiserModelExample();
            	example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
        		List<AdvertiserModel> advertisers = advertiserDao.selectByExample(example);
        		if (advertisers != null && !advertisers.isEmpty()) {
        			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
        		}
        	}
        }
        
        List<String> newIds = Arrays.asList(bean.getAdxIds());
        if (newIds.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.ADVERTISER_NOTNULL_ADX);
		}
        
        copyTempToFormal(bean);
        // 将传输对象映射成数据库Model
        AdvertiserModel model = modelMapper.map(bean, AdvertiserModel.class);
        model.setId(id);
        
        advertiserDao.updateByPrimaryKey(model);
        
        AdvertiserAuditModelExample auditEx = new AdvertiserAuditModelExample();
        auditEx.createCriteria().andAdvertiserIdEqualTo(id);
        List<AdvertiserAuditModel> audits = advertiserAuditDao.selectByExample(auditEx);
        List<String> oldIds = new ArrayList<String>();
        Map<String, AdvertiserAuditModel> cache = new HashMap<String, AdvertiserAuditModel>();
        for (AdvertiserAuditModel audit : audits) {
        	String adxId = audit.getAdxId();
        	oldIds.add(adxId);
        	cache.put(adxId, audit);
        }
        Map<String, List<String>> adxIdMap = getAddAndDelAdxIds(newIds, oldIds);
        List<String> addIds = adxIdMap.get("add");
        List<String> delIds = adxIdMap.get("del");
        for (String addId : addIds) {
			AdvertiserAuditModel advertiserAudit = new AdvertiserAuditModel();
			advertiserAudit.setId(UUIDGenerator.getUUID());
			advertiserAudit.setAdvertiserId(id);
			advertiserAudit.setAdxId(addId);
			advertiserAudit.setStatus(StatusConstant.ADVERTISER_AUDIT_NOCHECK);
			advertiserAudit.setEnable(StatusConstant.ADVERTISER_ADX_DISABLE);
			
			advertiserAuditDao.insertSelective(advertiserAudit);
        }
        for (String delId : delIds) {
        	AdvertiserAuditModel audit = cache.get(delId);
        	if (StatusConstant.ADVERTISER_AUDIT_SUCCESS.equals(audit.getStatus())) {
        		throw new IllegalStatusException(PhrasesConstant.ADVERVISER_ADX_AUDIT_SUCCESS);
        	}
        	advertiserAuditDao.deleteByPrimaryKey(audit.getId());
        }
    }

    /**
     * 根据id查询广告主
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional
    public AdvertiserBean getAdvertiser(String id) throws Exception
    {
        AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(id);
        
        if (advertiser == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_NOT_FOUND);
        }
        AdvertiserBean bean = modelMapper.map(advertiser, AdvertiserBean.class);
        // 找出所属行业
        String industryId = advertiser.getIndustryId();
        IndustryModel industry = industryDao.selectByPrimaryKey(industryId);
        if (industry != null && !"".equals(industry)) {
        	String industryName = industry.getName();
            bean.setIndustryName(industryName);
        }        
		// 找出adxes
		AdvertiserAuditModelExample advertiserAuditExample = new AdvertiserAuditModelExample();
		advertiserAuditExample.createCriteria().andAdvertiserIdEqualTo(id);
		List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(advertiserAuditExample);
		Audit[] audits = new Audit[advertiserAudits.size()];
		for (int i = 0; i < audits.length; i++) {
			// adx的基本信息
			AdvertiserAuditModel advertiserAudiModel = advertiserAudits.get(i);
			audits[i] = modelMapper.map(advertiserAudiModel, Audit.class);
			// 获取adx的名称（从adx表中获得其名称）
			String adxId = advertiserAudits.get(i).getAdxId();
			AdxModel adxModel = adxDao.selectByPrimaryKey(adxId);
			if (adxModel != null) {
				audits[i].setName(adxModel.getName());
			}			
			// 如果message为null, 设置为空字符串
			if(audits[i].getMessage() == null){
				audits[i].setMessage("");
			}
		}
		bean.setAudits(audits);
                
        // 将DAO创建的新对象复制回传输对象中
        return bean;
    }

    /**
     * 批量查询广告主
     * @param name
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @Transactional
    public List<AdvertiserBean> listAdvertisers(String name, Long startDate, Long endDate) throws Exception
    {
		AdvertiserModelExample example = new AdvertiserModelExample();

		// 根据用户名进行过滤（可选）
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}

		// 按更新时间进行倒序排序
		example.setOrderByClause("update_time DESC");

		List<AdvertiserModel> advertisers = advertiserDao.selectByExample(example);
		List<AdvertiserBean> advertiserBeans = new ArrayList<AdvertiserBean>();

		// 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
		for (AdvertiserModel advertiser : advertisers) {
			AdvertiserBean bean = modelMapper.map(advertiser, AdvertiserBean.class);
			// 找出所属行业
			String industryId = advertiser.getIndustryId();
			IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
			if (industryModel != null && !"".equals(industryModel)) {
	        	String industryName = industryModel.getName();
	            bean.setIndustryName(industryName);
	        }  

			// 找出adxes(1.广告主id找到该广告主审核信息；2.该广告主审核信息中的adxId找到ADX的信息；3.找到ADX的name)
			String advertiserId = advertiser.getId();
			AdvertiserAuditModelExample auditExample = new AdvertiserAuditModelExample();
			auditExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
			List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(auditExample);
			Audit[] audits = new Audit[advertiserAudits.size()];
			for (int i = 0; i < audits.length; i++) {
				// adx的基本信息
				AdvertiserAuditModel advertiserAuditModel = advertiserAudits.get(i);
				audits[i] = modelMapper.map(advertiserAuditModel, Audit.class);
				// 获取adx的名称（从adx表中获得其名称）
				String adxId = advertiserAudits.get(i).getAdxId();
				AdxModel adxModel = adxDao.selectByPrimaryKey(adxId);
				if (adxModel != null) {
					audits[i].setName(adxModel.getName());
				}
			}
			bean.setAudits(audits);

			if (startDate != null && endDate != null) {
				// 查询投放数据
				AdvertiserBean data = (AdvertiserBean)dataService.getAdvertiserData(advertiserId, startDate, endDate);
				BeanUtils.copyProperties(data, bean, "id", "name", "company", "contact", "phone", "qq", 
						"industryId", "industryName", "audits", "brandName", "licenseNo", 
						"organizationNo", "logoPath", "icpPath", "organizationPath", 
						"licensePath", "accountPath", "siteUrl", "siteName", "email", 
						"zip", "address", "remark", "status");
			}

			advertiserBeans.add(bean);
		}

		return advertiserBeans;
    }
    
    /**
     * 查询项目投放数据
     * @param projectId
     * @param beginTime
     * @param endTime
     * @param bean
     * @throws Exception
     */
//    private void getData(Long startDate, Long endDate, AdvertiserBean bean) throws Exception {
//    	ProjectModelExample projectModleExample = new ProjectModelExample();
//    	projectModleExample.createCriteria().andAdvertiserIdEqualTo(bean.getId());
//    	List<ProjectModel> projects = projectDao.selectByExample(projectModleExample);
//    	if (projects != null && !projects.isEmpty()) {
//    		List<String> projectIds = new ArrayList<String>();
//    		for (ProjectModel project : projects) {
//    			projectIds.add(project.getId());
//    		}
//    		CampaignModelExample campaignExample = new CampaignModelExample();
//    		campaignExample.createCriteria().andProjectIdIn(projectIds);
//    		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
//    		if (campaigns != null && !campaigns.isEmpty()) {
//    			List<String> campaignIds = new ArrayList<String>();
//    			for (CampaignModel campaign : campaigns) {
//    				campaignIds.add(campaign.getId());
//    			}
//    			CreativeModelExample creativeExample = new CreativeModelExample();
//    			creativeExample.createCriteria().andCampaignIdIn(campaignIds);
//    			List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
//    			List<String> creativeIds = new ArrayList<String>();
//    			if (creatives != null && !creatives.isEmpty()) {
//    				for (CreativeModel creative : creatives) {
//    					creativeIds.add(creative.getId());
//    				}
//    			}
//    			BasicDataBean dataBean = creativeService.getCreativeDatas(creativeIds, startDate, endDate);
//    			BeanUtils.copyProperties(dataBean, bean);
//    		}
//    	}
//	}
    
    /**
     * 上传广告主资质
     * @param file
     * @return
     * @throws Exception
     */
    @Transactional
    public String uploadQualification(MultipartFile file) throws Exception 
    {
        String path = upload(file);
        
    	return path.replace(uploadDir, "");
    }

    /**
     * 上传广告主资质，针对于广点通logo
     * @param file
     * @return
     * @throws Exception
     */
//    @Transactional
//    public String uploadQualification4Logo(MultipartFile file) throws Exception {
//    	ImageBean bean = (ImageBean) FileUtils.checkFile(file);
//    	Integer width = bean.getWidth();
//    	Integer height = bean.getHeight();
//    	if (width != 80 || height != 80) {
//    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_SIZE);
//    	}
//    	Float volume = bean.getVolume();
//    	if (volume > 30) {
//    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_VOLUME);
//    	}
//    	// 图片绝对路径
//    	String path = FileUtils.uploadFileToLocal(uploadDir + TEMP_DIR, UUIDGenerator.getUUID(), file);
//    	// 返回相对路径
//    	return path.replace(uploadDir, "");
//    }
    
    private void copyTempToFormal(AdvertiserBean advertiserBean) throws Exception
    {
        String qualificationPath = advertiserBean.getQualificationPath();
        
        File destDir = new File(uploadDir + FORMAL_DIR);
        
        if (qualificationPath != null && qualificationPath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + logoPath), destDir);
            doCopy(qualificationPath, destDir);
            advertiserBean.setQualificationPath(qualificationPath.replace(TEMP_DIR, FORMAL_DIR));
        }
    }
    
    private void removeImages(String advertiserId) {
    	// 查询出广告主下的图片
    	AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
    	String qualificationPath = advertiserModel.getQualificationPath();
        
        // 删除
        if (!StringUtils.isEmpty(qualificationPath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(qualificationPath));
        }
    	
    }

    /**
     * 广告主提交第三方审核
     * @param auditId 审核id
     * @throws Exception
     */
    @Transactional
	public void auditAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息判断是否存在
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit == null) {
			throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_AUDIT_NOT_FOUND);
		}
		//判断广告主的审核状态,如果状态是审核中、审核通过、审核不通过，则直接返回提示信息
		String status = advertiserAudit.getStatus();
		if(status.equals(StatusConstant.ADVERTISER_AUDIT_WATING) || status.equals(StatusConstant.ADVERTISER_AUDIT_SUCCESS) 
				|| status.equals(StatusConstant.ADVERTISER_AUDIT_FAILURE)){
			throw new IllegalStatusException(PhrasesConstant.ADVERVISER_STATUS_CANNOT_AUDIT);
		}
		// 获取adxId
		String adxId = advertiserAudit.getAdxId();
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			// 如果adxId是陌陌，则提交陌陌审核
			momoAuditService.auditAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
			// 如果adxId是INMOBI，则提交INMOBI审核
			inmobiAuditService.auditAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
			// 如果adxId是汽车之家，则提交汽车之家审核
			autohomeAuditService.auditAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
		    // 如果adxId是百度，则提交百度BES审核
		    baiduAuditService.auditAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_TENCENT_VALUE.equals(adxId)) {
			tencentAuditService.auditAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adxId)) {
		    adviewAuditService.auditAdvertiser(auditId);
		}
	}
    
    /**
     * 同步广告主第三方审核结果
     * @param id 广告主id
     * @param adxId ADX的id
     * @throws Exception
     */
    @Transactional
	public void synchronizeAdvertiser(String auditId) throws Exception {
		// 查询广告主审核信息判断是否存在
		AdvertiserAuditModel advertiserAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		if (advertiserAudit == null) {
			throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_AUDIT_NOT_FOUND);
		}
		// 判断状态是否可以同步：如果状态是未审核、审核通过、审核不通过，则直接返回提示信息
		String status = advertiserAudit.getStatus();
		if (StatusConstant.ADVERTISER_AUDIT_SUCCESS.equals(status) || StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(status)
				|| StatusConstant.ADVERTISER_AUDIT_NOCHECK.equals(status)) {
			throw new IllegalStatusException(PhrasesConstant.ADVERVISER_STATUS_CANNOT_SYNCHRONIZE);
		}
		// 获取adxId
		String adxId = advertiserAudit.getAdxId();
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			// 如果adxId是陌陌，则同步陌陌审核结果
			momoAuditService.synchronizeAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
			// 如果adxId是INMOBI，则同步INMOBI审核结果
			inmobiAuditService.synchronizeAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_AUTOHOME_VALUE.equals(adxId)) {
			// 如果adxId是汽车之家，则同步汽车之家审核结果
			autohomeAuditService.synchronizeAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adxId)) {
		    // 如果adxId是百度，则同步百度BES审核结果
		    baiduAuditService.synchronizeAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_TENCENT_VALUE.equals(adxId)) {
			tencentAuditService.synchronizeAdvertiser(auditId);
		}
		if (AdxKeyConstant.ADX_ADVIEW_VALUE.equals(adxId)) {
		    adviewAuditService.synchronizeAdvertiser(auditId);
		}
	}

    /**
     * 根据配置文件中的设置，来绝定上传文件至本地文件服务器或远程文件服务器
     * @param env   SpringBoot Environment配置
     * @param file  multipart/form-data对象
     * @return
     * @throws IOException 
     */
    @Transactional
    private String upload(MultipartFile file) throws IOException
    {
        if ("local".equalsIgnoreCase(uploadMode))
        {
            /*return FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);*/
        	return FileUtils.uploadFileToLocal(uploadDir + TEMP_DIR, UUIDGenerator.getUUID(), file);
        }
        else
        {
            /*return FileUtils.uploadFileToRemote(scpUtils, UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);*/
        	return FileUtils.uploadFileToRemote(host, port, username, password, uploadDir + TEMP_DIR, UUIDGenerator.getUUID(), file);
        }
    }
    
    private void doCopy(String path, File destDir) throws Exception
    {
        if ("local".equalsIgnoreCase(uploadMode))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(uploadDir + path), destDir);
        }
        else
        {
            FileUtils.copyRemoteFile(host, port, username, password, uploadDir + path, FilenameUtils.separatorsToUnix(destDir.getPath()));
        }
    }
    
    /**
     * 编辑广告主启用/禁用ADX，即修改数据库中的标志位
     * @param auditId 广告主ADX审核id
     * @param adxId ADX的id
     * @param map 广告主ADX的状态
     * @throws Exception
     */
    @Transactional
    public void updateAdvertiserAdxEnabled(String auditId, Map<String, String> map) throws Exception {
		String enable = map.get("enable");
		// 判断传来的状态是否为空 enabled
		if (StringUtil.isEmpty(enable)) {
			// 如果传来状态为空，则抛异常
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		// 查询广告主审核信息
		AdvertiserAuditModel advertiseAudit = advertiserAuditDao.selectByPrimaryKey(auditId);
		// 判断广告主信息是否存在
		if (advertiseAudit == null) {
			throw new ResourceNotFoundException(PhrasesConstant.ADVERTISER_AUDIT_NOT_FOUND);
		}
		// 如果广告主信息存在，则改变广告主adx的状态
		if (StatusConstant.ADVERTISER_ADX_DISABLE.equals(enable)) { 
			advertiseAudit.setEnable(StatusConstant.ADVERTISER_ADX_DISABLE);
			advertiseAudit.setId(auditId);
			// 更新数据库
			advertiserAuditDao.updateByPrimaryKeySelective(advertiseAudit);
		} else if (StatusConstant.ADVERTISER_ADX_ENABLE.equals(enable)) {
			advertiseAudit.setEnable(StatusConstant.ADVERTISER_ADX_ENABLE);
			advertiseAudit.setId(auditId);
			// 更新数据库
			advertiserAuditDao.updateByPrimaryKeySelective(advertiseAudit);
		} else {
			// 否则抛出异常
			throw new IllegalArgumentException(PhrasesConstant.PARAM_OUT_OF_RANGE);
		}
    }
    
    /**
     * 获取两个id组之间的差异
     * @param newIds
     * @param oldIds
     * @return
     */
    private Map<String, List<String>> getAddAndDelAdxIds(List<String> newIds, List<String> oldIds) {
    	List<String> addIds = new ArrayList<String>();
    	List<String> delIds = new ArrayList<String>();
    	for (String id : newIds) {
    		if (!oldIds.contains(id)) {
    			addIds.add(id);
    		}
    	}
    	for (String id : oldIds) {
    		if (!newIds.contains(id)) {
    			delIds.add(id);
    		}
    	}
    	Map<String, List<String>> result = new HashMap<String, List<String>>();
    	result.put("add", addIds);
    	result.put("del", delIds);
    	
    	return result;
    }
}
