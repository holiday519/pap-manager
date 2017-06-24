package com.pxene.pap.service;

import static com.pxene.pap.constant.StatusConstant.ADVERTISER_ADX_ENABLE;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_SUCCESS;

import java.util.*;

import javax.transaction.Transactional;

import com.pxene.pap.domain.models.*;
import com.pxene.pap.repository.basic.*;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<AppModel> getAppByCampaignId(String campaignId){
		//先根据活动id获取appTarget，再找到appid
		List<AppTargetModel> appTargetModels = getAppTargetByCampaignId(campaignId);
		if(appTargetModels != null && !appTargetModels.isEmpty()){
			//用set对appId去重
			Set<String> appIds_set = new HashSet<>();
			for(AppTargetModel appTargetModel : appTargetModels){
				appIds_set.add(appTargetModel.getAppId());
			}

			if(appIds_set.size()>0){
				//将set转换为list
				List<String> appIds_list = new ArrayList<>();
				appIds_list.addAll(appIds_set);
				//获取app信息
				List<AppModel> appModels = getAppModelByAppIds(appIds_list);
				return appModels;
			}
		}

		return null;
	}

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
}
