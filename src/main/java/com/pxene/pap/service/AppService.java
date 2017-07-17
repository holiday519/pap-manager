package com.pxene.pap.service;

import static com.pxene.pap.constant.StatusConstant.ADVERTISER_ADX_ENABLE;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_SUCCESS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import com.pxene.pap.common.CharacterUtils;
import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.DspReviewDbUtils;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.CodeTableConstant;
import com.pxene.pap.domain.beans.CampaignBean;
import com.pxene.pap.domain.models.*;
import com.pxene.pap.repository.basic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.domain.beans.AppBean;
import com.pxene.pap.domain.models.AppModelExample.Criteria;

@Service
public class AppService extends BaseService {
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
    private CampaignDao campaignDao;
	
	@Autowired
    private ProjectDao projectDao;
	
	@Autowired
	private AdvertiserAuditDao advertiserAuditDao;

	@Autowired
	private AppTargetDao appTargetDao;

	@Autowired
	private Environment env;

	
	/**
	 * 查询app列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AppBean> listApps(String name, String campaignId) throws Exception {
		List<AppBean> result = new ArrayList<AppBean>();
		if (StringUtils.isEmpty(campaignId)) {
			AppModelExample appExample = new AppModelExample();
			if (!StringUtils.isEmpty(name)) {
				appExample.createCriteria().andAppNameLike("%" + name + "%");
			}
			List<AppModel> apps = appDao.selectByExample(appExample);
			for (AppModel model : apps) {
		        AppBean appBean = modelMapper.map(model, AppBean.class);
		        result.add(appBean);
		    }
		} else {
			// 根据活动ID查询项目ID
	        CampaignModel campaignInfo = campaignDao.selectByPrimaryKey(campaignId);
	        String projectId = campaignInfo.getProjectId();
	        // 根据项目ID查询广告主ID
	        ProjectModel projectInfo = projectDao.selectByPrimaryKey(projectId);
	        String advertiserId = projectInfo.getAdvertiserId();
	        // 根据广告主ID、广告主审核状态为审核通过、广告主Adx的状态为启用查询出全部ADX Id
	        AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
	        example.createCriteria().andAdvertiserIdEqualTo(advertiserId).andStatusEqualTo(ADVERTISER_AUDIT_SUCCESS).andEnableEqualTo(ADVERTISER_ADX_ENABLE);
	        List<AdvertiserAuditModel> advertiserAuditModels = advertiserAuditDao.selectByExample(example);
	        if (advertiserAuditModels.isEmpty()) {
	        	throw new IllegalStateException(PhrasesConstant.ADVERVISER_NOT_HAVE_ADX);
	        }
	        List<String> adxIds = new ArrayList<String>();
			if (advertiserAuditModels != null && !advertiserAuditModels.isEmpty()) {
				for (AdvertiserAuditModel advertiserAuditModel : advertiserAuditModels) {
					adxIds.add(advertiserAuditModel.getAdxId());
				}
			}
			AppModelExample appExample = new AppModelExample();
			Criteria criteria = appExample.createCriteria();
			criteria.andAdxIdIn(adxIds);
			if (!StringUtils.isEmpty(name)) {
				criteria.andAppNameLike("%" + name + "%");
			}
			List<AppModel> appModels = appDao.selectByExample(appExample);
			for (AppModel appModel : appModels)
            {
                AppBean appBean = modelMapper.map(appModel, AppBean.class);
                result.add(appBean);
            }
		}
		
		return result;
	}

	/**
	 * 根据app定向明细查询app
	 * @param appTargetDetailModels
	 * @param adx
	 * @return
     */
	public List<AppModel> listAppsByAppTargetDetail(List<AppTargetDetailModel> appTargetDetailModels,String adx){
		if(appTargetDetailModels !=null) {
			AppModelExample appModelExample = new AppModelExample();

			List<AppTargetDetailModel> includes = new ArrayList<>();//包含条件
			List<AppTargetDetailModel> excludes = new ArrayList<>();//不包含条件

			for (AppTargetDetailModel appTargetDetailModel : appTargetDetailModels) {
				if(appTargetDetailModel.getFilterType().equals(CodeTableConstant.FILTER_TYPE_INCLUDE)) {
					//包含条件
					includes.add(appTargetDetailModel);
				}else if(appTargetDetailModel.getFilterType().equals(CodeTableConstant.FILTER_TYPE_EXCLUDE)){
					//不包含条件
					excludes.add(appTargetDetailModel);
				}
			}

			boolean flag =false;
			if(includes !=null && includes.size() >0) {
				flag =true;
				for (AppTargetDetailModel appTargetDetailModel : includes) {
					Criteria criteria = getCriteriaOfAppByAppDetail(excludes,appModelExample,adx);
					if (appTargetDetailModel.getMatchType().equals(CodeTableConstant.MATCH_TYPE_EQUAL)) {//精确匹配
						criteria.andAppNameEqualTo(appTargetDetailModel.getWord());
					} else if (appTargetDetailModel.getMatchType().equals(CodeTableConstant.MATCH_TYPE_LIKE)) {//模糊匹配
						criteria.andAppNameLike("%" + appTargetDetailModel.getWord() + "%");
					}
					appModelExample.or(criteria);
				}
			}

			if(!flag){
				getCriteriaOfAppByAppDetail(excludes,appModelExample,adx);
			}

			List<AppModel> appModels = appDao.selectByExample(appModelExample);
			return appModels;
		}
		return null;
	}

