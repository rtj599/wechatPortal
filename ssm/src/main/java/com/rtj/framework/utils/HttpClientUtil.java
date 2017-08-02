package com.rtj.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;


@Slf4j
public class HttpClientUtil {
	/**
	 * @Fields CLASSNAME : 用于日志处理
	 */ 
	private static final String CLASSNAME = HttpClientUtil.class.getName();
	
    private static final RequestConfig config= RequestConfig.custom().setConnectTimeout(6000).setSocketTimeout(6000).build();
	public static final String DEFAULT_SEND_CHARSET="UTF-8" ;    
    public static final String DEFAULT_RES_CHARSET="UTF-8"; 
	public static final String LOG_ENTRY = "报文标识号:{}[进入-调用类:{}:的方法:{}:请求参数:{}]"; 
	public static final String LOG_EXIT = "报文标识号:{}[退出-调用类:{}:的方法:{}:请求参数:{}]"; 
	public static final String LOG_ERROR = "报文标识号:{}[调用类:{}:的方法:{}:出现错误-{}]";
	public static final String SYS_ERROR = "调用服务发生系统错误，请联系管理员"; 
    
	private static ThreadLocal<CloseableHttpClient> httpClientHolders=new ThreadLocal<CloseableHttpClient>();
    
	/**
	* @Title: getHttpClient
	* @Description:单例模式获取httpClient
	* @return
	* @throws Exception 
	*/
	public static CloseableHttpClient getHttpClient() throws Exception {
		CloseableHttpClient httpClient = httpClientHolders.get();
		if (httpClient == null) {
			httpClient = getSingleSSLConnection();
			httpClientHolders.set(httpClient);
		}
		return httpClient;
	}
	/**
	 * JSON的验证器
	 */
	static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> T deserialize(String strdata, Class<T> clazz) throws  IOException {
		return mapper.readValue(strdata, clazz);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> doPost(String url,String jsonString,String uuid)throws Exception{
		final String METHODNAME="doPost";
		log.info(LOG_ENTRY,uuid,CLASSNAME,METHODNAME,jsonString);
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		if (url==null||"".equals(url)) {
			log.info(LOG_ENTRY,uuid,CLASSNAME,METHODNAME,"服务的的url为空");
			throw new Exception("请求的服务的的url为空");
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("uuid", uuid);
			InputStreamEntity inputStreamEntity = new InputStreamEntity(
					new ByteArrayInputStream(jsonString.getBytes()),
					jsonString.getBytes().length, ContentType.TEXT_XML);
			httpPost.setEntity(inputStreamEntity);
			String responseText = null;
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				log.info(LOG_ENTRY,uuid,CLASSNAME,METHODNAME,"statusCode不为200,请求放弃");
				httpPost.abort();
				throw new Exception("响应状态码为:"+statusCode+",不为200,请求放弃");
			}else{
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					responseText = EntityUtils.toString(responseEntity,DEFAULT_RES_CHARSET);
				}
				EntityUtils.consume(responseEntity);
				return SerializeUtil.deserialize(responseText, Map.class);
			}
		} catch (Exception e) {
			log.error(LOG_ERROR,uuid,CLASSNAME,METHODNAME,e);
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
					response=null;
				} catch (IOException e) {
					log.error(LOG_ERROR,uuid,CLASSNAME,METHODNAME,e);
				}
			log.debug(LOG_EXIT,uuid,CLASSNAME,METHODNAME,jsonString);
		}
		
	
	}
	
	
	public static Map<String,Object>doGet(String url,String uuid) throws Exception{

		final String METHODNAME="doPost";
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		if (url==null||"".equals(url)) {
			log.info(LOG_ENTRY,uuid,CLASSNAME,METHODNAME,"服务的的url为空");
			throw new Exception("请求的服务的的url为空");
		}
		try {
			HttpGet httpGet = new HttpGet(url);
			String responseText = null;
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				log.info(LOG_ENTRY,uuid,CLASSNAME,METHODNAME,"statusCode不为200,请求放弃");
				httpGet.abort();
				throw new Exception("响应状态码为:"+statusCode+",不为200,请求放弃");
			}else{
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					responseText = EntityUtils.toString(responseEntity,DEFAULT_RES_CHARSET);
				}
				EntityUtils.consume(responseEntity);
				return SerializeUtil.deserialize(responseText, Map.class);
			}
		} catch (Exception e) {
			log.error(LOG_ERROR,uuid,CLASSNAME,METHODNAME,e);
			throw e;
		} finally {
			if (response != null)
				try {
					response.close();
					response=null;
				} catch (IOException e) {
					log.error(LOG_ERROR,uuid,CLASSNAME,METHODNAME,e);
				}
			log.debug(LOG_EXIT,uuid,CLASSNAME,METHODNAME);
		}
	
	
	}
   /**
    * HTTP Post 获取内容
    * @param url  请求的url地址 ?之前的地址
    * @param params 请求的参数
    * @return    页面内容 
    * @throws Exception  
    */
	public static Map<String,Object> doPost(String url, Map<String,Object> params,String uuid) throws Exception {
		String jsonString=SerializeUtil.serialize(params);
		return doPost(url,jsonString,uuid);
	}
   
	 /**
	  * 创建单向ssl的连接
	  * @return
	  * @throws AppException
	  */
	private static CloseableHttpClient getSingleSSLConnection(){
		final String METHODNAME = "getSingleSSLConnection";
	 	CloseableHttpClient httpClient = null;
	 	try {
				SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,new TrustStrategy() {
					@Override
					public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate,
							String paramString) throws CertificateException {
						return true;
					}
				}).build();
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
				httpClient =  HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
				return httpClient;
			} catch (Exception e) {
				log.error(LOG_ERROR,CLASSNAME,METHODNAME,e);
			}
	 	return null;
	}
}
