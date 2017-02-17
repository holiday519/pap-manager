package com.pxene.pap.common;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


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
}
