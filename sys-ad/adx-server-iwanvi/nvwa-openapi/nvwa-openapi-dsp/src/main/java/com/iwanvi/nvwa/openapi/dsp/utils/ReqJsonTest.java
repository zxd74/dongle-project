package com.iwanvi.nvwa.openapi.dsp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import ai.houyi.dorado.rest.http.impl.HttpHeaderNames;

public class ReqJsonTest {

	private final static Logger LOG = LoggerFactory.getLogger(ReqJsonTest.class);
	
	public static String getHttpRespData(String url) {
		return getHttpRespData(url, 30000);
	}

	/**
	 * 
	 * @param urlStr
	 * @param timeOut 毫秒
	 * @return
	 */
	public static String getHttpRespData(String urlStr, int timeOut){
		LOG.info("getHttpRespData urlStr=" + urlStr);
		
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String respData = StringUtils.EMPTY;
		String dataline = StringUtils.EMPTY;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(timeOut);
			conn.setConnectTimeout(timeOut);
			conn.setRequestProperty("ContentType", "text/html; charset=UTF-8");
			
			int respCode = conn.getResponseCode();
			
			if (respCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while ((dataline = reader.readLine()) != null) {
					respData += dataline.trim();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return respData;
	}
	
	@SuppressWarnings("deprecation")
	public static String sendPostRequestBody(String url, String param) {
		LOG.info("url:" + url);
		HttpClient client = new HttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(5000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(5000);
		PostMethod postMethod = new PostMethod(url);
		
		postMethod.setRequestBody(param);
		
//		postMethod.addRequestHeader("Connection", "close");
//		postMethod.addRequestHeader(HttpHeaderNames.CONTENT_TYPE, "application/json");
		postMethod.addRequestHeader("content-type", "application/json");
		postMethod.getParams().setParameter(HttpClientParams.HTTP_CONTENT_CHARSET, "UTF-8");
		String executeResult = "";
		try {
			
			int status = client.executeMethod(postMethod);
			if (status != 200) {
				LOG.error("Http status error,[status: <" + status + ">, url: <" + url + ">].");
				executeResult = "ERROR: Http status error,[status: <" + status + ">, url: <" + url + ">].";
			} else {
				executeResult = postMethod.getResponseBodyAsString();
				if (StringUtils.isBlank(executeResult)) {
					LOG.warn("Accessed successfully, not any response received.");
				} else {
					LOG.info("Access to app server successfully!");
				}
			}
			
			System.out.println("-------------status==========" + status);
		} catch (ConnectTimeoutException ex) {
			LOG.error("Host is off, the error message: " + ex);
		} catch (HttpException e) {
			LOG.error("Access failed, HttpException. message: " + e);
		} catch (IOException e) {
			LOG.error("Access failed. IOException. message: " + e);
		} finally {
			postMethod.releaseConnection();
		}
		return executeResult;
	}

	public static void testRequestBody(){
		try {
			String param = FileUtils.readFile("/data/temp/iwanvi.txt");
			String url = "http://127.0.0.1:9300/api/ad";
//			String url = "http://feifanadx.cread.com/api/ad";
			//System.out.println("param-----" + param);
			
//			JSONObject jsonObj = JSONObject.parseObject(param);
//			
//			jsonObj.getJSONObject("device").put("ip", "203.100.81.50");
//			jsonObj.getJSONObject("device").put("did", "123456789012345");
//			jsonObj.getJSONObject("device").put("os", 2);
//			jsonObj.getJSONObject("device").put("deviceType", 3);
//			jsonObj.getJSONObject("app").put("appId", "M7nuyi");
//			jsonObj.getJSONArray("imps").getJSONObject(0).put("posId", "YJR7Zr");
//			param = jsonObj.toJSONString();
			
			System.out.println("jsonObj-----" + param);
			for (int i = 0; i < 1; i++) {
				String string = sendPostRequestBody(url, param);
				System.out.println(i + "-----" + string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testRequestBody();
	}
}
