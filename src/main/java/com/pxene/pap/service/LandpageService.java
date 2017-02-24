package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

import com.pxene.pap.constant.PhrasesConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.LandpageBean;
import com.pxene.pap.domain.models.CampaignModel;
import com.pxene.pap.domain.models.CampaignModelExample;
import com.pxene.pap.domain.models.LandpageModel;
import com.pxene.pap.domain.models.LandpageModelExample;
import com.pxene.pap.exception.DuplicateEntityException;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.CampaignDao;
import com.pxene.pap.repository.basic.LandpageDao;

@Service
public class LandpageService extends BaseService {

	@Autowired
	private LandpageDao landpageDao;

	@Autowired
	private CampaignDao campaignDao;

	private static final String HTTP_ACCEPT = "accept";

	private static final String HTTP_ACCEPT_DEFAULT = "*/*";

	private static final String HTTP_USER_AGENT = "user-agent";

	private static final String HTTP_USER_AGENT_VAL = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

	private static final String HTTP_CONNECTION = "connection";

	private static final String HTTP_CONNECTION_VAL = "Keep-Alive";

	/**
	 * 监测脚本地址
	 */
	public static final String MONITOR_URL = "img.pxene.com/pxene.js";
    /**
     * 监测代码片段_开始
     */
    private static final String CODE_START = "<script>var_pxe=_pxe||[];var_pxe_id='";
    /**
     * 监测代码片段_结束
     */
    private static final String CODE_END = "';(function(){varpxejs=document.createElement('script');var_pxejsProtocol=(('https:'==document.location.protocol)?'https://':'http://');pxejs.src=_pxejsProtocol+'//"+MONITOR_URL+"';varone=document.getElementsByTagName('script')[0];one.parentNode.insertBefore(pxejs,one);})();</script>";
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
		LandpageModel model = modelMapper.map(bean, LandpageModel.class);
		String id = UUID.randomUUID().toString();
		model.setId(id);
		model.setStatus(StatusConstant.LANDPAGE_CHECK_NOTCHECK);
		try {
			// 添加落地页信息
			landpageDao.insertSelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
		}
		BeanUtils.copyProperties(model, bean);
	}

	/**
	 * 修改落地页
	 * 
	 * @param id
	 * @param bean
	 */
	@Transactional
	public void updateLandpage(String id, LandpageBean bean) throws Exception {
		LandpageModel landpageInDB = landpageDao.selectByPrimaryKey(id);
		if (landpageInDB == null) {
			throw new ResourceNotFoundException();
		}

		LandpageModel model = modelMapper.map(bean, LandpageModel.class);
		model.setId(id);
		try {
			// 修改落地页信息
			landpageDao.updateByPrimaryKeySelective(model);
		} catch (DuplicateKeyException exception) {
			throw new DuplicateEntityException();
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
			throw new ResourceNotFoundException();
		}
		CampaignModelExample example = new CampaignModelExample();
		example.createCriteria().andLandpageIdEqualTo(id);
		List<CampaignModel> list = campaignDao.selectByExample(example);
		if (list == null || !list.isEmpty()) {
			throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN);
		}
		
		// 删除落地页
		landpageDao.deleteByPrimaryKey(id);
	}
	/**
	 * 批量删除落地页
	 * @param ids
	 * @throws Exception
	 */
	@Transactional
	public void deleteLandpages(String[] ids) throws Exception {
		
		List<String> asList = Arrays.asList(ids);
		LandpageModelExample ex = new LandpageModelExample();
		ex.createCriteria().andIdIn(asList);
		
		List<LandpageModel> landpageInDB = landpageDao.selectByExample(ex);
		if (landpageInDB == null || landpageInDB.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (String id : ids) {
			CampaignModelExample example = new CampaignModelExample();
			example.createCriteria().andLandpageIdEqualTo(id);
			List<CampaignModel> list = campaignDao.selectByExample(example);
			// 不是投放中才可以删除
			if (list == null || !list.isEmpty()) {
				throw new IllegalStatusException(PhrasesConstant.LANDPAGE_HAVE_CAMPAIGN);
			}
		}
		// 删除落地页
		landpageDao.deleteByExample(ex);
	}

	/**
	 * 根据Id查询落地页
	 * 
	 * @param id
	 * @return
	 */
	public LandpageBean selectLandpage(String id) throws Exception {
		LandpageModel landpageModel = landpageDao.selectByPrimaryKey(id);
		if (landpageModel == null) {
			throw new ResourceNotFoundException();
		}
		LandpageBean bean = modelMapper.map(landpageModel, LandpageBean.class);

		return bean;
	}
	/**
	 * 查询落地页列表
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<LandpageBean> selectLandpages(String name) throws Exception {
		LandpageModelExample example = new LandpageModelExample();
		if (!StringUtils.isEmpty(name)) {
			example.createCriteria().andNameLike("%" + name + "%");
		}
		List<LandpageBean> list = new ArrayList<LandpageBean>();
		List<LandpageModel> models = landpageDao.selectByExample(example);
		
		if (models == null || models.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		for (LandpageModel model : models) {
			list.add(modelMapper.map(model, LandpageBean.class));
		}
		
		return list;
	}
	/**
	 * 检查落地页中监测代码安装状态
	 * @param landpageId
	 * @throws Exception
	 */
	public String checkCode(String landpageId) throws Exception {
		LandpageModel model = landpageDao.selectByPrimaryKey(landpageId);
		if (model == null) {
			throw new ResourceNotFoundException();
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
			// 获取所有响应头字段
//		Map<String, List<String>> map = connection.getHeaderFields();
//		// 遍历所有的响应头字段
//		for (String key : map.keySet()) {
//			System.out.println(key + "--->" + map.get(key));
//		}
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
			int headStart = reCode.indexOf(HTML_HEAD_START);
			int headEnd = reCode.indexOf(HTML_HEAD_END);
			if (headStart > 0
					&& headEnd > 0) {
				reCode = reCode.substring(headStart,
						headEnd).replaceAll(" ", "")
						.replaceAll("\"", "'").replaceAll("\n", "").replace("\t", "");
				if (reCode.indexOf(CODE_START + landpageId
						+ CODE_END) > 0) {
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
}
