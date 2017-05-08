package com.pxene.pap.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.beans.AdvertiserBean.Adx;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdvertiserModelExample;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.domain.models.AdxModelExample;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.CreativeModel;
import com.pxene.pap.domain.models.CreativeModelExample;
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
import com.pxene.pap.repository.basic.IndustryKpiDao;
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
    private IndustryKpiDao industryKpiDao;
    @Autowired
    private KpiDao kpiDao;
    
	@Autowired
	private MomoAuditService momoAuditService;
	
	@Autowired
	private InmobiAuditService inmobiAuditService;
    
    private Environment env;
    
    private static String UPLOAD_MODE;
    
    private static String UPLOAD_DIR;
    
    private static final String TEMP_DIR = "temp/";
    
    private static final String FORMAL_DIR = "formal/";
    
    private String host;
    
    private int port;
    
    private String username;
    
    private String password;
    
    
    @Autowired
    public AdvertiserService(Environment env)
    {
        this.env = env;
        
        UPLOAD_MODE = env.getProperty("pap.fileserver.mode", "local");
        
        host = env.getProperty("pap.fileserver.remote.host");
        port = Integer.parseInt(env.getProperty("pap.fileserver.remote.port", "22"));
        username = env.getProperty("pap.fileserver.remote.username");
        password = env.getProperty("pap.fileserver.remote.password");
        
        if ("local".equals(UPLOAD_MODE))
        {
            UPLOAD_DIR = env.getProperty("pap.fileserver.local.upload.dir");
        }
        else
        {
            UPLOAD_DIR = env.getProperty("pap.fileserver.remote.upload.dir");
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
		List<AdxModel> adxes = adxDao.selectByExample(adxExample);
		for (AdxModel adx : adxes) {
			// 1.如果广告主审核信息为空，则向广告主审核表插入数据
			AdvertiserAuditModel advertiserAudit = new AdvertiserAuditModel();
			advertiserAudit.setId(UUIDGenerator.getUUID());
			advertiserAudit.setAdvertiserId(bean.getId());
//			advertiserAudit.setAuditValue("1");
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
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
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
    	// 操作前先查询一次数据库，判断指定的资源是否存在
    	AdvertiserModelExample advertiserModelExample = new AdvertiserModelExample();
    	advertiserModelExample.createCriteria().andIdIn(Arrays.asList(ids));
    	List<AdvertiserModel> advertiserInDB = advertiserDao.selectByExample(advertiserModelExample);
    	if (advertiserInDB == null || advertiserInDB.size() < ids.length)
    	{
    		throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
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
		for (int i = 0; i < advertiserInDB.size(); i++) {
			AdvertiserAuditModelExample auditExample = new AdvertiserAuditModelExample();
			auditExample.createCriteria().andAdvertiserIdEqualTo(advertiserInDB.get(i).getId());
			List<AdvertiserAuditModel> auditModels = advertiserAuditDao.selectByExample(auditExample);
			for (AdvertiserAuditModel auditModel : auditModels) {
				advertiserAuditDao.deleteByPrimaryKey(auditModel.getId());
			}
		} 
		
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
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        } else {
        	String nameInDB = advertiserInDB.getName();
        	String name = bean.getName();
        	if (!nameInDB.equals(name)) {
        		// 验证名称重复
            	AdvertiserModelExample example = new AdvertiserModelExample();
            	example.createCriteria().andNameEqualTo(bean.getName());
        		List<AdvertiserModel> advertisers = advertiserDao.selectByExample(example);
        		if (advertisers != null && !advertisers.isEmpty()) {
        			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
        		}
        	}
        }
        
        copyTempToFormal(bean);
        // 将传输对象映射成数据库Model
        AdvertiserModel model = modelMapper.map(bean, AdvertiserModel.class);
        model.setId(id);
        
        advertiserDao.updateByPrimaryKey(model);
    }

    /**
     * 根据id查询广告主
     * @param id
     * @return
     * @throws Exception
     */
    public AdvertiserBean getAdvertiser(String id) throws Exception
    {
        AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(id);
        
        if (advertiser == null)
        {
            throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
        }
        AdvertiserBean bean = modelMapper.map(advertiser, AdvertiserBean.class);
        // 找出所属行业
        Integer industryId = advertiser.getIndustryId();
        IndustryModel industry = industryDao.selectByPrimaryKey(industryId);
        if (industry != null && !"".equals(industry)) {
        	String industryName = industry.getName();
            bean.setIndustryName(industryName);
        }        
		// 找出adxes
		String advertiserId = advertiser.getId();
		AdvertiserAuditModelExample advertiserAuditExample = new AdvertiserAuditModelExample();
		advertiserAuditExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
		List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(advertiserAuditExample);
		Adx[] adxes = new Adx[advertiserAudits.size()];
		for (int i = 0; i < adxes.length; i++) {
			// adx的基本信息
			AdvertiserAuditModel advertiserAudiModel = advertiserAudits.get(i);
			Adx adx = modelMapper.map(advertiserAudiModel, Adx.class);
			// 获取adx的名称（从adx表中获得其名称）
			String adxId = advertiserAudits.get(i).getAdxId();
			AdxModel adxModel = adxDao.selectByPrimaryKey(adxId);
			// 放到adx[]中
			adxes[i] = adx;
			adxes[i].setName(adxModel.getName());
		}
		bean.setAdxes(adxes);
                
        // bean.setStatus(getAdvertiserAuditStatus(advertiser.getId()));
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

		if (advertisers == null || advertisers.size() <= 0) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}

		// 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
		for (AdvertiserModel advertiser : advertisers) {
			AdvertiserBean bean = modelMapper.map(advertiser, AdvertiserBean.class);
			// 找出所属行业
			Integer industryId = advertiser.getIndustryId();
			IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
			if (industryModel != null && !"".equals(industryModel)) {
	        	String industryName = industryModel.getName();
	            bean.setIndustryName(industryName);
	        }  

			// 找出adxes
			String advertiserId = advertiser.getId();
			AdvertiserAuditModelExample auditExample = new AdvertiserAuditModelExample();
			auditExample.createCriteria().andAdvertiserIdEqualTo(advertiserId);
			List<AdvertiserAuditModel> advertiserAudits = advertiserAuditDao.selectByExample(auditExample);
			Adx[] adxes = new Adx[advertiserAudits.size()];
			for (int i = 0; i < adxes.length; i++) {
				// adx的基本信息
				AdvertiserAuditModel advertiserAuditModel = advertiserAudits.get(i);
				Adx adx = modelMapper.map(advertiserAuditModel, Adx.class);
				// 获取adx的名称（从adx表中获得其名称）
				String adxId = advertiserAudits.get(i).getAdxId();
				AdxModel adxModel = adxDao.selectByPrimaryKey(adxId);
				// 放到adx[]数组中
				adxes[i] = adx;
				adxes[i].setName(adxModel.getName());
			}
			bean.setAdxes(adxes);

			// 查询审核状态
			//bean.setStatus(getAdvertiserAuditStatus(advertiser.getId()));

			if (startDate != null && endDate != null) {
				// 查询投放数据
				getData(startDate, endDate, bean);
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
    private void getData(Long startDate, Long endDate, AdvertiserBean bean) throws Exception {
    	ProjectModelExample projectModleExample = new ProjectModelExample();
    	projectModleExample.createCriteria().andAdvertiserIdEqualTo(bean.getId());
    	List<ProjectModel> projects = projectDao.selectByExample(projectModleExample);
    	if (projects != null && !projects.isEmpty()) {
    		List<String> projectIds = new ArrayList<String>();
    		for (ProjectModel project : projects) {
    			projectIds.add(project.getId());
    		}
    		CampaignModelExample campaignExample = new CampaignModelExample();
    		campaignExample.createCriteria().andProjectIdIn(projectIds);
    		List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
    		if (campaigns != null && !campaigns.isEmpty()) {
    			List<String> campaignIds = new ArrayList<String>();
    			for (CampaignModel campaign : campaigns) {
    				campaignIds.add(campaign.getId());
    			}
    			CreativeModelExample creativeExample = new CreativeModelExample();
    			creativeExample.createCriteria().andCampaignIdIn(campaignIds);
    			List<CreativeModel> creatives = creativeDao.selectByExample(creativeExample);
    			List<String> creativeIds = new ArrayList<String>();
    			if (creatives != null && !creatives.isEmpty()) {
    				for (CreativeModel creative : creatives) {
    					creativeIds.add(creative.getId());
    				}
    			}
    			BasicDataBean dataBean = creativeService.getCreativeDatas(creativeIds, startDate, endDate);
    			BeanUtils.copyProperties(dataBean, bean);
    		}
    	}
	}
    
    /**
	 * 活动审核状态
	 * @param creativeId
	 * @return
	 */
//	private String getAdvertiserAuditStatus(String AdvertiserId) {
//		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
//		example.createCriteria().andAdvertiserIdEqualTo(AdvertiserId);
//		List<AdvertiserAuditModel> models = advertiserAuditDao.selectByExample(example);
//		String status = StatusConstant.ADVERTISER_AUDIT_NOCHECK;
//		boolean successFlag = false;
//		for (AdvertiserAuditModel model : models) {
//			if (StatusConstant.ADVERTISER_AUDIT_SUCCESS.equals(model.getStatus())) {
//				status = StatusConstant.ADVERTISER_AUDIT_SUCCESS;
//				successFlag  = true;
//				break;
//			}
//		}
//		if (!successFlag) {
//			boolean watingFlag = false;
//			for (AdvertiserAuditModel model : models) {
//				if (StatusConstant.ADVERTISER_AUDIT_WATING.equals(model.getStatus())) {
//					status = StatusConstant.ADVERTISER_AUDIT_WATING;
//					watingFlag = true;
//					break;
//				}
//			}
//			if (!watingFlag) {
//				for (AdvertiserAuditModel model : models) {
//					if (StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(model.getStatus())) {
//						status = StatusConstant.ADVERTISER_AUDIT_FAILURE;
//						break;
//					}
//				}
//			}
//		}
//		return status;
//	}
    
    /**
     * 上传广告主资质
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadQualification(MultipartFile file) throws Exception 
    {
    	// 图片绝对路径
    	// String path = FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);
    	// String path = FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUIDGenerator.getUUID(), file);
        // 获得图片上传至本地/远程文件服务器的物理绝对路径
        String path = upload(env, file);
        
    	// 返回相对路径
    	return path.replace(UPLOAD_DIR, "");
    }

    /**
     * 上传广告主资质，针对于广点通logo
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadQualification4Logo(MultipartFile file) throws Exception {
    	ImageBean bean = (ImageBean) FileUtils.checkFile(file);
    	Integer width = bean.getWidth();
    	Integer height = bean.getHeight();
    	if (width != 80 || height != 80) {
    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_SIZE);
    	}
    	Float volume = bean.getVolume();
    	if (volume > 30) {
    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_VOLUME);
    	}
    	// 图片绝对路径
    	String path = FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUIDGenerator.getUUID(), file);
    	// 返回相对路径
    	return path.replace(UPLOAD_DIR, "");
    }
    
    private void copyTempToFormal(AdvertiserBean advertiserBean) throws Exception
    {
        String logoPath = advertiserBean.getLogoPath();
        String accountPath = advertiserBean.getAccountPath();
        String licensePath = advertiserBean.getLicensePath();
        String organizationPath = advertiserBean.getOrganizationPath();
        String icpPath = advertiserBean.getIcpPath();
        
        File destDir = new File(UPLOAD_DIR + FORMAL_DIR);
        
        if (logoPath != null && logoPath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + logoPath), destDir);
            doCopy(logoPath, destDir);
            advertiserBean.setLogoPath(logoPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (accountPath != null && accountPath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + accountPath), destDir);
            doCopy(accountPath, destDir);
            advertiserBean.setAccountPath(accountPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (licensePath != null && licensePath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + licensePath), destDir);
            doCopy(licensePath, destDir);
            advertiserBean.setLicensePath(licensePath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (organizationPath != null && organizationPath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + organizationPath), destDir);
            doCopy(organizationPath, destDir);
            advertiserBean.setOrganizationPath(organizationPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (icpPath != null && icpPath.contains(TEMP_DIR))
        {
            //org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + icpPath), destDir);
            doCopy(icpPath, destDir);
            advertiserBean.setIcpPath(icpPath.replace(TEMP_DIR, FORMAL_DIR));
        }
    }
    
    private void removeImages(String advertiserId) {
    	// 查询出广告主下的图片
    	AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(advertiserId);
    	String logoPath = advertiserModel.getLogoPath();
        String accountPath = advertiserModel.getAccountPath();
        String licensePath = advertiserModel.getLicensePath();
        String organizationPath = advertiserModel.getOrganizationPath();
        String icpPath = advertiserModel.getIcpPath();
        
        // 删除
        if (!StringUtils.isEmpty(logoPath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(logoPath));
        }
        if (!StringUtils.isEmpty(accountPath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(accountPath));
        }
        if (!StringUtils.isEmpty(licensePath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(licensePath));
        }
        if (!StringUtils.isEmpty(organizationPath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(organizationPath));
        }
        if (!StringUtils.isEmpty(icpPath)) {
        	org.apache.commons.io.FileUtils.deleteQuietly(new File(icpPath));
        }
    	
    }

    /**
     * 广告主提交第三方审核
     * @param id 广告主id
     * @param adxId ADX的id
     * @throws Exception
     */
    @Transactional
	public void auditAdvertiser(String id, String adxId) throws Exception {
    	// 查询广告主信息判断是否存在广告主
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(id);
		if (advertiser == null) {
			// 如果广告主不存在，则提示：该对象不存在
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			// 如果adxId是陌陌，则提交陌陌审核
			momoAuditService.auditAdvertiser(id);
		}
		if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
			// 如果adxId是INMOBI，则提交INMOBI审核
			inmobiAuditService.auditAdvertiser(id);
		}		
	}
    
    /**
     * 同步广告主第三方审核结果
     * @param id 广告主id
     * @param adxId ADX的id
     * @throws Exception
     */
    @Transactional
	public void synchronizeAdvertiser(String id, String adxId) throws Exception {
    	// 查询广告主信息判断是否存在广告主
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(id);
		if (advertiser == null) {
			// 如果广告主不存在，则提示：该对象不存在
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		if (AdxKeyConstant.ADX_MOMO_VALUE.equals(adxId)) {
			// 如果adxId是陌陌，则同步陌陌审核结果
			momoAuditService.synchronizeAdvertiser(id);
		}
		if (AdxKeyConstant.ADX_INMOBI_VALUE.equals(adxId)) {
			// 如果adxId是INMOBI，则同步INMOBI审核结果
			inmobiAuditService.synchronizeAdvertiser(id);
		}		
	}

    /**
     * 根据配置文件中的设置，来绝定上传文件至本地文件服务器或远程文件服务器
     * @param env   SpringBoot Environment配置
     * @param file  multipart/form-data对象
     * @return
     */
    private String upload(Environment env, MultipartFile file)
    {
        if ("local".equalsIgnoreCase(UPLOAD_MODE))
        {
            /*return FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);*/
        	return FileUtils.uploadFileToLocal(UPLOAD_DIR + TEMP_DIR, UUIDGenerator.getUUID(), file);
        }
        else
        {
            /*return FileUtils.uploadFileToRemote(scpUtils, UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);*/
        	return FileUtils.uploadFileToRemote(host, port, username, password, UPLOAD_DIR + TEMP_DIR, UUIDGenerator.getUUID(), file);
        }
    }
    
    private void doCopy(String path, File destDir) throws Exception
    {
        if ("local".equalsIgnoreCase(UPLOAD_MODE))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + path), destDir);
        }
        else
        {
            FileUtils.copyRemoteFile(host, port, username, password, UPLOAD_DIR + path, FilenameUtils.separatorsToUnix(destDir.getPath()));
        }
    }
    
    /**
     * 编辑广告主启用/禁用ADX，即修改数据库中的标志位
     * @param id 广告主id
     * @param adxId ADX的id
     * @param map 广告主ADX的状态
     * @throws Exception
     */
    @Transactional
    public void updateAdvertiserAdxEnabled(String id, String adxId, Map<String,String> map) throws Exception {
    	String enable = map.get("enable");
    	// 判断传来的状态是否为空  enabled
    	if (StringUtil.isEmpty(enable)) {
    		// 如果传来状态为空，则抛异常  
    		throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
    	}
    	// 判断广告主对应adx审核信息是否存在
    	AdvertiserAuditModelExample adAuditExample = new AdvertiserAuditModelExample();
    	adAuditExample.createCriteria().andAdvertiserIdEqualTo(id).andAdxIdEqualTo(adxId);
    	List<AdvertiserAuditModel> advertiseAuditList = advertiserAuditDao.selectByExample(adAuditExample);
    	if (advertiseAuditList == null || advertiseAuditList.size() == 0) {
    		// 如果信息不存在，则抛异常  
    		throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
    	}
    	if (StatusConstant.ADVERTISER_ADX_ENABLE.equals(enable)) {
    		// 如果获得的状态为开启状态，则修改状态为禁用
    		AdvertiserAuditModel advertiseAudit = advertiseAuditList.get(0);
    		advertiseAudit.setEnable(StatusConstant.ADVERTISER_ADX_DISABLE);
    		// 更新数据库
    		advertiserAuditDao.updateByPrimaryKeySelective(advertiseAudit);
    	} else if (StatusConstant.ADVERTISER_ADX_DISABLE.equals(enable)) {
    		// 如果获得的状态为禁用状态，则修改状态为开启
    		AdvertiserAuditModel advertiseAudit = advertiseAuditList.get(0);
    		advertiseAudit.setEnable(StatusConstant.ADVERTISER_ADX_ENABLE);
    		// 更新数据库
    		advertiserAuditDao.updateByPrimaryKeySelective(advertiseAudit);
    	} else {
    		// 否则抛出异常
    		throw new IllegalArgumentException(PhrasesConstant.PARAM_OUT_OF_RANGE);
    	}
    }
}
