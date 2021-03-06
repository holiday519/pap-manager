package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.springframework.util.StringUtils;

import com.pxene.pap.exception.IllegalArgumentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxene.pap.common.DateUtils;
import com.pxene.pap.common.UUIDGenerator;
import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.LandpageBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.LandpageCodeHistoryModel;
import com.pxene.pap.domain.models.LandpageCodeHistoryModelExample;
import com.pxene.pap.domain.models.LandpageCodeModel;
import com.pxene.pap.domain.models.LandpageCodeModelExample;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.LandpageModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.LandpageCodeDao;
import com.pxene.pap.repository.basic.LandpageCodeHistoryDao;
import com.pxene.pap.repository.basic.LandpageDao;
import com.pxene.pap.repository.custom.CustomLandpageDao;

@Service
public class LandpageService extends BaseService {

	@Autowired
	private LandpageDao landpageDao;

	@Autowired
	private CampaignDao campaignDao;
	
	@Autowired
	private LandpageCodeDao landpageCodeDao;
	
	@Autowired
	private CustomLandpageDao customLandpageDao;
	
	@Autowired
	private LandpageCodeHistoryDao landpageCodeHistoryDao;

	private static final String HTTP_ACCEPT = "accept";

	private static final String HTTP_ACCEPT_DEFAULT = "*/*";

	private static final String HTTP_USER_AGENT = "user-agent";

	private static final String HTTP_USER_AGENT_VAL = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

	private static final String HTTP_CONNECTION = "connection";

	private static final String HTTP_CONNECTION_VAL = "Keep-Alive";

	/**
	 * 监测脚本地址
	 */
	public static final String MONITOR_URL = "//192.168.3.93/pap/pxene.js";
    /**
     * 监测代码片段_开始
     */
    private static final String CODE_START = "<script>var_pxe=_pxe||[];var_pxe_id='";
    /**
     * 监测代码片段_结束
     */
    private static final String CODE_END = "';(function(){varpxejs=document.createElement('script');var_pxejsProtocol=(('https:'==document.location.protocol)?'https://':'http://');pxejs.src=_pxejsProtocol+'"+MONITOR_URL+"';varone=document.getElementsByTagName('script')[0];one.parentNode.insertBefore(pxejs,one);})();</script>";
    /**
     * HTML中头部开始标签
     */
    private static final String HTML_HEAD_START = "<HEAD>";
    
    /**
     * HTML中头部结束标签
     */
    private static final String HTML_HEAD_END = "</HEAD>";
	
	/**
	 * 添加落地页
	 * 
	 * @param bean
	 * @throws Exception
	 */
	@Transactional
	public void createLandpage(LandpageBean bean) throws Exception {
		//验证名称重复
		LandpageModelExample landpageExample = new LandpageModelExample();
		landpageExample.createCriteria().andNameEqualTo(bean.getName());
		List<LandpageModel> landpages = landpageDao.selectByExample(landpageExample);
		if (landpages != null && !landpages.isEmpty()) {
			throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
		}
		
		LandpageModel landpage = modelMapper.map(bean, LandpageModel.class);
		String id = UUIDGenerator.getUUID();
		landpage.setId(id);
		landpage.setStatus(StatusConstant.LANDPAGE_CHECK_NOTCHECK);
		landpageDao.insertSelective(landpage);
		BeanUtils.copyProperties(landpage, bean);
		
		// 插入落地页监测码
		String[] codes = bean.getCodes();
		if (codes != null)
		{
		    LandpageCodeModel landPageRecord = null;
		    for (String code : codes)
		    {
		        landPageRecord = new LandpageCodeModel();
		        landPageRecord.setId(UUIDGenerator.getUUID());
		        landPageRecord.setLandpageId(id);
		        landPageRecord.setCode(code);
		        landpageCodeDao.insert(landPageRecord);
		    }
		}
	}