	/**
	 * 获取Criteria
	 * @param excludes
	 * @param appModelExample
	 * @param adx
     * @return
     */
	public Criteria getCriteriaOfAppByAppDetail(List<AppTargetDetailModel> excludes, AppModelExample appModelExample,String adx){
		Criteria criteria = appModelExample.createCriteria();
		criteria.andAdxIdEqualTo(adx);

		if(excludes !=null && excludes.size() >0) {
			for (AppTargetDetailModel appTargetDetailModel : excludes) {
				if (appTargetDetailModel.getMatchType().equals(CodeTableConstant.MATCH_TYPE_EQUAL)) {//精确匹配
					criteria.andAppNameNotEqualTo(appTargetDetailModel.getWord());
				} else if (appTargetDetailModel.getMatchType().equals(CodeTableConstant.MATCH_TYPE_LIKE)) {//模糊匹配
					criteria.andAppNameNotLike("%" + appTargetDetailModel.getWord() + "%");
				}
			}
		}
		return criteria;
	}

	/**
	 * 根据app查询条件获取app个数
	 * @param target
	 * @return
	 * @throws Exception
     */
//	public int  getAppNumsByQueryCondition(CampaignBean.Target target) throws Exception {
//		if(target !=null){
//			List<String> appIds = customAppDao.selectApps(target);
//			return appIds.size();
//		}
//		return 0;
//	}

	public int  getAppNumsByQueryCondition(CampaignBean.Target target) throws Exception {
		if(target !=null){

			AppModelExample appModelExample = new AppModelExample();

			//包含条件
			CampaignBean.Target.Include[] includes = target.getInclude();
			boolean flag =false;
			if(includes !=null && includes.length >0) {
				flag =true;
				for (CampaignBean.Target.Include include : includes) {
					Criteria criteria = getAndCriteriaOfApp(target,appModelExample);
					if (include.getType().equals(CodeTableConstant.MATCH_TYPE_EQUAL)) {//精确匹配
						criteria.andAppNameEqualTo(include.getWord());

					} else if (include.getType().equals(CodeTableConstant.MATCH_TYPE_LIKE)) {//模糊匹配
						criteria.andAppNameLike("%" + include.getWord() + "%");

					}
					appModelExample.or(criteria);
				}
			}

			if(!flag){
				getAndCriteriaOfApp(target,appModelExample);
			}


			List<AppModel> appModels = appDao.selectByExample(appModelExample);
			return appModels.size();
		}
		return 0;
	}

	/**
	 * 获取Criteria
	 * @param target
	 * @param appModelExample
     * @return
     */
	public Criteria getAndCriteriaOfApp(CampaignBean.Target target, AppModelExample appModelExample){
		Criteria criteria = appModelExample.createCriteria();
		criteria.andAdxIdEqualTo(target.getAdx());
		CampaignBean.Target.Exclude[] excludes = target.getExclude();
		if(excludes !=null && excludes.length >0) {
			for (CampaignBean.Target.Exclude exclude : excludes) {
				if (exclude.getType().equals(CodeTableConstant.MATCH_TYPE_EQUAL)) {//精确匹配
					criteria.andAppNameNotEqualTo(exclude.getWord());
				} else if (exclude.getType().equals(CodeTableConstant.MATCH_TYPE_LIKE)) {//模糊匹配
					criteria.andAppNameNotLike("%" + exclude.getWord() + "%");
				}
			}
		}
		return criteria;
	}

