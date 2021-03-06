package com.pxene.pap.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class GlobalUtil {

	public static String parseString(String s, String v) {
	    if (s == null) {
	      return v;
	    }
	    return s;
    }

	public static String parseString(Object s, String v) {
		if (s == null) {
			return v;
		}
		return String.valueOf(s);
	}

	public static String parseStringAft(Object s, String v, String add) {
		if (s == null) {
			return v;
		}
		return String.valueOf(s) + add;
	}

	public static String parseStringBef(Object s, String v, String add) {
		if (s == null) {
			return v;
		}
		return add + String.valueOf(s);
	}

	public static Long parseLong(String s, Long v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Long.valueOf(Long.parseLong(s));
	  }

	public static Float parseFloat(String s, Float v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Float.valueOf(Float.parseFloat(s));
	}

	public static int parseInt(String s, int v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
	    return Integer.parseInt(s);
    }

	public static int parseInt(Object s, int v) throws Exception {
		if (s == null || "".equals(s)) {
			return v;
		}
		return Integer.parseInt(String.valueOf(s));
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
	public static String sendPost(String url, String param) {
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			post.setEntity(new StringEntity(param,Charset.forName("UTF-8")));
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText 被签名的字符串
     * @param secret  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String secret) throws Exception
    {
        byte[] data=secret.getBytes("UTF-8");
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance("HmacSHA1");
        //用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes("UTF-8");
        //完成 Mac 操作
        return mac.doFinal(text);
    }
	/**
	 * 对SHA1结果进行base64
	 * @param params
	 * @param secret
	 * @param postUrl
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public static String paramsToString(Map<String, String> params, String secret, String postUrl, String action) throws Exception{
		String encodeUrl = URLEncoder.encode(postUrl, "UTF-8");
		String str = "";
		String paramStr = "";
		for(String key : params.keySet()){
			String value = params.get(key);
			String encodeValue = URLEncoder.encode(value, "UTF-8");
			paramStr = paramStr + key + "=" + encodeValue + "&";
			if(!key.equals("oganization_license")){
				str = str + key + "=" + encodeValue + "&";
			}
		}
		str = str.substring(0,str.length() - 1);
		String encodeParams = URLEncoder.encode(str, "UTF-8");
		String text = action.toUpperCase() + "&" + encodeUrl + "&" + encodeParams;
		byte[] sha1 = HmacSHA1Encrypt(text, secret);
		String auth_signature = Base64.encode(sha1);
		return auth_signature;
	}
	
	/**
	 * MD5加密
	 * @param input
	 * @return
	 */
	public static String MD5(String input) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(input.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	public static JsonObject sortJsonObject(JsonObject obj)
    {
        Map<String, JsonElement> map = new TreeMap<String, JsonElement>();
        Set<Entry<String, JsonElement>> entrySet = obj.entrySet();
        for (Entry<String, JsonElement> entry : entrySet)
        {
            String key = entry.getKey();
            JsonElement val = entry.getValue();
            if (val.isJsonObject())
            {
                map.put(key, sortJsonObject(val.getAsJsonObject()));
            }
            else if (val.isJsonArray()) 
            {
                map.put(key, sortJsonArray(val.getAsJsonArray()));
            }
            else if (val.isJsonNull())
            {
                // do nothing
            }
            else
            {
                map.put(key, val.getAsJsonPrimitive());
            }
        }
        
        Gson gson = new Gson();
        JsonElement jsonTree = gson.toJsonTree(map);
        
        return jsonTree.getAsJsonObject();
    }
    
    public static JsonArray sortJsonArray(JsonArray array)
    {
        List<JsonElement> list = new ArrayList<JsonElement>();
        int size = array.size();
        
        for (int i = 0; i < size; i++)
        {
            JsonElement obj = array.get(i);
            if (obj.isJsonObject())
            {
                list.add(sortJsonObject(obj.getAsJsonObject()));
            }
            else if (obj.isJsonArray()) 
            {
                list.add(sortJsonArray(obj.getAsJsonArray()));
            }
            else if (obj.isJsonNull())
            {
                // do nothing
            }
            else
            {
                list.add(obj.getAsJsonPrimitive());
            }
        }
        
        Comparator<JsonElement> comparator = new Comparator<JsonElement>()
        {

            @Override
            public int compare(JsonElement o1, JsonElement o2)
            {
                if (o1.toString().compareTo(o2.toString()) > 0)
                {
                    return 1;
                }
                else if (o1.toString().compareTo(o2.toString()) < 0)
                {
                    return -1;
                }
                return 0;
            }
            
        };
        Collections.sort(list, comparator);
        
        Gson gson = new Gson();
        JsonElement jsonTree = gson.toJsonTree(list);
        
        return jsonTree.getAsJsonArray(); 
    }
    
    public static String writeObject2Json(Object object, boolean needFormat) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        if (needFormat)
        {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }
        return objectMapper.writeValueAsString(object);
    }
    
    public static byte[] recoverImageFromUrl(String urlText) throws Exception
    {
        URL url = new URL(urlText);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        try (InputStream inputStream = url.openStream())
        {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer)))
            {
                output.write(buffer, 0, n);
            }
        }
        
        return output.toByteArray();
    }
}
