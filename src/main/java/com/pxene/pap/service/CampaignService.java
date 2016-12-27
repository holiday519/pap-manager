package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.CampaignInfoBean;
import com.pxene.pap.domain.beans.CampaignInfoBean.Frequency;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.CampaignBean.Target;
import com.pxene.pap.domain.beans.CampaignInfoBean.Monitor;
import com.pxene.pap.domain.beans.CampaignTargetBean;
import com.pxene.pap.domain.model.basic.AdTypeTargetModel;
import com.pxene.pap.domain.model.basic.AdTypeTargetModelExample;
import com.pxene.pap.domain.model.basic.AppTargetModel;
import com.pxene.pap.domain.model.basic.AppTargetModelExample;
import com.pxene.pap.domain.model.basic.BrandTargetModel;
import com.pxene.pap.domain.model.basic.BrandTargetModelExample;
import com.pxene.pap.domain.model.basic.CampaignModel;
import com.pxene.pap.domain.model.basic.CampaignModelExample;
import com.pxene.pap.domain.model.basic.CreativeModel;
import com.pxene.pap.domain.model.basic.CreativeModelExample;
import com.pxene.pap.domain.model.basic.DeviceTargetModel;
import com.pxene.pap.domain.model.basic.DeviceTargetModelExample;
import com.pxene.pap.domain.model.basic.FrequencyModel;
import com.pxene.pap.domain.model.basic.MonitorModel;
import com.pxene.pap.domain.model.basic.MonitorModelExample;
import com.pxene.pap.domain.model.basic.NetworkTargetModel;
import com.pxene.pap.domain.model.basic.NetworkTargetModelExample;
import com.pxene.pap.domain.model.basic.OperatorTargetModel;
import com.pxene.pap.domain.model.basic.OperatorTargetModelExample;
import com.pxene.pap.domain.model.basic.OsTargetModel;
import com.pxene.pap.domain.model.basic.OsTargetModelExample;
import com.pxene.pap.domain.model.basic.ProjectModel;
import com.pxene.pap.domain.model.basic.RegionTargetModel;
import com.pxene.pap.domain.model.basic.RegionTargetModelExample;
import com.pxene.pap.domain.model.basic.TimeTargetModel;
import com.pxene.pap.domain.model.basic.TimeTargetModelExample;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModel;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdTypeTargetDao;
import com.pxene.pap.repository.basic.AppTargetDao;
import com.pxene.pap.repository.basic.BrandTargetDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.CreativeDao;
import com.pxene.pap.repository.basic.DeviceTargetDao;
import com.pxene.pap.repository.basic.FrequencyDao;
import com.pxene.pap.repository.basic.MonitorDao;
import com.pxene.pap.repository.basic.NetworkTargetDao;
import com.pxene.pap.repository.basic.OperatorTargetDao;
import com.pxene.pap.repository.basic.OsTargetDao;
import com.pxene.pap.repository.basic.ProjectDao;
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

@Service
public class CampaignService extends LaunchService{
	
	@Autowired
	private CampaignDao campaignDao; 
	
	@Autowired
	private ProjectDao projectDao; 
	
	@Autowired
	private CreativeService creativeService; 
	
	@Autowired
	private CreativeDao creativeDao; 
	
	@Autowired
	private MonitorDao monitorDao;
	
	@Autowired
	private FrequencyDao frequencyDao;
	
	@Autowired
	private RegionTargetDao regionTargetDao;
	
	@Autowired
	private AdTypeTargetDao adtypeTargetDao;
	
	@Autowired
	private TimeTargetDao timeTargetDao;
	
	@Autowired
	private NetworkTargetDao networkTargetDao;
	
	@Autowired
	private OperatorTargetDao operatorTargetDao;
	
	@Autowired
	private DeviceTargetDao deviceTargetDao;
	
	@Autowired
	private OsTargetDao osTargetDao;
	
	@Autowired
	private BrandTargetDao brandTargetDao;
	
	@Autowired
	private AppTargetDao appTargetDao;
	
	@Autowired
	private CampaignTargetDao campaignTargetDao;
	
