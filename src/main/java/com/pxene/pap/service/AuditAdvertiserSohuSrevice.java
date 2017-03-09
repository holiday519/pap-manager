package com.pxene.pap.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.pxene.pap.common.Base64;
import com.pxene.pap.common.GlobalUtil;
import com.pxene.pap.constant.AdxKeyConstant;
import com.pxene.pap.constant.StatusConstant;
import com.pxene.pap.domain.beans.AdvertiserBean;
import com.pxene.pap.domain.models.AdvertiserAuditModel;
import com.pxene.pap.domain.models.AdvertiserAuditModelExample;
import com.pxene.pap.domain.models.AdvertiserModel;
import com.pxene.pap.domain.models.AdxModel;
import com.pxene.pap.exception.IllegalStatusException;
import com.pxene.pap.exception.ResourceNotFoundException;
import com.pxene.pap.repository.basic.AdvertiserAuditDao;
import com.pxene.pap.repository.basic.AdvertiserDao;
import com.pxene.pap.repository.basic.AdxDao;

@Service
public class AuditAdvertiserSohuSrevice {

	private static final String HTTP_ACCEPT = "accept";

	private static final String HTTP_ACCEPT_DEFAULT = "*/*";

	private static final String HTTP_USER_AGENT = "user-agent";

	private static final String HTTP_USER_AGENT_VAL = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

	private static final String HTTP_CONNECTION = "connection";

	private static final String HTTP_CONNECTION_VAL = "Keep-Alive";

    @Autowired
    private AdxDao adxDao;
    
    @Autowired
    private AdvertiserDao advertiserDao;
    
    @Autowired
    private AdvertiserAuditDao advertiserAuditDao;
    
