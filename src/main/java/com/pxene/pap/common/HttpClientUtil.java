package com.pxene.pap.common;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil
{
    private static final String CHARSET_UTF_8 = "UTF-8";

    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).setConnectionRequestTimeout(15000).build();
    
    private static HttpClientUtil instance = null;
    
    
    private HttpClientUtil()
    {
    }
    
    public static HttpClientUtil getInstance()
    {
        if (instance == null)
        {
            instance = doSync();
        }
        return instance;
    }
    
    private synchronized static HttpClientUtil doSync()
    {
        if (instance == null)
        {
            instance = new HttpClientUtil();
        }
        return instance;
    }
    
    
    /**
     * 发送基础的HTTP POST请求，不包含请求Body。
     * @param httpUrl 请求URL
     */
    public String sendHttpPost(String httpUrl)
    {
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }
    
    public String sendHttpPostJson(String httpUrl, String requestBody)
    {
        HttpPost httpPost = new HttpPost(httpUrl);
        try
        {
            StringEntity stringEntity = new StringEntity(requestBody, CHARSET_UTF_8);
            stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());
            httpPost.setEntity(stringEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }
    
    /**
     * 用来发送有其他header信息的请求
     * @param httpUrl
     * @param requestBody
     * @param headers
     * @return
     */
    public String sendHttpPostJson(String httpUrl, String requestBody, Map<String, String> headers)
    {
        HttpPost httpPost = new HttpPost(httpUrl);
        for (Entry<String, String> entry : headers.entrySet()) {
        	httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        try
        {
            StringEntity stringEntity = new StringEntity(requestBody, CHARSET_UTF_8);
            stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());
            httpPost.setEntity(stringEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }
    
    /**
     * 发送基于表单的的HTTP POST请求。
     * @param httpUrl 请求URL
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPostForm(String httpUrl, String params)
    {
        HttpPost httpPost = new HttpPost(httpUrl);
        try
        {
            StringEntity stringEntity = new StringEntity(params, CHARSET_UTF_8);
            stringEntity.setContentType(ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            httpPost.setEntity(stringEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost);
    }
    
    
    /**
     * 发送基于Key-Value（键值对）的HTTP POST请求。
     * @param httpUrl 请求URL
     * @param maps    参数
     */
    public String sendHttpPostKV(String httpUrl, Map<String, String> maps)
    {
        HttpPost httpPost = new HttpPost(httpUrl);
        
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        
        for (String key : maps.keySet())
        {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        
        try
        {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, CHARSET_UTF_8));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return sendHttpPost(httpPost);
    }
    
    
    /**
     * 发送Post请求
     * @param httpPost
     * @return
     */
    private String sendHttpPost(HttpPost httpPost)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
    
    /**
     * 发送 get请求
     * @param httpUrl
     */
    public String sendHttpGet(String httpUrl)
    {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }
    
    /**
     * 发送 get请求Https
     * @param httpUrl
     */
    public String sendHttpsGet(String httpUrl)
    {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpsGet(httpGet);
    }
    
    /**
     * 发送Get请求
     * @param httpPost
     * @return
     */
    private String sendHttpGet(HttpGet httpGet)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
    
    /**
     * 发送Get请求Https
     * @param httpPost
     * @return
     */
    private String sendHttpsGet(HttpGet httpGet)
    {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try
        {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, CHARSET_UTF_8);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 关闭连接,释放资源
                if (response != null)
                {
                    response.close();
                }
                if (httpClient != null)
                {
                    httpClient.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}
