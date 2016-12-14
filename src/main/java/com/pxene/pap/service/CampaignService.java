package com.pxene.pap.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.pxene.pap.domain.model.basic.FrequencyModelExample;
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
import com.pxene.pap.repository.mapper.basic.AdTypeTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.AppTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.BrandTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.CampaignModelMapper;
import com.pxene.pap.repository.mapper.basic.CreativeModelMapper;
import com.pxene.pap.repository.mapper.basic.DeviceTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.FrequencyModelMapper;
import com.pxene.pap.repository.mapper.basic.MonitorModelMapper;
import com.pxene.pap.repository.mapper.basic.NetworkTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.OperatorTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.OsTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.RegionTargetModelMapper;
import com.pxene.pap.repository.mapper.basic.TimeTargetModelMapper;

@Service
public class CampaignService {
	
	@Autowired
	private CampaignModelMapper campaignMapper; 
	
	@Autowired
	private CreativeService creativeService; 
	
	@Autowired
	private CreativeModelMapper creativeMapper; 
	
	@Autowired
	private MonitorModelMapper monitorMapper;
	
	@Autowired
	private FrequencyModelMapper frequencyMapper;
	
	@Autowired
	private RegionTargetModelMapper regionTargetMapper;
	
	@Autowired
	private AdTypeTargetModelMapper adtypeTargetMapper;
	
	@Autowired
	private TimeTargetModelMapper timeTargetMapper;
	
	@Autowired
	private NetworkTargetModelMapper networkTargetMapper;
	
	@Autowired
	private OperatorTargetModelMapper operatorTargetMapper;
	
	@Autowired
	private DeviceTargetModelMapper deviceTargetMapper;
	
	@Autowired
	private OsTargetModelMapper osTargetMapper;
	
	@Autowired
	private BrandTargetModelMapper brandTargetMapper;
	
	@Autowired
	private AppTargetModelMapper appTargetMapper;
	
