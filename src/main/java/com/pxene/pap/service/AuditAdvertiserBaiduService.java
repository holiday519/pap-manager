package com.pxene.pap.service;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModel;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModelExample;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.repository.mapper.basic.AdvertiserAuditModelMapper;
import com.pxene.pap.repository.mapper.basic.AdxModelMapper;

public class AuditAdvertiserBaiduService {
	
    @Autowired
    private AdxModelMapper adxMapper;
    
    @Autowired
    private AdvertiserAuditModelMapper advAuditMapper;
    
    /**
     * 审核和重复审核接口
     * auditType 传值 ："add","edit";请勿任意传值
     * @param bean
     * @param auditType
     * @return
     * @throws Exception
     */
	public void auditAndEdit(AdvertiserBean bean,String auditType) throws Exception {
		AdxModel adxModel = adxMapper.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		String aexamineurl = adxModel.getAexamineUrl();//广告主审核地址
		String qexamineurl = adxModel.getQexamineUrl();//资质审核地址
		String aupdateurl = adxModel.getAupdateUrl();//广告主更新地址
		String qupdateurl = adxModel.getQupdateUrl();//资质更新地址
    	String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("dspId") == null || json.get("token") == null) {
			throw new Exception();//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
		String dspId = json.get("dspId").toString();
		String token = json.get("token").toString();
		//组成“请求头”
    	JsonObject authHeader = new JsonObject();
    	authHeader.addProperty("dspId", dspId);
    	authHeader.addProperty("token", token);
		
		String licenseURL = bean.getLicenseUrl();
		String accountURL = bean.getAccountUrl();
		String licenseNO = bean.getLicenseNo();
		if ((licenseURL == null || "".equals(licenseURL))
				&& (accountURL == null || "".equals(accountURL))) {
			throw new Exception();// "baidu广告主提交第三方审核错误：原因：需要上传资质文件"// ——————————————
		}
		if (licenseNO == null || "".equals(licenseNO)) {
			throw new Exception();// "baidu广告主提交第三方审核错误：原因：资质编号不可为空"// ——————————————
		}
		String advertiserId = bean.getId();
		//将请求参数插入到广告主审核表中
		AdvertiserAuditModel model = new AdvertiserAuditModel();
		model.setId(UUID.randomUUID().toString());
		model.setAdvertiserId(advertiserId);
		
		model.setStatus("00");
		//生成一个数字型随机数（数字id）
		long num;
		//如果auditType是“edit”时，走编辑逻辑；
		if ("edit".equals(auditType)) {
			// 重新提交时，如果数据库中已有提交值，那么就直接用，如果没有 就像第一次提交一样，添加到数据库中
			AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
			ex.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
			List<AdvertiserAuditModel> list = advAuditMapper.selectByExample(ex);
			if (list == null || list.isEmpty()) {
				num = (long) Math
						.floor((new Random()).nextDouble() * 1000000000D);
				model.setAuditValue(String.valueOf(num));
				advAuditMapper.insertSelective(model);
			} else {
				num = Long.parseLong(list.get(0).getAuditValue());
			}
			// 如果auditType值不是“edit”，走添加逻辑
		} else {
			num = (long) Math.floor((new Random()).nextDouble() * 1000000000D);
			model.setAuditValue(String.valueOf(num));
			advAuditMapper.insertSelective(model);
		}
		//request参数——广告主提交第三方审核
		JsonObject param = new JsonObject();
		JsonArray request = new JsonArray();
		JsonObject advObj = new JsonObject();
		advObj.addProperty("advertiserId", num);
		advObj.addProperty("advertiserLiteName", bean.getName());
		advObj.addProperty("advertiserName", bean.getCompany());
		advObj.addProperty("siteName", bean.getSiteName());
		advObj.addProperty("siteUrl", bean.getSiteUrl());
		advObj.addProperty("telephone", bean.getPhone());
		advObj.addProperty("address", bean.getAddress());
		request.add(advObj);
		param.addProperty("request", request.toString());
		param.addProperty("authHeader", authHeader.toString());
		//发送post请求
		Map<String, String> map; 
		//如果auditType是“edit”，调用编辑请求；否则调用添加请求
		if ("edit".equals(auditType)) {
			map = post(aupdateurl, param.toString());
		} else {
			map = post(aexamineurl, param.toString());
		}
		//返回结构中取出reslut
		String result = map.get("result");
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		//查询状态
        int status = jsonMap.get("status").getAsInt();
        //返回0 时，才是成功；否则审核错误
		if (status != 0) {
			throw new Exception();// ——————————————
		}
        //提交资质审核
        Integer type = 2;//资质类型（大陆企业单位类客户：营业执照（编号：2））
        String validDate = "2030-12-31";
        JsonObject zParam = new JsonObject();//资质请求属性
        JsonArray qualifications = new JsonArray();//资质
        JsonObject qualificationObj = new JsonObject();//资质列表
        JsonObject mainLinceObj = new JsonObject();//主体资质
        JsonArray imgUrls = new JsonArray();//资质图片url
		if (licenseURL != null && !"".equals(licenseURL)) {
			imgUrls.add(licenseURL);
		}
		if (accountURL != null && !"".equals(accountURL)) {
			imgUrls.add(accountURL);
		}
		//request参数——资质提交第三方审核
        mainLinceObj.addProperty("type", type);
        mainLinceObj.addProperty("name", bean.getCompany());
        mainLinceObj.addProperty("number", licenseNO);
        mainLinceObj.addProperty("validDate", validDate);
        mainLinceObj.addProperty("imgUrls", imgUrls.toString());
        qualificationObj.addProperty("advertiserId", num);
        qualificationObj.addProperty("mainLincence", mainLinceObj.toString());
        qualifications.add(qualificationObj);
        zParam.addProperty("qualifications", qualifications.toString());
        zParam.addProperty("authHeader", authHeader.toString());
        //请求资质审核接口
        Map<String, String> resultMap;
        //如果auditType是“edit”，调用编辑请求；否则调用添加请求
        if ("edit".equals(auditType)) {
        	resultMap = post(qupdateurl, zParam.toString());
		} else {
			resultMap = post(qexamineurl, zParam.toString());
		}
        String zResult = resultMap.get("reslut");
        JsonObject zJsonMap = gson.fromJson(zResult, new JsonObject().getClass());
        //查询状态
        int zStatus = zJsonMap.get("status").getAsInt();
        //状态 0 表示成功
		if (0 == zStatus) {
			JsonObject zObj = zJsonMap.getAsJsonObject("qualificationStatuses");
			int zStatu = zObj.get("status").getAsInt();
			if (0 == zStatu) {
				//成功
			}
		}
	}
	