	@Transactional
	public void audit(AdvertiserBean bean) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_SOHU_VALUE);
		String aexamineUrl = adxModel.getAexamineUrl();//广告主审核地址
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
		Gson gson = new Gson();
		//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("auth_consumer_key") == null || json.get("secret") == null) {
			throw new ResourceNotFoundException("sohu : 缺少私密key");//缺少私密key("sohu广告主提交第三方审核错误！原因：私密key不存在")
		}
		String auth_consumer_key = json.get("auth_consumer_key").getAsString();
        String secret = json.get("secret").getAsString();
        String auth_nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 23);
        String auth_signature_method = "HMAC-SHA1";
        String auth_timestampStr = (System.currentTimeMillis() + "").substring(0,10);
        String customer_name = bean.getCompany();
        String oganization_code = bean.getOrganizationNo();
        Map<String, String> pmap = new LinkedHashMap<String, String>();
        pmap.put("auth_consumer_key", auth_consumer_key);
        pmap.put("auth_nonce", auth_nonce);
        pmap.put("auth_signature_method", auth_signature_method);
        pmap.put("auth_timestamp", auth_timestampStr);
        if (StringUtils.isEmpty(bean.getAddress())) {
        	pmap.put("company_address", bean.getAddress());
        }
        pmap.put("customer_name", customer_name);//广告主名称（不可重复）必填 --- 与公司名称一致
        pmap.put("customer_website", bean.getSiteUrl());//广告主官方网站地址
        if (StringUtils.isEmpty(oganization_code)) {
        	pmap.put("oganization_code", oganization_code);
        }
        String encodeUrl = URLEncoder.encode(aexamineUrl, "UTF-8");
        String str = "";
        String paramStr = "";
        for(String key : pmap.keySet()){
            String value = pmap.get(key);
            String encodeValue = URLEncoder.encode(value, "UTF-8");
            paramStr = paramStr + key + "=" + encodeValue + "&";
            if(!key.equals("oganization_license")){
                str = str + key + "=" + encodeValue + "&";
            }
        }
        str = str.substring(0,str.length() - 1);
        String encodeParams = URLEncoder.encode(str, "UTF-8");
        String text = "POST".toUpperCase() + "&" + encodeUrl + "&" + encodeParams;
        byte[] sha1 = GlobalUtil.HmacSHA1Encrypt(text, secret);
        String auth_signature = Base64.encode(sha1);
        Map<String, String> params = new HashMap<String, String>();
        params.put("auth_consumer_key", auth_consumer_key);
        params.put("auth_nonce", auth_nonce);
        params.put("auth_signature_method", auth_signature_method);
        params.put("auth_timestamp", auth_timestampStr);
        params.put("auth_signature", auth_signature);
        if (StringUtils.isEmpty(bean.getAddress())) {
        	params.put("company_address", bean.getAddress());
        }
        params.put("customer_name", customer_name);//必填
        //选填部分--至少填一项
        params.put("customer_website", bean.getSiteUrl());//广告主官方网站地址
        if (StringUtils.isEmpty(bean.getOrganizationNo())) {
        	params.put("oganization_code", bean.getOrganizationNo());
        }
        
		String result = sendPost(aexamineUrl, params);
        JsonObject jsonMap = gson.fromJson(result , new JsonObject().getClass());
        String status = jsonMap.get("status").getAsString();
        if(status.equals("true")){
        	AdvertiserAuditModelExample example = new AdvertiserAuditModelExample();
			example.createCriteria().andAdvertiserIdEqualTo(bean.getId()).andAdxIdEqualTo(AdxKeyConstant.ADX_SOHU_VALUE);
			AdvertiserAuditModel aModel = new AdvertiserAuditModel();
			aModel.setStatus(StatusConstant.ADVERTISER_AUDIT_WATING);
			advertiserAuditDao.updateByExample(aModel, example);
        }else{
            throw new IllegalStatusException("搜狐广告主提交第三方审核执行失败！原因：" + jsonMap.get("message").toString());
        }
	}
	
	
    public void editAudit(AdvertiserBean bean) throws Exception {
        audit(bean);
    }

	@Transactional
	public void synchronize(String advertiserId) throws Exception {
		AdxModel adxModel = adxDao.selectByPrimaryKey(AdxKeyConstant.ADX_SOHU_VALUE);
		String aexamineresultUrl = adxModel.getAexamineresultUrl();
		String privateKey = adxModel.getPrivateKey();//取出adx的私密key
    	Gson gson = new Gson();
    	//将私密key转成json格式
    	JsonObject json = gson.fromJson(privateKey, new JsonObject().getClass());
		if (json.get("dspId") == null || json.get("token") == null) {
			throw new ResourceNotFoundException("sohu : 缺少私密key");//缺少私密key("sohu广告主同步第三方审核结果错误！原因：私密key不存在")
		}
		String auth_consumer_key = json.get("auth_consumer_key").getAsString();
        String secret = json.get("secret").getAsString();
        AdvertiserAuditModelExample ex = new AdvertiserAuditModelExample();
		ex.createCriteria().andAdvertiserIdEqualTo(advertiserId).andAdxIdEqualTo(AdxKeyConstant.ADX_SOHU_VALUE);
		List<AdvertiserAuditModel> list = advertiserAuditDao.selectByExample(ex);
		AdvertiserAuditModel advAuditModel = null;
		if (list != null && !list.isEmpty()) {
			for (AdvertiserAuditModel adv : list) {
				advAuditModel = adv;
			}
		} else {
			throw new ResourceNotFoundException();
		}
		String customer_key = advAuditModel.getAuditValue();
		AdvertiserModel advertiser = advertiserDao.selectByPrimaryKey(advertiserId);
		String customer_name = advertiser .getCompany();
        String auth_nonce = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 23);
        String auth_signature_method = "HMAC-SHA1";
        String auth_timestampStr = (System.currentTimeMillis() + "").substring(0,10);
        Map<String, String> pmap = new LinkedHashMap<String, String>();
        pmap.put("auth_consumer_key", auth_consumer_key);
        pmap.put("auth_nonce", auth_nonce);
        pmap.put("auth_signature_method", auth_signature_method);
        pmap.put("auth_timestamp", auth_timestampStr);
        pmap.put("customer_key", customer_key);
        pmap.put("customer_name", customer_name);
        pmap.put("page", "1");
        pmap.put("perpage", "1");
        String encodeUrl = URLEncoder.encode(aexamineresultUrl, "UTF-8");
        String str = "";
        String pStr = "";
        for(String key : pmap.keySet()){
            String value = pmap.get(key);
            String encodeValue = URLEncoder.encode(value, "UTF-8");
            pStr = pStr + key + "=" + encodeValue + "&";
            if(!key.equals("oganization_license")){
                str = str + key + "=" + encodeValue + "&";
            }
        }
        str = str.substring(0,str.length() - 1);
        String encodeParams = URLEncoder.encode(str, "UTF-8");
        String text = "GET".toUpperCase() + "&" + encodeUrl + "&" + encodeParams;
        byte[] sha1 = GlobalUtil.HmacSHA1Encrypt(text, secret);
        String auth_signature = Base64.encode(sha1);
        String paramStr = "auth_consumer_key=" + auth_consumer_key + "&auth_nonce=" + auth_nonce +
                "&auth_signature_method=" + auth_signature_method + "&auth_timestamp=" + auth_timestampStr +
                "&auth_signature=" + auth_signature + "&customer_key=" + customer_key +
                "&customer_name=" + URLEncoder.encode(customer_name, "UTF-8") + "&page=1&perpage=1";
        //发送GET请求，返回结果
        String result = sendGet(aexamineresultUrl, paramStr);
        //请求结果处理
        JsonObject jsonMap = gson.fromJson(result , new JsonObject().getClass());
        String status = jsonMap.get("status").getAsString();
        if ("true".equals(status)) {
        	String returnContent = jsonMap.get("content").getAsString();
        	JsonObject jsonContent = gson.fromJson(returnContent , new JsonObject().getClass());
        	JsonArray jsonArray = jsonContent.get("items").getAsJsonArray();
        	int state = 0; // 搜狐门户状态
//            int tv_state = 0; // 搜狐视频状态
            String message = "";
            if(jsonArray.size() != 0){
                for(int i=0;i<jsonArray.size();i++){
                    JsonObject obj = jsonArray.get(i).getAsJsonObject();
                    String _customer_key = obj.get("customer_key").getAsString();
                    if(_customer_key.equals(customer_key)){
                    	state = obj.get("status").getAsInt();
//                    	tv_state = obj.get("tv_status").getAsInt();
                    	if (!StringUtils.isEmpty(obj.get("audit_info").getAsString())) {
                    		message =obj.get("audit_info").getAsString();
                    		break;
                    	}
                    }
                }
            }
            String statu = null;
            if (state == 1) {
            	statu = StatusConstant.ADVERTISER_AUDIT_SUCCESS;// 审核通过
            } else if (state == 0) {
            	statu = StatusConstant.ADVERTISER_AUDIT_WATING;// 待审核
            } else if (state == 2) {
            	statu = StatusConstant.ADVERTISER_AUDIT_FAILURE;// 审核未通过
            }
//            String tv_statu = null;
//            if (tv_state == 1) {
//                tv_statu = StatusConstant.ADVERTISER_AUDIT_SUCCESS;// 审核通过
//            } else if (tv_state == 0) {
//                tv_statu = StatusConstant.ADVERTISER_AUDIT_WATING;// 待审核
//            } else if (tv_state == 2) {
//                tv_statu = StatusConstant.ADVERTISER_AUDIT_FAILURE;// 审核未通过
//            }
            advAuditModel.setStatus(statu);
            advAuditModel.setMessage(message);
            advertiserAuditDao.updateByPrimaryKeySelective(advAuditModel);
        } else {
            throw new IllegalStatusException("搜狐广告主同步第三方审核状态执行失败！原因：" + jsonMap.get("message").toString());
        }
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty(HTTP_ACCEPT, HTTP_ACCEPT_DEFAULT);
			connection.setRequestProperty(HTTP_CONNECTION, HTTP_CONNECTION_VAL);
			connection.setRequestProperty(HTTP_USER_AGENT, HTTP_USER_AGENT_VAL);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
//			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key));
//			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, String> param) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数 
	        for(Map.Entry<String,String> entry : param.entrySet()){ 
	        	formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
	        } 
			post.setEntity(new UrlEncodedFormEntity(formParams,Charset.forName("UTF-8")));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
