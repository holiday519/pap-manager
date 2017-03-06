package com.pxene.pap.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.beans.AdvertiserBean.Kpi;
import com.pxene.pap.domain.beans.BasicDataBean;
import com.pxene.pap.domain.beans.ImageBean;
import com.pxene.pap.domain.beans.MediaBean;
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
import com.pxene.pap.domain.models.IndustryKpiModel;
import com.pxene.pap.domain.models.IndustryKpiModelExample;
import com.pxene.pap.domain.models.IndustryModel;
import com.pxene.pap.domain.models.KpiModel;
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
    private AuditAdvertiserBaiduService auditAdvertiserBaiduService;
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
    
    private final String UPLOAD_DIR;
    
    private static final String TEMP_DIR = "temp/";
    
    private static final String FORMAL_DIR = "formal/";
    
    @Autowired
    public AdvertiserService(Environment env)
    {
    	UPLOAD_DIR = env.getProperty("pap.fileserver.upload.dir");
    }
    
    @Transactional
    public void saveAdvertiser(AdvertiserBean advertiserBean) throws Exception
    {
    	//验证名称重复
    	if (!StringUtils.isEmpty(advertiserBean.getName())) {
    		AdvertiserModelExample e = new AdvertiserModelExample();
    		e.createCriteria().andNameEqualTo(advertiserBean.getName());
    		List<AdvertiserModel> list = advertiserDao.selectByExample(e);
    		if (list != null && !list.isEmpty()) {
    			throw new IllegalArgumentException(PhrasesConstant.NAME_NOT_REPEAT);
    		}
    	}
    	
        // 先将临时目录中的图片拷贝到正式目录，并将bean中路径替换为正式目录
    	copyTempToFormal(advertiserBean);
    	// 将path替换成正式目录
        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(UUID.randomUUID().toString());
        try
        {
            advertiserDao.insertSelective(advertiserModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO创建的新对象复制回传输对象中
        BeanUtils.copyProperties(advertiserModel, advertiserBean);
    }


    @Transactional
    public void deleteAdvertiser(String id) throws Exception
    {
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        ProjectModelExample example = new ProjectModelExample();
        example.createCriteria().andAdvertiserIdEqualTo(id);
        
        List<ProjectModel> projects = projectDao.selectByExample(example);
        
        // 查看欲操作的广告主名下是否还有创建的项目，如果有，则不可以删除
        if (projects != null && !projects.isEmpty())
        {
            throw new IllegalStatusException(PhrasesConstant.ADVERVISER_HAS_PROJECTS);
        }
        else
        {
        	// 删除广告主下的资质图片
        	removeImages(id);
            advertiserDao.deleteByPrimaryKey(id);
        }
    }
    @Transactional
    public void deleteAdvertisers(String[] ids) throws Exception
    {
    	List<String> asList = Arrays.asList(ids);
    	// 操作前先查询一次数据库，判断指定的资源是否存在
    	AdvertiserModelExample ex = new AdvertiserModelExample();
    	ex.createCriteria().andIdIn(asList);
    	List<AdvertiserModel> advertiserInDB = advertiserDao.selectByExample(ex);
    	if (advertiserInDB == null || advertiserInDB.isEmpty())
    	{
    		throw new ResourceNotFoundException();
    	}
		for (String id : ids) {
			ProjectModelExample example = new ProjectModelExample();
			example.createCriteria().andAdvertiserIdEqualTo(id);
			
			List<ProjectModel> projects = projectDao.selectByExample(example);
			
			// 查看欲操作的广告主名下是否还有创建的项目，如果有，则不可以删除
			if (projects != null && !projects.isEmpty())
			{
				throw new IllegalStatusException(PhrasesConstant.ADVERVISER_HAS_PROJECTS);
			}
		}
		advertiserDao.deleteByExample(ex);
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


    @Transactional
    public void updateAdvertiser(String id, AdvertiserBean advertiserBean) throws Exception
    {
//        // 更新操作要求绑定到RequestBody中的资源ID必须为空
//        if (!StringUtils.isEmpty(advertiserBean.getId()))
//        {
//            throw new IllegalArgumentException();
//        }
        
        // 操作前先查询一次数据库，判断指定的资源是否存在
        AdvertiserModel advertiserInDB = advertiserDao.selectByPrimaryKey(id);
        if (advertiserInDB == null)
        {
            throw new ResourceNotFoundException();
        }
        
        copyTempToFormal(advertiserBean);
        // 将传输对象映射成数据库Model
        AdvertiserModel advertiserModel = modelMapper.map(advertiserBean, AdvertiserModel.class);
        advertiserModel.setId(id);
        
        try
        {
            advertiserDao.updateByPrimaryKey(advertiserModel);
        }
        catch (DuplicateKeyException exception)
        {
            // 违反数据库唯一约束时，向上抛出自定义异常，交给全局异常处理器处理
            throw new DuplicateEntityException();
        }
        
        // 将DAO编辑后的新对象复制回传输对象中
        //BeanUtils.copyProperties(advertiserDao.selectByPrimaryKey(id), advertiserBean);
    }


    public AdvertiserBean findAdvertiserById(String id) throws Exception
    {
        AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(id);
        
        if (advertiserModel == null)
        {
            throw new ResourceNotFoundException();
        }
        AdvertiserBean bean = modelMapper.map(advertiserModel, AdvertiserBean.class);
        // 找出所属行业
        Integer industryId = advertiserModel.getIndustryId();
        IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
        String industryName = industryModel.getName();
        bean.setIndustryName(industryName);
        // 找出该行业下的kpi
        IndustryKpiModelExample example = new IndustryKpiModelExample();
        com.pxene.pap.domain.models.IndustryKpiModelExample.Criteria criteria = example.createCriteria();
        criteria.andIndustryIdEqualTo(industryId);
        List<IndustryKpiModel> industryKpiModels = industryKpiDao.selectByExample(example);
        Kpi[] kpis = new Kpi[industryKpiModels.size()]; 
        for (int i=0; i<kpis.length; i++) {
        	String kpiId = industryKpiModels.get(i).getKpiId();
        	KpiModel kpiModel = kpiDao.selectByPrimaryKey(kpiId);
        	kpis[i] = modelMapper.map(kpiModel, Kpi.class);
        }
        bean.setKpis(kpis);
        
        bean.setStatus(getAdvertiserAuditStatus(advertiserModel.getId()));
        // 将DAO创建的新对象复制回传输对象中
        return bean;
    }


    public List<AdvertiserBean> listAdvertisers(String name, Long beginTime, Long endTime) throws Exception
    {
        AdvertiserModelExample example = new AdvertiserModelExample();
        
        // 根据用户名进行过滤（可选）
        if (!StringUtils.isEmpty(name))
        {
        	example.createCriteria().andNameLike("%" + name + "%");
        }
        
        // 按更新时间进行倒序排序
        example.setOrderByClause("update_time DESC");
        
        List<AdvertiserModel> advertiserModels = advertiserDao.selectByExample(example);
        List<AdvertiserBean> advertiserList = new ArrayList<AdvertiserBean>();
        
        if (advertiserModels == null || advertiserModels.size() <= 0)
        {
            throw new ResourceNotFoundException();
        }
        else
        {
            // 遍历数据库中查询到的全部结果，逐个将DAO创建的新对象复制回传输对象中
            for (AdvertiserModel advertiserModel : advertiserModels)
            {
                //advertiserList.add(modelMapper.map(advertiserModel, AdvertiserBean.class));
            	AdvertiserBean bean = modelMapper.map(advertiserModel, AdvertiserBean.class);
                // 找出所属行业
                Integer industryId = advertiserModel.getIndustryId();
                IndustryModel industryModel = industryDao.selectByPrimaryKey(industryId);
                String industryName = industryModel.getName();
                bean.setIndustryName(industryName);
                // 找出该行业下的kpi
                IndustryKpiModelExample industryKpiModelExample = new IndustryKpiModelExample();
                com.pxene.pap.domain.models.IndustryKpiModelExample.Criteria industryKpiModelExampleCriteria = industryKpiModelExample.createCriteria();
                industryKpiModelExampleCriteria.andIndustryIdEqualTo(industryId);
                List<IndustryKpiModel> industryKpiModels = industryKpiDao.selectByExample(industryKpiModelExample);
                Kpi[] kpis = new Kpi[industryKpiModels.size()]; 
                for (int i=0; i<kpis.length; i++) {
                	String kpiId = industryKpiModels.get(i).getKpiId();
                	KpiModel kpiModel = kpiDao.selectByPrimaryKey(kpiId);
                	kpis[i] = modelMapper.map(kpiModel, Kpi.class);
                }
                bean.setKpis(kpis);
                //查询审核状态
                bean.setStatus(getAdvertiserAuditStatus(advertiserModel.getId()));
                
                if (beginTime != null && endTime != null) {
					//查询投放数据
    				getData(beginTime, endTime, bean);
    			}
                
                advertiserList.add(bean);
            }
        }
        
        return advertiserList;
    }
    
    /**
     * 查询项目投放数据
     * @param projectId
     * @param beginTime
     * @param endTime
     * @param bean
     * @throws Exception
     */
    private void getData(Long beginTime, Long endTime,AdvertiserBean bean) throws Exception {
    	ProjectModelExample projectExample = new ProjectModelExample();
    	projectExample.createCriteria().andAdvertiserIdEqualTo(bean.getId());
    	List<ProjectModel> projects = projectDao.selectByExample(projectExample);
    	BasicDataBean dataBean = new BasicDataBean();//在此处创建bean，并初始化各个参数，保证所有数据都能返回，即便都是零
    	dataService.formatBeanParams(dataBean);
    	dataService.formatBeanRate(dataBean);
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
    			CreativeModelExample cExample = new CreativeModelExample();
    			cExample.createCriteria().andCampaignIdIn(campaignIds);
    			List<CreativeModel> list = creativeDao.selectByExample(cExample);
    			List<String> creativeIds = new ArrayList<String>();
    			if (list != null && !list.isEmpty()) {
    				for (CreativeModel model : list) {
    					creativeIds.add(model.getId());
    				}
    			}
    			dataBean = creativeService.getCreativeDatas(creativeIds, beginTime, endTime);
    		}
    	}
    	bean.setImpressionAmount(dataBean.getImpressionAmount());
		bean.setClickAmount(dataBean.getClickAmount());
		bean.setTotalCost(dataBean.getTotalCost());
		bean.setJumpAmount(dataBean.getJumpAmount());
		bean.setImpressionCost(dataBean.getImpressionCost());
		bean.setClickCost(dataBean.getClickCost());
		bean.setClickRate(dataBean.getClickRate());
		bean.setJumpCost(dataBean.getJumpCost());
	}
    
    /**
	 * 活动审核状态
	 * @param creativeId
	 * @return
	 */
	public String getAdvertiserAuditStatus(String AdvertiserId) {
		AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
		ex.createCriteria().andAdvertiserIdEqualTo(AdvertiserId);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(ex);
		String status = StatusConstant.ADVERTISER_AUDIT_NOCHECK;
		boolean successFlag = false;
		for (AdvertiserAuditModel model : list) {
			if (StatusConstant.ADVERTISER_AUDIT_SUCCESS.equals(model.getStatus())) {
				status = StatusConstant.ADVERTISER_AUDIT_SUCCESS;
				successFlag  = true;
				break;
			}
		}
		if (!successFlag) {
			boolean watingFlag = false;
			for (AdvertiserAuditModel model : list) {
				if (StatusConstant.ADVERTISER_AUDIT_WATING.equals(model.getStatus())) {
					status = StatusConstant.ADVERTISER_AUDIT_WATING;
					watingFlag = true;
					break;
				}
			}
			if (!watingFlag) {
				for (AdvertiserAuditModel model : list) {
					if (StatusConstant.ADVERTISER_AUDIT_FAILURE.equals(model.getStatus())) {
						status = StatusConstant.ADVERTISER_AUDIT_FAILURE;
						break;
					}
				}
			}
		}
		return status;
	}
    
    
    public String uploadQualification(MultipartFile file) throws Exception {
    	// 图片绝对路径
    	String path = FileUtils.uploadFile(UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);
    	// 返回相对路径
    	return path.replace(UPLOAD_DIR, "");
    }
    
    /**
     * 品牌logo专用
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadQualificationForLogo(MultipartFile file) throws Exception {
    	ImageBean bean = (ImageBean) FileUtils.checkFile(file);
    	Integer width = bean.getWidth();
    	Integer height = bean.getHeight();
    	if (width != 80 || height != 80 ) {
    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_SIZE);
    	}
    	Float volume = bean.getVolume();
    	if (volume > 30 ) {
    		throw new IllegalArgumentException(PhrasesConstant.IMAGE_NOT_MAP_VOLUME);
    	}
    	// 图片绝对路径
    	String path = FileUtils.uploadFile(UPLOAD_DIR + TEMP_DIR, UUID.randomUUID().toString(), file);
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
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + logoPath), destDir);
            advertiserBean.setLogoPath(logoPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (accountPath != null && accountPath.contains(TEMP_DIR))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + accountPath), destDir);
            advertiserBean.setAccountPath(accountPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (licensePath != null && licensePath.contains(TEMP_DIR))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + licensePath), destDir);
            advertiserBean.setLicensePath(licensePath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (organizationPath != null && organizationPath.contains(TEMP_DIR))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + organizationPath), destDir);
            advertiserBean.setOrganizationPath(organizationPath.replace(TEMP_DIR, FORMAL_DIR));
        }
        
        if (icpPath != null && icpPath.contains(TEMP_DIR))
        {
            org.apache.commons.io.FileUtils.copyFileToDirectory(new File(UPLOAD_DIR + icpPath), destDir);
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
     * @param id
     * @throws Exception
     */
    @Transactional
	public void auditAdvertiser(String id) throws Exception {
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(id);
		if (advertiserModel == null) {
			throw new ResourceNotFoundException();
		}
		//将属性复制到bean中
		AdvertiserBean advertiserBean = new AdvertiserBean();
		BeanUtils.copyProperties(advertiserModel, advertiserBean);
		//查询adx列表
//		AdxModelExample adxExample = new AdxModelExample();
//		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//广告主审核
//		for (AdxModel adx : adxs) {
//			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {//百度
//				//查询是否已经提交过审核
//				AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
//				ex.createCriteria().andAdvertiserIdEqualTo(id).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
//				List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(ex);
//				if (list == null || list.isEmpty()) {
//					//百度广告主第一次审核
//					auditAdvertiserBaiduService.auditAndEdit(advertiserBean, "add");
//				} else {
//					//百度广告主重新审核
//					auditAdvertiserBaiduService.auditAndEdit(advertiserBean, "edit");
//				}
//			}else if (AdxKeyConstant.ADX_TANX_VALUE.equals(adx.getId())) {
//				
//			}
//		}
		//直接审核通过（现仅百度）
		AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
		example.createCriteria().andAdvertiserIdEqualTo(id);
		List<AdvertiserAuditModel> auditModel = advertiserAuditDao.selectByExample(example);
		if (auditModel == null || auditModel.isEmpty()) {
			AdvertiserAuditModel aModel = new AdvertiserAuditModel();
			aModel.setAdvertiserId(id);
			aModel.setAdxId(AdxKeyConstant.ADX_BAIDU_VALUE);
			long num =  (long) Math.floor((new Random()).nextDouble() * 1000000000D);
			String auditValue = String.valueOf(num);
			aModel.setAuditValue(auditValue);
			aModel.setId(UUID.randomUUID().toString());
			aModel.setStatus(StatusConstant.ADVERTISER_AUDIT_SUCCESS);
			advertiserAuditDao.insertSelective(aModel);
		}

	}
    
    /**
     * 同步广告主第三方审核结果
     * @param id
     * @throws Exception
     */
    @Transactional
	public void synchronize(String id) throws Exception {
		AdvertiserModel advertiserModel = advertiserDao.selectByPrimaryKey(id);
		if (advertiserModel == null) {
			throw new ResourceNotFoundException();
		}
		//查询adx列表
		AdxModelExample adxExample = new AdxModelExample();
		List<AdxModel> adxs = adxDao.selectByExample(adxExample);
		//同步结果
		for (AdxModel adx : adxs) {
			if (AdxKeyConstant.ADX_BAIDU_VALUE.equals(adx.getId())) {
				//百度
				auditAdvertiserBaiduService.synchronize(id);
			}else if (AdxKeyConstant.ADX_TANX_VALUE.equals(adx.getId())) {
				
			}
		}
	}
    
}
