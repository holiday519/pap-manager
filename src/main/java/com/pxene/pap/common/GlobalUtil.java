package com.pxene.pap.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
	 * 根据图片地址获取图片内容
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static byte[] getImageContent(String url) throws Exception {
		HttpGet httpGet = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpGet);  
		
		InputStream content = response.getEntity().getContent();
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = content.read(buff, 0, 100)) > 0) 
		{
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
	
}