	/**
	 * 创建活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String createCampaign(CampaignBean bean) throws Exception {
		
		String name = bean.getName();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andNameEqualTo(name);
		List<CampaignModel> list = campaignMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return "活动名称重复";
		}
		String id = UUID.randomUUID().toString();
		bean.setId(id);
		String projectId = bean.getProjectId();
		String type = bean.getType();
		Integer totalBudget = bean.getTotalBudget();
		Integer dailyBudget = bean.getDailyBudget();
		Integer dailyImpression = bean.getDailyImpression();
		Integer dailyClick = bean.getDailyClick();
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		String frequencyId = UUID.randomUUID().toString();
		String controlObj = bean.getControlObj();
		String timeType = bean.getTimeType();
		int frequency = bean.getFrequency();
		//频次信息
		FrequencyModel frequencyModel = new FrequencyModel();
		frequencyModel.setId(frequencyId);
		frequencyModel.setControlObj(controlObj);
		frequencyModel.setTimeType(timeType);
		frequencyModel.setFrequency(frequency);
		frequencyMapper.insertSelective(frequencyModel);
		//定向信息
		addCampaignTarget(bean);
		//点击、展现监测地址
		addCampaignMonitor(bean);
		//基本信息
		CampaignModel campaign = new CampaignModel();
		campaign.setId(id);
		campaign.setProjectId(projectId);
		campaign.setName(name);
		campaign.setType(type);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		campaign.setTotalBudget(totalBudget);
		campaign.setDailyBudget(dailyBudget);
		campaign.setDailyImpression(dailyImpression);
		campaign.setDailyClick(dailyClick);
		campaign.setStatus("00");
		campaign.setFrequencyId(frequencyId);
		int basicNum = campaignMapper.insertSelective(campaign);

		if (basicNum > 0) {
			return id;
		}else{
			return "活动创建失败";
		}
	}
	
	/**
	 * 编辑活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public String updateCampaign(CampaignBean bean) throws Exception {
		
		String id = bean.getId();
		String name = bean.getName();
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
		List<CampaignModel> list = campaignMapper.selectByExample(example);
		if (!list.isEmpty()) {
			return "活动名称重复";
		}
		String projectId = bean.getProjectId();
		String type = bean.getType();
		Integer totalBudget = bean.getTotalBudget();
		Integer dailyBudget = bean.getDailyBudget();
		Integer dailyImpression = bean.getDailyImpression();
		Integer dailyClick = bean.getDailyClick();
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		String frequencyId = bean.getFrequencyId();
		String controlObj = bean.getControlObj();
		String timeType = bean.getTimeType();
		String status = bean.getStatus();
		int frequency = bean.getFrequency();
		//修改频次信息
		FrequencyModel frequencyModel = new FrequencyModel();
		frequencyModel.setId(frequencyId);
		frequencyModel.setControlObj(controlObj);
		frequencyModel.setTimeType(timeType);
		frequencyModel.setFrequency(frequency);
		frequencyMapper.updateByPrimaryKeySelective(frequencyModel);
		
		//删除定向信息
		deletCampaignTarget(id);
		//重新添加定向信息
		addCampaignTarget(bean);
		//删除点击、展现监测地址
		deleteCampaignMonitor(id);
		//重新添加点击、展现监测地址
		addCampaignMonitor(bean);
		//修改基本信息
		CampaignModel campaign = new CampaignModel();
		campaign.setId(id);
		campaign.setProjectId(projectId);
		campaign.setName(name);
		campaign.setType(type);
		campaign.setStartDate(startDate);
		campaign.setEndDate(endDate);
		campaign.setTotalBudget(totalBudget);
		campaign.setDailyBudget(dailyBudget);
		campaign.setDailyImpression(dailyImpression);
		campaign.setDailyClick(dailyClick);
		campaign.setStatus(status);
		campaign.setFrequencyId(frequencyId);
		int basicNum = campaignMapper.updateByPrimaryKeySelective(campaign);
		if (basicNum > -1) {
			return id;
		}else{
			return "活动编辑失败";
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
				regionTargetMapper.insertSelective(region);
			}
		}
		if (adTypeTarget != null && !adTypeTarget.isEmpty()) {
			AdTypeTargetModel adType;
			for (String adTypeId : adTypeTarget) {
				adType = new AdTypeTargetModel();
				adType.setId(UUID.randomUUID().toString());
				adType.setCampaignId(id);
				adType.setAdType(adTypeId);
				adtypeTargetMapper.insertSelective(adType);
			}
		}
		if (timeTarget != null && !timeTarget.isEmpty()) {
			TimeTargetModel time;
			for (String timeId : timeTarget) {
				time = new TimeTargetModel();
				time.setId(UUID.randomUUID().toString());
				time.setCampaignId(id);
				time.setTimeId(timeId);
				timeTargetMapper.insertSelective(time);
			}
		}
		if (networkTarget != null && !networkTarget.isEmpty()) {
			NetworkTargetModel network;
			for (String networkid : networkTarget) {
				network = new NetworkTargetModel();
				network.setId(UUID.randomUUID().toString());
				network.setCampaignId(id);
				network.setNetwork(networkid);
				networkTargetMapper.insertSelective(network);
			}
		}
		if (operatorTarget != null && !operatorTarget.isEmpty()) {
			OperatorTargetModel operator;
			for (String operatorId : operatorTarget) {
				operator = new OperatorTargetModel();
				operator.setId(UUID.randomUUID().toString());
				operator.setCampaignId(id);
				operator.setOperator(operatorId);
				operatorTargetMapper.insertSelective(operator);
			}
		}
		if (deviceTarget != null && !deviceTarget.isEmpty()) {
			DeviceTargetModel device;
			for (String deviceId : deviceTarget) {
				device = new DeviceTargetModel();
				device.setId(UUID.randomUUID().toString());
				device.setCampaignId(id);
				device.setDevice(deviceId);
				deviceTargetMapper.insertSelective(device);
			}
		}
		if (osTarget != null && !osTarget.isEmpty()) {
			OsTargetModel os;
			for (String osId : osTarget) {
				os = new OsTargetModel();
				os.setId(UUID.randomUUID().toString());
				os.setCampaignId(id);
				os.setOs(osId);
				osTargetMapper.insertSelective(os);
			}
		}
		if (brandTarget != null && !brandTarget.isEmpty()) {
			BrandTargetModel brand;
			for (String brandId : brandTarget) {
				brand = new BrandTargetModel();
				brand.setId(UUID.randomUUID().toString());
				brand.setCampaignId(id);
				brand.setBrandId(brandId);
				brandTargetMapper.insertSelective(brand);
			}
		}
		if (appTarget != null && !appTarget.isEmpty()) {
			AppTargetModel app;
			for (String appId : appTarget) {
				app = new AppTargetModel();
				app.setId(UUID.randomUUID().toString());
				app.setCampaignId(id);
				app.setAppId(appId);
				appTargetMapper.insertSelective(app);
			}
		}
	}
	
	/**
	 * 删除活动定向
	 * @param campaignId
	 */
	@Transactional
	public void deletCampaignTarget(String campaignId)  throws Exception {
		//删除地域定向
		RegionTargetModelExample region = new RegionTargetModelExample();
		region.createCriteria().andCampaignIdEqualTo(campaignId);
		regionTargetMapper.deleteByExample(region);
		//删除广告类型定向
		AdTypeTargetModelExample adType = new AdTypeTargetModelExample();
		adType.createCriteria().andCampaignIdEqualTo(campaignId);
		adtypeTargetMapper.deleteByExample(adType);
		//删除时间定向
		TimeTargetModelExample time = new TimeTargetModelExample();
		time.createCriteria().andCampaignIdEqualTo(campaignId);
		timeTargetMapper.deleteByExample(time);
		//删除网络定向
		NetworkTargetModelExample network = new NetworkTargetModelExample();
		network.createCriteria().andCampaignIdEqualTo(campaignId);
		networkTargetMapper.deleteByExample(network);
		//删除运营商定向
		OperatorTargetModelExample op = new OperatorTargetModelExample();
		op.createCriteria().andCampaignIdEqualTo(campaignId);
		operatorTargetMapper.deleteByExample(op);
		//删除设备定向
		DeviceTargetModelExample device = new DeviceTargetModelExample();
		device.createCriteria().andCampaignIdEqualTo(campaignId);
		deviceTargetMapper.deleteByExample(device);
		//删除系统定向
		OsTargetModelExample os = new OsTargetModelExample();
		os.createCriteria().andCampaignIdEqualTo(campaignId);
		osTargetMapper.deleteByExample(os);
		//删除品牌定向
		BrandTargetModelExample brand = new BrandTargetModelExample();
		brand.createCriteria().andCampaignIdEqualTo(campaignId);
		brandTargetMapper.deleteByExample(brand);
		//删除APP定向
		AppTargetModelExample app = new AppTargetModelExample();
		app.createCriteria().andCampaignIdEqualTo(campaignId);
		appTargetMapper.deleteByExample(app);
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
					monitor.setImpression(impressionUrl);
				}
				if (urls != null && urls.size() > 1 && urls.get(1) != null
						&& !"".equals(urls.get(1))) {
					String clickUrl = urls.get(1);
					monitor.setClick(clickUrl);
				}
				monitorMapper.insertSelective(monitor);
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
		monitorMapper.deleteByExample(example);
	}
	
	/**
	 * 删除频次信息
	 * @param campaignId
	 * @throws Exception
	 */
	@Transactional
	public void deletefrequency(String campaignId) throws Exception {
		CampaignModel campaignModel = campaignMapper.selectByPrimaryKey(campaignId);
		frequencyMapper.deleteByPrimaryKey(campaignModel.getFrequencyId());
	}
	
	/**
	 * 删除活动
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int deleteCampaign(String campaignId) throws Exception {
		//先查询出活动下创意
		CreativeModelExample creativeExample = new CreativeModelExample();
		creativeExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<CreativeModel> list = creativeMapper.selectByExample(creativeExample);
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
		deletCampaignTarget(campaignId);
		//删除频次信息
		deletefrequency(campaignId);
		//删除活动
		int num = creativeMapper.deleteByPrimaryKey(campaignId);
		return num;
	}
	
		
}