	/**
	 * 根据appIds得到app
	 * @param appIds
	 * @return
     */
	public List<AppModel> getAppModelByAppIds(List<String> appIds){
		AppModelExample appModelExample = new AppModelExample();
		appModelExample.createCriteria().andIdIn(appIds);
		List<AppModel> appModels = appDao.selectByExample(appModelExample);
		return appModels;
	}

	/**
	 * 根据单个活动id得到它下面的app信息
	 * @param campaignId
	 * @return
     */
//	public List<AppModel> getAppByCampaignId(String campaignId){
//		//先根据活动id获取appTarget，再找到appid
//		List<AppTargetModel> appTargetModels = getAppTargetByCampaignId(campaignId);
//		if(appTargetModels != null && !appTargetModels.isEmpty()){
//			//用set对appId去重
//			Set<String> appIds_set = new HashSet<>();
//			for(AppTargetModel appTargetModel : appTargetModels){
//				appIds_set.add(appTargetModel.getAppId());
//			}
//
//			if(appIds_set.size()>0){
//				//将set转换为list
//				List<String> appIds_list = new ArrayList<>();
//				appIds_list.addAll(appIds_set);
//				//获取app信息
//				List<AppModel> appModels = getAppModelByAppIds(appIds_list);
//				return appModels;
//			}
//		}
//
//		return null;
//	}

	/**
	 * 根据单个活动id获取appTarget
	 * @param campaignId
	 * @return
     */
	public List<AppTargetModel> getAppTargetByCampaignId(String campaignId){
		AppTargetModelExample appTargetModelExample = new AppTargetModelExample();
		appTargetModelExample.createCriteria().andCampaignIdEqualTo(campaignId);
		List<AppTargetModel> appTargetModels = appTargetDao.selectByExample(appTargetModelExample);
		return appTargetModels;
	}

	/**
	 * 每天7点，定时执行app信息同步，同步前一天的数据
	 *
     */
	@Scheduled(cron = "0 0 5 * * ?")
	public void synApp2Mysql_timeTask(){
		//获取前一天的时间
		String sysDate = DateUtils.getDayOfChange(new Date(),-1);
		synApp2Mysql(sysDate);
	}

