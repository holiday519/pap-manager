package com.pxene.pap.service;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.model.basic.AdvertiserAuditModel;
import com.pxene.pap.domain.model.basic.AdxModel;
import com.pxene.pap.repository.mapper.basic.AdvertiserAuditModelMapper;
import com.pxene.pap.repository.mapper.basic.AdxModelMapper;

public class AuditAdvertiserBaiduService {
	
    // 百度广告主上传资质接口地址
    public static final String BAIDU_UPLOAD_QUALIFICATION = "https://api.es.baidu.com/v1/advertiser/uploadQualification";
    
    @Autowired
    private AdxModelMapper adxMapper;
    @Autowired
    private AdvertiserAuditModelMapper advAuditMapper;
    
	public Map<String,String> audit(AdvertiserBean bean) throws Exception{
		AdxModel adxModel = adxMapper.selectByPrimaryKey("8");
		String aexamineurl = adxModel.getAexamineUrl();//广告主审核
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
			throw new Exception();// "baidu广告主提交第三方审核错误：原因：需要上传资质文件"
		}
		if (licenseNO == null || "".equals(licenseNO)) {
			throw new Exception();// "baidu广告主提交第三方审核错误：原因：资质编号不可为空"
		}
		String advertiserId = bean.getId();
		//生成一个数字型随机数（数字id）
		long num = (long)Math.floor((new Random()).nextDouble() * 1000000000D);
		//将请求参数插入到广告主审核表中
		AdvertiserAuditModel model = new AdvertiserAuditModel();
		model.setId(UUID.randomUUID().toString());
		model.setAdvertiserId(advertiserId);
		model.setAuditValue(String.valueOf(num));
		model.setStatus("00");
		advAuditMapper.insertSelective(model);
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
		Map<String, String> map = post(aexamineurl, param.toString());
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
        Map<String, String> resultMap = post(BAIDU_UPLOAD_QUALIFICATION, param.toString());
        String zResult = resultMap.get("reslut");
        JsonObject zJsonMap = gson.fromJson(result, new JsonObject().getClass());
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
		return null;
	}
	
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