	/**
	 * 创建活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCampaign(CampaignInfoBean bean) throws Exception {
		
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		String id = UUID.randomUUID().toString();
		bean.setId(id);//此处放入ID，添加活动相关联信息时用到
		campaignModel.setId(id);
		
		try {
			String frequencyId = UUID.randomUUID().toString();
			Frequency frequency = bean.getFrequency();
			//添加频次信息
			FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
			frequencyModel.setId(frequencyId);
			frequencyDao.insertSelective(frequencyModel);
			//添加点击、展现监测地址
			addCampaignMonitor(bean);
			//添加频次ID
			campaignModel.setFrequencyId(frequencyId);
			campaignModel.setStatus(StatusConstant.CAMPAIGN_WATING);
			//添加活动基本信息
			campaignDao.insertSelective(campaignModel);
			
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
//		BeanUtils.copyProperties(campaignModel, bean);
	}
	
	/**
	 * 编辑活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCampaign(String id ,CampaignInfoBean bean) throws Exception {
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(id);
		if (campaignInDB == null || StringUtils.isEmpty(campaignInDB.getId())) {
			throw new ResourceNotFoundException();
		}
		
		bean.setId(id);//bean中放入ID，用于更新关联关系表中数据
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		
		try {
			//删除频次信息***
			deletefrequency(id);
			//频次数据---如果以前有修改即可；若果以前没有，那就新建
			Frequency frequency = bean.getFrequency();
			String frequencyId = campaignInDB.getFrequencyId();
			if(StringUtils.isEmpty(frequencyId)){
				//以前就是null；如果现在还是null那就不处理，现在不是null添加
				frequencyId = UUID.randomUUID().toString();
				if (frequency != null) {
					// 添加频次信息
					FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
					frequencyModel.setId(frequencyId);
					frequencyDao.insertSelective(frequencyModel);
				}
			}else{
				//以前就不是null；如果现在是null就删除frequencyId；如果现在不是null就修改frequencyId对应的频次信息
				FrequencyModel frequencyModel = modelMapper.map(frequency, FrequencyModel.class);
				if (frequency != null) {
					//修改频次信息
					frequencyModel.setId(frequencyId);
					frequencyDao.updateByPrimaryKeySelective(frequencyModel);
				} else {
					//删除频次后将活动表中FrequencyId变成空
					campaignModel.setFrequencyId("");
				}
				
			}
			//删除点击、展现监测地址
			deleteCampaignMonitor(id);
			//重新添加点击、展现监测地址
			addCampaignMonitor(bean);
			//修改基本信息
			campaignDao.updateByPrimaryKeySelective(campaignModel);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
	}
	
	/**
	 * 修改活动状态
	 * @param id
	 * @param action
	 * @throws Exception
	 */
	@Transactional
	public void updateCampaignStatus(String id, Map<String, String> map) throws Exception {
		if (StringUtils.isEmpty(map.get("action"))) {
			throw new IllegalArgumentException();
		}
		String action = map.get("action").toString();
		String status = "";
		if ("01".equals(action)) {
			status = StatusConstant.CAMPAIGN_START;
		} else if ("02".equals(action)) {
			status = StatusConstant.CAMPAIGN_PAUSE;
		}else {
			throw new IllegalStatusException();
		}
		CampaignModel model = new CampaignModel();
		model.setId(id);
		model.setStatus(status);
		campaignDao.updateByPrimaryKeySelective(model);
	}
	