	/**
	 * 同步广告主第三方审核状态
	 * @param advertiserId
	 * @throws Exception
	 */
	public void synchronize(String advertiserId) throws Exception {
		AdxModel adxModel = adxMapper.selectByPrimaryKey(AdxKeyConstant.ADX_BAIDU_VALUE);
		String aexamineresultUrl = adxModel.getAexamineresultUrl();
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("dspId") == null || json.get("token") == null) {
			throw new Exception();//缺少私密key("baidu广告主提交第三方审核错误！原因：私密key不存在")
		}
		String dspId = json.get("dspId").toString();
		String token = json.get("token").toString();
		//组成“请求头”
    	JsonObject authHeader = new JsonObject();
    	authHeader.addProperty("dspId", dspId);
    	authHeader.addProperty("token", token);
    	AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
		ex.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE);
		List<AdvertiserAuditModel> list = advAuditMapper.selectByExample(ex);
		AdvertiserAuditModel advAuditModel = list.get(0);
		long num = Long.parseLong(advAuditModel.getAuditValue());
		JsonArray adver = new JsonArray();//广告主数字Id数组
		adver.add(num);
		JsonObject obj = new JsonObject();//请求参数
		obj.addProperty("authHeader", authHeader.toString());
		obj.addProperty("advertiserIds", adver.toString());
		//发送同步请求
		Map<String, String> map = post(aexamineresultUrl, obj.toString());
		//返回结构中取出reslut
		String result = map.get("result");
		JsonObject jsonMap = gson.fromJson(result, new JsonObject().getClass());
		//获取状态
		int status = jsonMap.get("status").getAsInt();
		//0代表同步成功；如果成功获取同步信息,存入数据库
		if (0 == status) {
			JsonObject responseObj = jsonMap.getAsJsonObject("response");
			//审核状态值
			int state = responseObj.get("state").getAsInt();
			//审核错误、失败原因
			String refuseReason = responseObj.get("refuseReason").getAsString();
			//审核结构
			String statu = null;//状态（要存数据库的状态）
            String message = null;//（错误信息）
			if (state == 0) {
				statu = "01";// 审核通过
			} else if (state == 1) {
				statu = "00";// 待审核
			} else if (state == 2) {
				message = refuseReason;
				statu = "02";// 审核未通过
			} else if (state == 3) {
				if (refuseReason != null) {
					message = refuseReason;
				} else {
					message = "因为资质或信息不全，广告主未发起状态检查";
				}
				statu = "02";// 审核未通过
			}
			AdvertiserAuditModelExample advAudEx = new AdvertiserAuditModelExample();
			advAudEx.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE).andAdvertiserIdEqualTo(advertiserId);
			AdvertiserAuditModel advAudModel = new AdvertiserAuditModel();
			advAudModel.setStatus(statu);
			advAudModel.setMessage(message);
			//更新广告主审核表信息
			advAuditMapper.updateByExampleSelective(advAudModel, advAudEx);
		} else {
		//广告主同步第三方审核状态执行失败;将错误信息存入数据库
			JsonArray errors = jsonMap.getAsJsonArray("errors");
			JsonObject error = errors.get(0).getAsJsonObject();
			String message = error.get("message").getAsString();
			AdvertiserAuditModelExample advAudEx = new AdvertiserAuditModelExample();
			advAudEx.createCriteria().andAdxIdEqualTo(AdxKeyConstant.ADX_BAIDU_VALUE).andAdvertiserIdEqualTo(advertiserId);
			AdvertiserAuditModel advAudModel = new AdvertiserAuditModel();
			advAudModel.setMessage(message);
			//更新广告主审核表信息
			advAuditMapper.updateByExampleSelective(advAudModel, advAudEx);
			// 是否需要抛出异常？抛的话上方插入操作回滚？——————————————
		}
	}
	
	/**
	 * 发送请求
	 */
	public Map<String, String> post(String url,String param) throws Exception {
		HttpPost httpost = new HttpPost(url);
		httpost.setHeader("Content-Type", "application/json;charset=utf-8");
		httpost.setHeader("Accept", "application/json;charset=utf-8");
		StringEntity input = new StringEntity(param, "UTF-8");
		input.setContentType("application/json;charset=utf-8");
		Map<String, String> map = new HashMap<String, String>();
		httpost.setEntity(input);
		DefaultHttpClient httpClient = getHttpClient(true);
		HttpResponse response = httpClient.execute(httpost);
		int statuCode = response.getStatusLine().getStatusCode();
		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);
		map.put("result", body);
		map.put("code", param);
		map.put("statucode", statuCode + "");
		return map;
	}
	
	@SuppressWarnings({ "deprecation" })
	private  DefaultHttpClient getHttpClient(boolean isSSL) throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
		if (isSSL) {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = httpclient.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			httpclient = new DefaultHttpClient(ccm, httpclient.getParams());
			return httpclient;
		} else {
			return httpclient;
		}
	}
}
