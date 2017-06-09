package com.pxene.pap.service;

import static com.pxene.pap.constant.StatusConstant.ADVERTISER_ADX_ENABLE;
import static com.pxene.pap.constant.StatusConstant.ADVERTISER_AUDIT_SUCCESS;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pxene.pap.domain.beans.AppBean;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AppModel;
import com.pxene.pap.domain.models.AppModelExample;
import com.pxene.pap.domain.models.AppModelExample.Criteria;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.ProjectModel;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AppDao;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.ProjectDao;

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
	
	/**
	 * 查询app列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AppBean> listApps(String name, String campaignId) throws Exception {
		// REVIEW ME
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
}