	/**
	 * 设置活动定向，新增和修改
	 * @param id
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createCampaignTarget(String id, CampaignTargetBean bean) throws Exception {
		bean.setId(id);
		//删除掉定向
		deleteCampaignTarget(id);
		//添加定向
		addCampaignTarget(bean);
	}
	
	/**
	 * 添加活动定向
	 * @param bean
	 */
	@Transactional
	public void addCampaignTarget(CampaignTargetBean bean) throws Exception {
		String id = bean.getId();
		String[] regionTarget = bean.getRegion();//地域
		String[] adTypeTarget = bean.getAdType();//广告类型
		String[] timeTarget = bean.getTime();//时间
		String[] networkTarget = bean.getNetwork();//网络
		String[] operatorTarget = bean.getOperator();//运营商
		String[] deviceTarget = bean.getDevice();//设备
		String[] osTarget = bean.getOs();//系统
		String[] brandTarget = bean.getBrand();//品牌
		String[] appTarget = bean.getApp();//app
		if (regionTarget != null && regionTarget.length > 0) {
			RegionTargetModel region;
			for (String regionId : regionTarget) {
				region = new RegionTargetModel();
				region.setId(UUID.randomUUID().toString());
				region.setCampaignId(id);
				region.setRegionId(regionId);
				regionTargetDao.insertSelective(region);
			}
		}
		if (adTypeTarget != null && adTypeTarget.length > 0) {
			AdTypeTargetModel adType;
			for (String adTypeId : adTypeTarget) {
				adType = new AdTypeTargetModel();
				adType.setId(UUID.randomUUID().toString());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetDao.insertSelective(adType);
			}
		}
		if (timeTarget != null && timeTarget.length > 0) {
			TimeTargetModel time;
			for (String timeId : timeTarget) {
				time = new TimeTargetModel();
				time.setId(UUID.randomUUID().toString());
				time.setCampaignId(id);
				time.setTimeId(timeId);
				timeTargetDao.insertSelective(time);
			}
		}
		if (networkTarget != null && networkTarget.length > 0) {
			NetworkTargetModel network;
			for (String networkid : networkTarget) {
				network = new NetworkTargetModel();
				network.setId(UUID.randomUUID().toString());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetDao.insertSelective(network);
			}
		}
		if (operatorTarget != null && operatorTarget.length > 0) {
			OperatorTargetModel operator;
			for (String operatorId : operatorTarget) {
				operator = new OperatorTargetModel();
				operator.setId(UUID.randomUUID().toString());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetDao.insertSelective(operator);
			}
		}
		if (deviceTarget != null && deviceTarget.length > 0) {
			DeviceTargetModel device;
			for (String deviceId : deviceTarget) {
				device = new DeviceTargetModel();
				device.setId(UUID.randomUUID().toString());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetDao.insertSelective(device);
			}
		}
		if (osTarget != null && osTarget.length > 0) {
			OsTargetModel os;
			for (String osId : osTarget) {
				os = new OsTargetModel();
				os.setId(UUID.randomUUID().toString());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetDao.insertSelective(os);
			}
		}
		if (brandTarget != null && brandTarget.length > 0) {
			BrandTargetModel brand;
			for (String brandId : brandTarget) {
				brand = new BrandTargetModel();
				brand.setId(UUID.randomUUID().toString());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetDao.insertSelective(brand);
			}
		}
		if (appTarget != null && appTarget.length > 0) {
			AppTargetModel app;
			for (String appId : appTarget) {
				app = new AppTargetModel();
				app.setId(UUID.randomUUID().toString());
				app.setCampaignId(id);
				app.setAppId(appId);
				appTargetDao.insertSelective(app);
			}
		}
	}
	
	/**
	 * 删除活动定向
	 * @param campaignId
	 */
	@Transactional
	public void deleteCampaignTarget(String campaignId)  throws Exception {
		//删除地域定向
		RegionTargetModelExample region = new RegionTargetModelExample();
		region.createCriteria().andCampaignIdEqualTo(campaignId);
		regionTargetDao.deleteByExample(region);
		//删除广告类型定向
		AdTypeTargetModelExample adType = new AdTypeTargetModelExample();
		adType.createCriteria().andCampaignIdEqualTo(campaignId);
		adtypeTargetDao.deleteByExample(adType);
		//删除时间定向
		TimeTargetModelExample time = new TimeTargetModelExample();
		time.createCriteria().andCampaignIdEqualTo(campaignId);
		timeTargetDao.deleteByExample(time);
		//删除网络定向
		NetworkTargetModelExample network = new NetworkTargetModelExample();
		network.createCriteria().andCampaignIdEqualTo(campaignId);
		networkTargetDao.deleteByExample(network);
		//删除运营商定向
		OperatorTargetModelExample op = new OperatorTargetModelExample();
		op.createCriteria().andCampaignIdEqualTo(campaignId);
		operatorTargetDao.deleteByExample(op);
		//删除设备定向
		DeviceTargetModelExample device = new DeviceTargetModelExample();
		device.createCriteria().andCampaignIdEqualTo(campaignId);
		deviceTargetDao.deleteByExample(device);
		//删除系统定向
		OsTargetModelExample os = new OsTargetModelExample();
		os.createCriteria().andCampaignIdEqualTo(campaignId);
		osTargetDao.deleteByExample(os);
		//删除品牌定向
		BrandTargetModelExample brand = new BrandTargetModelExample();
		brand.createCriteria().andCampaignIdEqualTo(campaignId);
		brandTargetDao.deleteByExample(brand);
		//删除APP定向
		AppTargetModelExample app = new AppTargetModelExample();
		app.createCriteria().andCampaignIdEqualTo(campaignId);
		appTargetDao.deleteByExample(app);
	}
	