	/**
	 * 修改落地页
	 * 
	 * @param id
	 * @param bean
	 */
	@Transactional
	public void updateLandpage(String id, LandpageBean bean) throws Exception {

		//先判断有没有活动在使用，有则不让修改
		CampaignModelExample campaginEx = new CampaignModelExample();
		campaginEx.createCriteria().andLandpageIdEqualTo(id);
		List<CampaignModel> campaigns = campaignDao.selectByExample(campaginEx);
		if (campaigns != null && !campaigns.isEmpty()) {
			throw new IllegalArgumentException(PhrasesConstant.LANDPAGE_USED_ERROR_CAMPAIGNID_USE);
		}

		LandpageModel landpageInDB = landpageDao.selectByPrimaryKey(id);
		if (landpageInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		} else {
			String nameInDB = landpageInDB.getName();
			String name = bean.getName();
			if (!nameInDB.equals(name)) {
				// 验证名称重复，排除自己使得同一落地页可用字母大小写不同的同一名称
				LandpageModelExample landpageExample = new LandpageModelExample();
				landpageExample.createCriteria().andNameEqualTo(name).andIdNotEqualTo(id);
				List<LandpageModel> landpages = landpageDao.selectByExample(landpageExample);
				if (landpages != null && !landpages.isEmpty()) {
					throw new DuplicateEntityException(PhrasesConstant.NAME_NOT_REPEAT);
				}
			}
		}

		LandpageModel landpage = modelMapper.map(bean, LandpageModel.class);
		landpage.setId(id);
		landpageDao.updateByPrimaryKey(landpage);
		
        String[] codes = bean.getCodes();
        
        // 删除落地页监测码
        LandpageCodeModelExample landpageCodeModelExample = new LandpageCodeModelExample();
        landpageCodeModelExample.createCriteria().andLandpageIdEqualTo(id);
        landpageCodeDao.deleteByExample(landpageCodeModelExample);
        
        // 重新添加落地页监测码
        if (codes != null)
        {
            LandpageCodeModel landPageRecord = null;
            for (String code : codes)
            {
                landPageRecord = new LandpageCodeModel();
                landPageRecord.setId(UUIDGenerator.getUUID());
                landPageRecord.setLandpageId(id);
                landPageRecord.setCode(code);
                landpageCodeDao.insert(landPageRecord);
            }
        }                
	}

	/**
	 * 删除落地页
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteLandpage(String id) throws Exception {
		LandpageModel landpageInDB = landpageDao.selectByPrimaryKey(id);
		if (landpageInDB == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andLandpageIdEqualTo(id);
		List<CampaignModel> campaigns = campaignDao.selectByExample(example);
		if (campaigns == null || !campaigns.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN);
		}
		
		// 删除落地页
		landpageDao.deleteByPrimaryKey(id);
		
		// 删除落地页监测码
        LandpageCodeModelExample landpageCodeModelExample = new LandpageCodeModelExample();
        landpageCodeModelExample.createCriteria().andLandpageIdEqualTo(id);
        landpageCodeDao.deleteByExample(landpageCodeModelExample);
	}
	/**
	 * 批量删除落地页
	 * @param ids
	 * @throws Exception
	 */
	@Transactional
	public void deleteLandpages(String[] ids) throws Exception {
		if(ids.length ==0){
			throw new IllegalArgumentException(PhrasesConstant.LACK_NECESSARY_PARAM);
		}
		LandpageModelExample landpageExample = new LandpageModelExample();
		landpageExample.createCriteria().andIdIn(Arrays.asList(ids));
		
		List<LandpageModel> landpageInDB = landpageDao.selectByExample(landpageExample);
		if (landpageInDB == null || landpageInDB.size() < ids.length) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		for (String id : ids) {
			CampaignModelExample campaignExample = new CampaignModelExample();
			campaignExample.createCriteria().andLandpageIdEqualTo(id);
			List<CampaignModel> campaigns = campaignDao.selectByExample(campaignExample);
			// 不是投放中才可以删除
			if (campaigns == null || !campaigns.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN);
			}
		}
		// 删除落地页
		landpageDao.deleteByExample(landpageExample);
		
		// 删除落地页监测码
        LandpageCodeModelExample landpageCodeModelExample = new LandpageCodeModelExample();
        landpageCodeModelExample.createCriteria().andLandpageIdIn(Arrays.asList(ids));
        landpageCodeDao.deleteByExample(landpageCodeModelExample);
	}

	/**
	 * 根据Id查询落地页
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public LandpageBean getLandpage(String id) throws Exception {
		/*
	    LandpageModel landpageModel = landpageDao.selectByPrimaryKey(id);
		if (landpageModel == null) {
			throw new ResourceNotFoundException();
		}
		LandpageBean bean = modelMapper.map(landpageModel, LandpageBean.class);
		*/
	    Map<String, String> map = customLandpageDao.selectLandPagesByPrimaryKey(id);

		LandpageBean landpageModel = parseFromMap(map);
		
		if (landpageModel == null) 
		{
            throw new ResourceNotFoundException();
        }
		
        return landpageModel;
	}
	/**
	 * 查询落地页列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<LandpageBean> listLandpages(String name) throws Exception {
		/*LandpageModelExample example = new LandpageModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		
		// 按更新时间降序排序
		example.setOrderByClause("update_time DESC");
		
		List<LandpageBean> list = new ArrayList<LandpageBean>();
		List<LandpageModel> models = landpageDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (LandpageModel model : models) {
			list.add(modelMapper.map(model, LandpageBean.class));
		}*/
		
	    List<LandpageBean> beans = new ArrayList<LandpageBean>();
		List<Map<String, String>> models = customLandpageDao.selectLandPages();
		