	/**
	 * 把app信息从dsp hadoop库中增量同步到pap数据库中
	 * @param sysDate	日期 格式yyyy-MM-dd
     */
	public void synApp2Mysql(String sysDate){
		sysDate = sysDate+"%";
		Connection connection = DspReviewDbUtils.getConnection(env);
		if(connection == null){
			return ;
		}
		PreparedStatement pstmt = null;
		ResultSet rst = null;
		try {
			connection.setAutoCommit(false);
			//模糊查询指定日期的有更新或者新创建的数据
			String sql="select appid,category,parentcode,apppackage,appname,downloadurl,systemname,adxtype,updatetime,createtime " +
					" from dsp_t_app_collection_adx_odin where adxtype !='17'and updatetime like '"+sysDate+"' or createtime  like '"+sysDate+"'";
			pstmt = connection.prepareStatement(sql);
			rst = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (rst.next()) {
				String appId = rst.getString(1).trim();
				String category = rst.getString(2);
				if(category == null){
					category = "NULL";
				}
				String parentCode = rst.getString(3);
				if(category == null){
					category = "NULL";
				}
				String appPackage = rst.getString(4);
				if(appPackage == null){
					appPackage = "NULL";
				}
				String appName = rst.getString(5);
				if(appName == null){
					appName = "NULL";
				}
				String downloadUrl = rst.getString(6);
				if(downloadUrl == null){
					downloadUrl = "NULL";
				}
				String systemName = rst.getString(7);
				if(systemName == null){
					systemName = "未知";
				}
				String adxType = rst.getString(8);
				String updateTimeStr = rst.getString(9);
				String createTimeStr = rst.getString(10);
				if(updateTimeStr == null){
					updateTimeStr = "2014-01-01 00:00:00";
				}
				if(createTimeStr == null){
					createTimeStr = "2014-01-01 00:00:00";
				}
				Date updateTime = null;
				try {
					updateTime = sdf.parse(updateTimeStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date createTime = null;
				try {
					createTime = sdf.parse(createTimeStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				AppBean appBean = new AppBean();
				appBean.setAppId(appId);
				appBean.setAppType(category.trim());
				appBean.setParentType(parentCode.trim());
				appBean.setPkgName(appPackage.trim());
				appBean.setAppName(appName.trim());
				appBean.setDownloadUrl(downloadUrl.trim());
				appBean.setOsType(systemName.trim());
				appBean.setAdxId(adxType);
				appBean.setUpdateTime(updateTime);
				appBean.setCreateTime(createTime);
				//检查是否符合要求
				boolean res = checkApp(appBean);
				if(res) {
					//根据appid和adx去数据库中查找
					AppModelExample queryApp = new AppModelExample();
					queryApp.createCriteria().andAppIdEqualTo(appId).andAdxIdEqualTo(adxType);
					List<AppModel> appModels = appDao.selectByExample(queryApp);

					if(appModels!=null && !appModels.isEmpty()){	//有则更新
						AppModel appModel_db = appModels.get(0);
						//pap数据库中的更新时间小于dsp的，就更新
						if(appModel_db.getUpdateTime().getTime() < updateTime.getTime()) {
							appBean.setId(appModel_db.getId());
							AppModel appModel = modelMapper.map(appBean, AppModel.class);
							appDao.updateByPrimaryKey(appModel);
						}
					}else{	//没有则插入
						appBean.setId(UUIDGenerator.getUUID());
						AppModel appModel =modelMapper.map(appBean, AppModel.class);
						appDao.insertSelective(appModel);
					}
				}

			}
			//关闭数据库连接
			DspReviewDbUtils.close(connection,pstmt,rst);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查app是否合法，符合要求返回true
	 * @param appBean
	 * @return
     */
	private boolean checkApp(AppBean appBean) {
		//appid过滤
		if (appBean.getAppId().length() > 100 || CharacterUtils.containsEmoji(appBean.getAppId()) || appBean.getAppId().equals("") || appBean.getAppId().equalsIgnoreCase("NULL") || CharacterUtils.isGarbledCode(appBean.getAppId()) || CharacterUtils.checkfirstChar(appBean.getAppId())) {
			return false;
		}
		//category
		if (appBean.getAppType().length() > 20 || CharacterUtils.containsEmoji(appBean.getAppType())) {
			return false;
		}
		//apppackage
		if (appBean.getPkgName().length() > 200 || CharacterUtils.containsEmoji(appBean.getPkgName())) {
			return false;
		}
		//appname
		if (appBean.getAppName().length() > 200 || CharacterUtils.containsEmoji(appBean.getAppName()) || CharacterUtils.isMessyCode(appBean.getAppName())) {
			return false;
		}
		//downloadurl
		if (appBean.getDownloadUrl().length() > 300 || CharacterUtils.containsEmoji(appBean.getDownloadUrl())) {
			return false;
		}
		//systemname 转换
		String systemname = "04";
		if (CharacterUtils.containsEmoji(appBean.getOsType()) || CharacterUtils.isMessyCode(appBean.getOsType())) {
			systemname = "04";
		} else {
			String[] systems = systemname.split(",");
			String os = "";
			if (systems.length == 1) {
				if (systems[0].equalsIgnoreCase("ios")) {
					os = "01";
				} else if (systems[0].equalsIgnoreCase("android") || systems[0].equalsIgnoreCase("Adnroid")) {
					os = "02";
				} else if (systems[0].equalsIgnoreCase("IOS&Android")) {
					os = "03";
				} else {
					os = "04";
				}
			} else {
				if (systemname.contains("ios") && systemname.contains("android")) {
					os = "03";
				} else if (systemname.contains("ios") && !systemname.contains("android")) {
					os = "01";
				} else if (!systemname.contains("ios") && systemname.contains("android")) {
					os = "02";
				} else {
					os = "04";
				}

			}
			systemname = os;
		}
		appBean.setOsType(systemname);
		return true;
	}

	/**
	 * 手动同步app信息
	 * @return
     */
	public boolean synAppInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		synApp2Mysql(date);
		return true;
	}

}