	/**
	 * 添加活动监测地址
	 * @param bean
	 */
	@Transactional
	public void addCampaignMonitor(CampaignInfoBean bean) throws Exception {
		Monitor[] monitors = bean.getMonitors();
		if (monitors != null && monitors.length > 0) {
			String id = bean.getId();
			for (Monitor mnt : monitors) {
				String monitorId = UUID.randomUUID().toString();
				MonitorModel monitor = modelMapper.map(mnt, MonitorModel.class);
				monitor.setId(monitorId);
				monitor.setCampaignId(id);
				monitorDao.insertSelective(monitor);
			}
		}
	}
	
	/**
	 * 删除活动监测地址
	 * @param campaignId
	 */
	@Transactional
	public void deleteCampaignMonitor(String campaignId) throws Exception {
		MonitorModelExample example = new MonitorModelExample();
		example.createCriteria().andCampaignIdEqualTo(campaignId);
		monitorDao.deleteByExample(example);
	}
	
	/**
	 * 删除频次信息
	 * @param campaignId
	 * @throws Exception
	 */
	@Transactional
	public void deletefrequency(String campaignId) throws Exception {
		CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
		frequencyDao.deleteByPrimaryKey(campaignModel.getFrequencyId());
	}
	
	/**
	 * 删除活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void deleteCampaign(String campaignId) throws Exception {
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(campaignId);
		if (campaignInDB ==null || StringUtils.isEmpty(campaignInDB.getId())) {
			throw new ResourceNotFoundException();
		}
		
		//先查询出活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> list = creativeDao.selectByExample(creativeExample);
		if (list != null && !list.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.CAMPAIGN_HAS_CREATIVE);
		}
		//删除检测地址
		deleteCampaignMonitor(campaignId);
		//删除定向
		deleteCampaignTarget(campaignId);
		//删除频次信息
		deletefrequency(campaignId);
		//删除活动
		campaignDao.deleteByPrimaryKey(campaignId);
	}
	
	/**
	 * 根据id查询活动
	 * @param campaignId
	 * @return
	 */
	public CampaignBean selectCampaign(String campaignId)  throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model ==null || StringUtils.isEmpty(model.getId())) {
        	throw new ResourceNotFoundException();
        }
		CampaignBean map = modelMapper.map(model, CampaignBean.class);
		CampaignBean bean = addParamToCampaign(map, model.getId());
		return bean;
	}
	
	/**
	 * 查询活动列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<CampaignBean> selectCampaigns(String name) throws Exception {
		CampaignModelExample example = new CampaignModelExample();
		if(!StringUtils.isEmpty(name)){
			example.createCriteria().andNameLike("%" + name + "%");
		}
		
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		List<CampaignBean> beans = new ArrayList<CampaignBean>();
		
		if (campaigns == null || campaigns.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		for (CampaignModel campaign : campaigns) {
			CampaignBean map = modelMapper.map(campaign, CampaignBean.class);
			CampaignBean bean = addParamToCampaign(map, campaign.getId());
			beans.add(bean);
		}
		
		return beans;
	}
	
	/**
	 * 查询活动相关属性
	 * @param bean
	 * @param campaignId
	 * @return
	 */
	private CampaignBean addParamToCampaign(CampaignBean bean, String campaignId) {
		CampaignTargetModelExample example = new CampaignTargetModelExample();
		example.createCriteria().andIdEqualTo(campaignId);
		// 查询定向信息
		List<CampaignTargetModel> list = campaignTargetDao.selectByExampleWithBLOBs(example);
		if (list != null && !list.isEmpty()) {
			CampaignTargetModel model = list.get(0);
			Target target = new Target();
			target.setRegion(formatTargetStringToArray(model.getRegionId()));
			target.setAdType(formatTargetStringToArray(model.getAdType()));
			target.setTime(formatTargetStringToArray(model.getTimeId()));
			target.setNetwork(formatTargetStringToArray(model.getNetwork()));
			target.setOperator(formatTargetStringToArray(model.getOperator()));
			target.setDevice(formatTargetStringToArray(model.getDevice()));
			target.setOs(formatTargetStringToArray(model.getOs()));
			target.setBrand(formatTargetStringToArray(model.getBrandId()));
			target.setApp(formatTargetStringToArray(model.getAppId()));
			bean.setTarget(target);
		}
		// 查询频次信息
		if (!StringUtils.isEmpty(bean.getFrequencyId())) {
			FrequencyModel frequencyModel = frequencyDao.selectByPrimaryKey(bean.getFrequencyId());
			com.pxene.pap.domain.beans.CampaignBean.Frequency frequency = new com.pxene.pap.domain.beans.CampaignBean.Frequency();
			frequency.setControlObj(frequencyModel.getControlObj());
			frequency.setNumber(frequencyModel.getNumber());
			frequency.setTimeType(frequencyModel.getTimeType());
			bean.setFrequency(frequency);
		}
		// 查询检测地址
		MonitorModelExample monitorExample = new MonitorModelExample();
		monitorExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<MonitorModel> monitorList = monitorDao.selectByExample(monitorExample);
		// 如果没有查到点击展现地址数据，直接返回
		if (monitorList != null && !monitorList.isEmpty()) {
			com.pxene.pap.domain.beans.CampaignBean.Monitor[] monitors = new com.pxene.pap.domain.beans.CampaignBean.Monitor[monitorList.size()];
			for (int i=0; i<monitors.length; i++) {
				MonitorModel model = monitorList.get(i);
				monitors[i].setImpressionUrl(model.getImpressionUrl());
				monitors[i].setClickUrl(model.getClickUrl());
			}
			bean.setMonitors(monitors);
		}
		return bean;
	}
	
	/**
	 * 将字符串形式定向转成数组("1,2,3"==》[1,2,3])
	 * @param target
	 * @return
	 */
	private static String[] formatTargetStringToArray(String target){
		if(!StringUtils.isEmpty(target)){
			String [] targets = target.split(",");
			return targets;
		}
		return null;//空字符串返回null
	}
	
	/**
	 * 按照活动投放
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	public void launchCampaign(List<String> campaignIds) throws Exception {
		if (campaignIds == null || campaignIds.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (String campaignId : campaignIds) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			if (campaignModel != null) {
				String projectId = campaignModel.getProjectId();
				ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
				if (StatusConstant.PROJECT_START.equals(projectModel.getStatus())) {
					// 投放
					launch(campaignId);
				}
				//改变数据库状态
				campaignModel.setStatus(StatusConstant.CAMPAIGN_START);
				campaignDao.updateByPrimaryKeySelective(campaignModel);
			}
		}
	}
	
	/**
	 * 按照活动暂停
	 * @param campaignIds
	 * @throws Exception
	 */
	@Transactional
	public void pauseCampaign(List<String> campaignIds) throws Exception {
		if (campaignIds == null || campaignIds.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (String campaignId : campaignIds) {
			CampaignModel campaignModel = campaignDao.selectByPrimaryKey(campaignId);
			if (campaignModel != null) {
				String projectId = campaignModel.getProjectId();
				ProjectModel projectModel = projectDao.selectByPrimaryKey(projectId);
				//投放中的才变成暂停
				if (StatusConstant.CAMPAIGN_START.equals(campaignModel.getStatus())
						&& StatusConstant.PROJECT_START.equals(projectModel.getStatus())) {
					//移除redis中key
					pause(campaignId);
				}
				//改变数据库状态
				campaignModel.setStatus(StatusConstant.CAMPAIGN_PAUSE);
				campaignDao.updateByPrimaryKeySelective(campaignModel);
			}
		}
	}
	
}