//		if (models == null || models.isEmpty())
//		{
//		    throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
//		}
		
		for (Map<String, String> model : models)
        {
		    LandpageBean bean = parseFromMap(model);
		    beans.add(bean);
        }
		
		return beans;
	}

    private LandpageBean parseFromMap(Map<String, String> item)
    {
        LandpageBean bean = new LandpageBean();
        bean.setId(item.get("id"));
        bean.setName(item.get("name"));
        bean.setUrl(item.get("url"));
        bean.setMonitorUrl(item.get("monitor_url"));
        bean.setStatus(item.get("remark"));
        
        String codes = item.get("monitor_codes");
        if (!StringUtils.isEmpty(codes))
        {
            bean.setCodes(codes.split(","));
        }
        return bean;
    }
	/**
	 * 检查落地页中监测代码安装状态
	 * @param landpageId
	 * @throws Exception
	 */
    @Transactional
	public String checkCode(String landpageId) throws Exception {
		LandpageModel model = landpageDao.selectByPrimaryKey(landpageId);
		if (model == null) {
			throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
		}
		try {
			String url = model.getUrl();
			String reCode = "";
			BufferedReader in = null;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty(HTTP_ACCEPT, HTTP_ACCEPT_DEFAULT);
			connection.setRequestProperty(HTTP_CONNECTION, HTTP_CONNECTION_VAL);
			connection.setRequestProperty(HTTP_USER_AGENT, HTTP_USER_AGENT_VAL);
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				reCode += line;
			}
			
			if (in != null) {
				in.close();
			}
			String codeStatus = StatusConstant.LANDPAGE_CHECK_NOTCHECK;
			int headStart = reCode.toUpperCase().indexOf(HTML_HEAD_START);
			int headEnd = reCode.toUpperCase().indexOf(HTML_HEAD_END);
			if (headStart > -1
					&& headEnd > -1) {
				reCode = reCode.substring(headStart,
						headEnd).replaceAll(" ", "")
						.replaceAll("\"", "'").replaceAll("\n", "").replace("\t", "");
				if (reCode.indexOf(CODE_START + landpageId
						+ CODE_END) > -1) {
					codeStatus = StatusConstant.LANDPAGE_CHECK_SUCCESS;
				} else {
					codeStatus = StatusConstant.LANDPAGE_CHECK_ERROR;
				}
			} else {
				codeStatus = StatusConstant.LANDPAGE_CHECK_ERROR;
			}
			model.setStatus(codeStatus);
			landpageDao.updateByPrimaryKeySelective(model);
			return codeStatus;
		} catch (Exception e) {
			String codeStatus = StatusConstant.LANDPAGE_CHECK_ERROR;
			model.setStatus(codeStatus);
			landpageDao.updateByPrimaryKeySelective(model);
			return codeStatus;
		}
		
	}
    
    /**
     * 向监测码历史表插入数据
     * @param campaignId 活动id
     * @param landpageId 落地页id
     * @throws Exception
     */
    public void creativeCodeHistoryInfo(String campaignId, String landpageId, Date startDate, Date endDate) throws Exception {
    	// 根据活动id查询活动信息
    	// FIXME 传新的时间
    	CampaignModel campaign = campaignDao.selectByPrimaryKey(campaignId);
    	if (campaign == null) {
    		throw new ResourceNotFoundException(PhrasesConstant.OBJECT_NOT_FOUND);
    	}    	  	
		
		// 根据落地页id查询落地页监测码信息
		LandpageCodeModelExample landpageCodeEx = new LandpageCodeModelExample();
		landpageCodeEx.createCriteria().andLandpageIdEqualTo(landpageId);
		List<LandpageCodeModel> landpageCodes = landpageCodeDao.selectByExample(landpageCodeEx);
		if (landpageCodes != null && !landpageCodes.isEmpty()) {
			List<String> listCodes = new ArrayList<String>();
			for (LandpageCodeModel landpageCode : landpageCodes) {
				// 获取检测码
				String code = landpageCode.getCode();
				listCodes.add(code);
			}
			String[] codes = listCodes.toArray(new String[landpageCodes.size()]);
			String strCodes = org.apache.commons.lang3.StringUtils.join(codes, ",");	
			// 当前时间
			Date current = new Date();
			// 写入数据的信息
			LandpageCodeHistoryModel landpageCodeHistory = new LandpageCodeHistoryModel();
			landpageCodeHistory.setId(UUIDGenerator.getUUID()); // id
			landpageCodeHistory.setCampaignId(campaignId); // 活动id
			landpageCodeHistory.setCodes(strCodes); // 监测码
			// 监测码使用的开始时间
			if (startDate.after(current)) {
				// 如果活动的开始时间在今天之后，监测码使用的开始时间为活动的开始时间
				landpageCodeHistory.setStartTime(startDate);
			} else {
				// 如果活动的开始时间是今天，监测码使用的开始时间为当前时间
				landpageCodeHistory.setStartTime(current);
			}			 						
			landpageCodeHistory.setEndTime(endDate); // 监测码使用的结束时间设置活动的结束时间
			// 插入数据
			landpageCodeHistoryDao.insertSelective(landpageCodeHistory);
		}
    }
    
    /**
     * 更新监测码历史记录表的使用结束时间
     * @param campaignId 活动id
     * @param startDate 活动开始时间
     * @param endDate 活动结束时间
     * @throws Exception
     */
    public void updateCodeHistoryEndTime(String campaignId) throws Exception {
    	// 根据活动id查询落地页监测码历史记录信息
    	LandpageCodeHistoryModelExample codeHistoryEx = new LandpageCodeHistoryModelExample();
    	codeHistoryEx.createCriteria().andCampaignIdEqualTo(campaignId);
    	// 按开始时间进行倒序排序
    	codeHistoryEx.setOrderByClause("start_time DESC");
    	List<LandpageCodeHistoryModel> codeHistorys = landpageCodeHistoryDao.selectByExample(codeHistoryEx);   	
    	// 更新监测码历史记录的结束时间
    	if (codeHistorys != null && !codeHistorys.isEmpty()) {
    		// 如果监测码历史记录不为空，更新距离现在时间最近的一条监测码历史记录的结束时间（一个活动可以对应多个监测码历史记录）
    		// 取出距离现在时间最近的一条监测码历史记录信息
    		LandpageCodeHistoryModel codeHistory = codeHistorys.get(0);
    		// 当前时间 
        	Date current = new Date(); 
			// 将监测码使用的结束时间设置为当前时间
    		codeHistory.setEndTime(current);
    		codeHistory.setId(codeHistory.getId());
    		landpageCodeHistoryDao.updateByPrimaryKeySelective(codeHistory);
    	}
    }   
    
    /**
     * 删除监测码历史记录信息
     * @param campaignId 活动id
     * @param startDate 活动的开始时间
     * @throws Exception
     */
    public void deleteCodeHistoryInfo(String campaignId) throws Exception {
    	// 根据活动id查询监测码开始使用时间大于今天的监测码历史记录信息
    	LandpageCodeHistoryModelExample codeHistorys = new LandpageCodeHistoryModelExample();
    	codeHistorys.createCriteria().andCampaignIdEqualTo(campaignId).andStartTimeGreaterThan(new Date());
    	// 删除
    	landpageCodeHistoryDao.deleteByExample(codeHistorys);
    }
    
    /**
     * 
     * @param campaignId
     * @param startDate
     * @param endDate
     * @throws Exception
     */
    public void updateCodeCodeHistoryInfo(String campaignId, String landpageId, Date startDate, Date endDate, Date startDateInDB) throws Exception {
    	// 当前时间
    	Date current = new Date();
    	// 一天中的最小时间
    	Date todayStart = DateUtils.getSmallHourOfDay(current);
    	if (startDate.equals(todayStart) || startDate.equals(startDateInDB)) {
    		// 修改后的活动开始时间是今天,更新监测码历史记录表中监测码使用的结束时间为调整后的活动结束时间
    		// 根据活动id查询落地页监测码历史记录信息
        	LandpageCodeHistoryModelExample codeHistoryEx = new LandpageCodeHistoryModelExample();
        	codeHistoryEx.createCriteria().andCampaignIdEqualTo(campaignId);
        	// 按开始时间进行倒序排序
        	codeHistoryEx.setOrderByClause("start_time DESC");
        	List<LandpageCodeHistoryModel> codeHistorys = landpageCodeHistoryDao.selectByExample(codeHistoryEx);   	
        	// 更新监测码历史记录的结束时间
        	if (codeHistorys != null && !codeHistorys.isEmpty()) {
        		// 如果监测码历史记录不为空，更新距离现在时间最近的一条监测码历史记录的结束时间（一个活动可以对应多个监测码历史记录）
        		// 取出距离现在时间最近的一条监测码历史记录信息
        		LandpageCodeHistoryModel codeHistory = codeHistorys.get(0);
        		// 将监测码使用的结束时间设置为活动的结束时间
        		codeHistory.setEndTime(endDate);
        		codeHistory.setId(codeHistory.getId());
        		landpageCodeHistoryDao.updateByPrimaryKeySelective(codeHistory);
        	}
    	} else {
    		// 修改后的活动开始时间不是今天---即今天之后
    		// 更新监测码使用的结束时间为当前时间
    		updateCodeHistoryEndTime(campaignId);
    		// 重新插入一条监测码使用记录,其中监测码使用的开始时间为修改后的活动开始时间、使用的结束时间为修改后的活动结束时间
    		creativeCodeHistoryInfo(campaignId, landpageId, startDate, endDate);
    	}
    }
}
