package com.pxene.pap.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.beans.MonitorBean;

@Repository
public class CampaignDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional
	public int createCampaignBasic(CampaignBean bean) throws Exception {
		String id = bean.getId();
		String projectId = bean.getProjectId();
		String name = bean.getName();
		String type = bean.getType();
		Integer totalBudget = bean.getTotalBudget();
		Integer totalImpression = bean.getTotalImpression();
		Integer totalClick = bean.getTotalClick();
		Integer dailyBudget = bean.getDailyBudget();
		Integer dailyImpression = bean.getDailyImpression();
		Integer dailyClick = bean.getDailyClick();
		Date startDate = bean.getStartDate();
		Date endDate = bean.getEndDate();
		String status = bean.getStatus();
		String uniform = bean.getUniform();
		
		List<MonitorBean> monitors = bean.getMonitors();
		for (MonitorBean mnt : monitors) {
			List<String> urls = mnt.getUrls();
			String monitorId = UUID.randomUUID().toString();
			String impressionUrl = urls.get(0);
			String clickUrl = urls.get(1);
			String monitorSql = "insert into pap_t_monitor (id,campaignid,impression,click) values (?,?,?,?)";
			jdbcTemplate.update(monitorSql,monitorId,id,impressionUrl,clickUrl);
		}

		// 插入活动基本信息
		String sql = "insert into pap_t_campaign (id,projectid,name,type,totalbudget,totalimpression,totalclick,"
				+ "dailybudget,dailyimpression,dailyclick,status,uniform,startdate,enddate)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int num = jdbcTemplate.update(sql, id, projectId, name, type,
				totalBudget, totalImpression, totalClick, dailyBudget,
				dailyImpression, dailyClick, status, uniform, startDate,
				endDate);
		return num;
	}

	/**
	 * 创建推广组定向信息
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public void createCampaignTarget(CampaignBean bean) throws Exception {
		String campaignid = bean.getId();
		List<String> regionTarget = bean.getRegionTarget();
		List<String> adtypeTarget = bean.getAdtypeTarget();
		List<String> timeTarget = bean.getTimeTarget();
		List<String> networkTarget = bean.getNetworkTarget();
		List<String> operatorTarget = bean.getOperatorTarget();
		List<String> deviceTarget = bean.getDeviceTarget();
		List<String> osTarget = bean.getOsTarget();
		List<String> brandTarget = bean.getBrandTarget();
		List<String> appTarget = bean.getAppTarget();
		insertTarget(regionTarget, campaignid, "pap_t_regiontarget", "regionid");
		insertTarget(adtypeTarget, campaignid, "pap_t_adtypetarget", "adtype");
		insertTarget(timeTarget, campaignid, "pap_t_timetarget", "timeid");
		insertTarget(networkTarget, campaignid, "pap_t_networktarget", "network");
		insertTarget(operatorTarget, campaignid, "pap_t_operatortarget", "operator");
		insertTarget(deviceTarget, campaignid, "pap_t_devicetarget", "device");
		insertTarget(osTarget, campaignid, "pap_t_ostarget", "os");
		insertTarget(brandTarget, campaignid, "pap_t_brandtarget", "brandid");
		insertTarget(appTarget, campaignid, "pap_t_apptarget", "appid");
	}
	
	/**
	 * 添加活动频次信息
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createCampaignFrequency(CampaignBean bean)throws Exception{
		String frequencyId = UUID.randomUUID().toString();
		String campaignId = bean.getId();
		String controlObj = bean.getControlObj();
		String timeType = bean.getTimeType();
		int frequency = bean.getFrequency();
		String frequencySql = "insert into pap_t_frequency (id,controlobj,timetype,frequency) values (?,?,?,?)";
		jdbcTemplate.update(frequencySql, frequencyId, controlObj, timeType, frequency);
		String sql = "update pap_t_campaign set frequencyid = ? where id = ?";
		jdbcTemplate.update(sql, frequencyId, campaignId);
	}
	
	/**
	 * 添加活动定向
	 * @param list  定向集合
	 * @param campaignid 活动ID
	 * @param tableName 表名
	 * @param param 对应字段名称
	 * @throws Exception
	 */
	public void insertTarget(List<String> list,String campaignId,String tableName,String param) throws Exception{
		String uuid = "";
		if (list != null && !list.isEmpty()) {
			for (String target : list) {
				uuid = UUID.randomUUID().toString();
				String sql = "insert into " + tableName + " (id,campaignid," + param + ") values(?,?,?)";
				jdbcTemplate.update(sql, uuid, campaignId, target);
			}
		}
	}

	/**
	 * 根据名称和id查询活动，用于判断名称是否重复
	 * 
	 * @param name
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int selectCampaignByNameAndId(String name, String id) throws Exception {
		// 插入项目信息
		String sql = "select t.* from pap_t_campaign t" + " where t.name = '" + name + "'";
		if (id != null) {
			sql = sql + "and t.id !=" + id;
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if (list == null || list.isEmpty()) {
			return 0;
		}
		return list.size();
	}

}
