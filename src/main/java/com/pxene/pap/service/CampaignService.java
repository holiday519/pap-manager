package com.pxene.pap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.MonitorBean;
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
import com.pxene.pap.domain.model.basic.RegionTargetModel;
import com.pxene.pap.domain.model.basic.RegionTargetModelExample;
import com.pxene.pap.domain.model.basic.TimeTargetModel;
import com.pxene.pap.domain.model.basic.TimeTargetModelExample;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModel;
import com.pxene.pap.domain.model.basic.view.CampaignTargetModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalArgumentException;
import com.pxene.pap.exception.NotFoundException;
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
import com.pxene.pap.repository.basic.RegionTargetDao;
import com.pxene.pap.repository.basic.TimeTargetDao;
import com.pxene.pap.repository.basic.view.CampaignTargetDao;

@Service
public class CampaignService extends BaseService{
	
	@Autowired
	private CampaignDao campaignDao; 
	
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
	public void createCampaign(CampaignBean bean) throws Exception {
		
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		String id = UUID.randomUUID().toString();
		bean.setId(id);//此处放入ID，添加活动相关联信息时用到
		campaignModel.setId(id);
		
		try {
				
			String frequencyId = UUID.randomUUID().toString();
			String controlObj = bean.getControlObj();
			String timeType = bean.getTimeType();
			int frequency = bean.getFrequency();
			//添加频次信息
			FrequencyModel frequencyModel = new FrequencyModel();
			frequencyModel.setId(frequencyId);
			frequencyModel.setControlObj(controlObj);
			frequencyModel.setTimeType(timeType);
			frequencyModel.setFrequency(frequency);
			frequencyDao.insertSelective(frequencyModel);
			//添加定向信息
			addCampaignTarget(bean);
			//添加点击、展现监测地址
			addCampaignMonitor(bean);
			//添加频次ID
			campaignModel.setFrequencyId(frequencyId);
			
			//添加活动基本信息
			campaignDao.insertSelective(campaignModel);
			
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(campaignModel, bean);
	}
	
	/**
	 * 编辑活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void updateCampaign(String id ,CampaignBean bean) throws Exception {
		
		if (!StringUtils.isEmpty(bean.getId())) {
			throw new IllegalArgumentException();
		}
		
		CampaignModel campaignInDB = campaignDao.selectByPrimaryKey(id);
		if (campaignInDB == null || StringUtils.isEmpty(campaignInDB.getId())) {
			throw new NotFoundException();
		}
		
		bean.setId(id);//bean中放入ID，用于更新关联关系表中数据
		CampaignModel campaignModel = modelMapper.map(bean, CampaignModel.class);
		
		try {
			//频次数据
			String frequencyId = bean.getFrequencyId();
			String controlObj = bean.getControlObj();
			String timeType = bean.getTimeType();
			int frequency = bean.getFrequency();
			//修改频次信息
			FrequencyModel frequencyModel = new FrequencyModel();
			frequencyModel.setId(frequencyId);
			frequencyModel.setControlObj(controlObj);
			frequencyModel.setTimeType(timeType);
			frequencyModel.setFrequency(frequency);
			frequencyDao.updateByPrimaryKeySelective(frequencyModel);
			
			//删除定向信息
			deleteCampaignTarget(id);
			//重新添加定向信息
			addCampaignTarget(bean);
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
	 * 添加活动定向
	 * @param bean
	 */
	@Transactional
	public void addCampaignTarget(CampaignBean bean) throws Exception {
		String id = bean.getId();
		List<String> regionTarget = bean.getRegionTarget();//地域
		List<String> adTypeTarget = bean.getAdtypeTarget();//广告类型
		List<String> timeTarget = bean.getTimeTarget();//时间
		List<String> networkTarget = bean.getNetworkTarget();//网络
		List<String> operatorTarget = bean.getOperatorTarget();//运营商
		List<String> deviceTarget = bean.getDeviceTarget();//设备
		List<String> osTarget = bean.getOsTarget();//系统
		List<String> brandTarget = bean.getBrandTarget();//品牌
		List<String> appTarget = bean.getAppTarget();//app
		if (regionTarget != null && !regionTarget.isEmpty()) {
			RegionTargetModel region;
			for (String regionId : regionTarget) {
				region = new RegionTargetModel();
				region.setId(UUID.randomUUID().toString());
				region.setCampaignId(id);
				region.setRegionId(regionId);
				regionTargetDao.insertSelective(region);
			}
		}
		if (adTypeTarget != null && !adTypeTarget.isEmpty()) {
			AdTypeTargetModel adType;
			for (String adTypeId : adTypeTarget) {
				adType = new AdTypeTargetModel();
				adType.setId(UUID.randomUUID().toString());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetDao.insertSelective(adType);
			}
		}
		if (timeTarget != null && !timeTarget.isEmpty()) {
			TimeTargetModel time;
			for (String timeId : timeTarget) {
				time = new TimeTargetModel();
				time.setId(UUID.randomUUID().toString());
				time.setCampaignId(id);
				time.setTimeId(timeId);
				timeTargetDao.insertSelective(time);
			}
		}
		if (networkTarget != null && !networkTarget.isEmpty()) {
			NetworkTargetModel network;
			for (String networkid : networkTarget) {
				network = new NetworkTargetModel();
				network.setId(UUID.randomUUID().toString());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetDao.insertSelective(network);
			}
		}
		if (operatorTarget != null && !operatorTarget.isEmpty()) {
			OperatorTargetModel operator;
			for (String operatorId : operatorTarget) {
				operator = new OperatorTargetModel();
				operator.setId(UUID.randomUUID().toString());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetDao.insertSelective(operator);
			}
		}
		if (deviceTarget != null && !deviceTarget.isEmpty()) {
			DeviceTargetModel device;
			for (String deviceId : deviceTarget) {
				device = new DeviceTargetModel();
				device.setId(UUID.randomUUID().toString());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetDao.insertSelective(device);
			}
		}
		if (osTarget != null && !osTarget.isEmpty()) {
			OsTargetModel os;
			for (String osId : osTarget) {
				os = new OsTargetModel();
				os.setId(UUID.randomUUID().toString());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetDao.insertSelective(os);
			}
		}
		if (brandTarget != null && !brandTarget.isEmpty()) {
			BrandTargetModel brand;
			for (String brandId : brandTarget) {
				brand = new BrandTargetModel();
				brand.setId(UUID.randomUUID().toString());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetDao.insertSelective(brand);
			}
		}
		if (appTarget != null && !appTarget.isEmpty()) {
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
	public void addCampaignMonitor(CampaignBean bean) throws Exception {
		String id = bean.getId();
		List<MonitorBean> monitors = bean.getMonitors();
		if (monitors != null && !monitors.isEmpty()) {
			for (MonitorBean mnt : monitors) {
				List<String> urls = mnt.getUrls();
				String monitorId = UUID.randomUUID().toString();
				MonitorModel monitor = new MonitorModel();
				monitor.setId(monitorId);
				monitor.setCampaignId(id);
				if (urls != null && urls.size() > 0 && urls.get(0) != null
						&& !"".equals(urls.get(0))) {
					String impressionUrl = urls.get(0);
					monitor.setImpressionUrl((impressionUrl));
				}
				if (urls != null && urls.size() > 1 && urls.get(1) != null
						&& !"".equals(urls.get(1))) {
					String clickUrl = urls.get(1);
					monitor.setClickUrl(clickUrl);
				}
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
			throw new NotFoundException();
		}
		
		//先查询出活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> list = creativeDao.selectByExample(creativeExample);
		if (list != null && !list.isEmpty()) {
			for (CreativeModel cModel : list) {
				String creativeId = cModel.getId();
				// 调用创意删除方法（方法内会删除关联关系）
				creativeService.deleteCreative(creativeId);
			}
		}
		//删除检测地址
		deleteCampaignMonitor(campaignId);
		//删除定向
		deleteCampaignTarget(campaignId);
		//删除频次信息
		deletefrequency(campaignId);
		//删除活动
		creativeDao.deleteByPrimaryKey(campaignId);
	}
	
	/**
	 * 根据id查询活动
	 * @param campaignId
	 * @return
	 */
	public CampaignBean selectCampaign(String campaignId)  throws Exception {
		CampaignModel model = campaignDao.selectByPrimaryKey(campaignId);
		if (model ==null || StringUtils.isEmpty(model.getId())) {
        	throw new NotFoundException();
        }
		CampaignBean bean = modelMapper.map(model, CampaignBean.class);
		CampaignBean campaignBean = addParamToCampaign(bean, campaignId);
		return campaignBean;
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
			throw new NotFoundException();
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
			bean.setRegionTarget(formatTargetStringToList(model.getRegionId()));
			bean.setAdtypeTarget(formatTargetStringToList(model.getAdType()));
			bean.setTimeTarget(formatTargetStringToList(model.getTimeId()));
			bean.setNetworkTarget(formatTargetStringToList(model.getNetwork()));
			bean.setOperatorTarget(formatTargetStringToList(model.getOperator()));
			bean.setDeviceTarget(formatTargetStringToList(model.getDevice()));
			bean.setOsTarget(formatTargetStringToList(model.getOs()));
			bean.setBrandTarget(formatTargetStringToList(model.getBrandId()));
			bean.setAppTarget(formatTargetStringToList(model.getAppId()));
		}
		// 查询频次信息
		if (!StringUtils.isEmpty(bean.getFrequencyId())) {
			FrequencyModel frequency = frequencyDao.selectByPrimaryKey(bean.getFrequencyId());
			bean.setControlObj(frequency.getControlObj());
			bean.setFrequency(frequency.getFrequency());
			bean.setTimeType(frequency.getTimeType());
		}
		// 查询检测地址
		MonitorModelExample monitorExample = new MonitorModelExample();
		monitorExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<MonitorModel> monitors = monitorDao.selectByExample(monitorExample);
		//如果没有查到点击展现地址数据，直接返回
		if (monitors != null && !monitors.isEmpty()) {
			List<MonitorBean> monitorList = new ArrayList<MonitorBean>();
			for (MonitorModel mo : monitors) {
				MonitorBean monitorBean = new MonitorBean();
				List<String> mlist = new ArrayList<String>();
				mlist.add(mo.getImpressionUrl());
				mlist.add(mo.getClickUrl());
				monitorBean.setUrls(mlist);
				monitorList.add(monitorBean);
			}
			bean.setMonitors(monitorList);
		}
		return bean;
	}
	
	/**
	 * 将字符串形式定向转成list("1,2,3"==》list)
	 * @param target
	 * @return
	 */
	private static List<String> formatTargetStringToList(String target){
		if(!StringUtils.isEmpty(target)){
			String [] targets = target.split(",");
			List<String> targetList = Arrays.asList(targets);
			return targetList;
		}
		return null;//空字符串返回null
	}
}
