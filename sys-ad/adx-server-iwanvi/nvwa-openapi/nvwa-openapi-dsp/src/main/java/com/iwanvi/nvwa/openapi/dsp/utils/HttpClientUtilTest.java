package com.iwanvi.nvwa.openapi.dsp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtilTest {

	private final static Logger LOG = LoggerFactory.getLogger(HttpClientUtilTest.class);
	
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
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("Authorization", "0AXuIn6o2ny17ayhqM6YReKduXE8Iq");
			conn.setRequestProperty("x-log-bodyrawsize", "0");
			conn.setRequestProperty("x-log-apiversion", "0.4.0");
			conn.setRequestProperty("x-log-signaturemethod", "hmac-sha1");
			
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
	
	/**
	 * 
	 * @param urlStr
	 * @param timeOut 毫秒
	 * @return
	 */
	public static String getHttpRespDataExe(String urlStr, int timeOut){
		long l1 = System.currentTimeMillis();
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
			
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Keep-Alive", "header");
			
			int respCode = conn.getResponseCode();
			
			if (respCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				while ((dataline = reader.readLine()) != null) {
					respData += dataline.trim();
				}
			}
			
		} catch (Exception e) {
			LOG.error("getHttpRespDataExe error. urlStr: {}, elapsed: {}", urlStr, (System.currentTimeMillis() - l1));
		} finally {
			if (reader != null) {
				try {
					reader.close();
					reader = null;
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		LOG.info("getHttpRespDataExe urlStr: {}, elapsed: {}", urlStr, (System.currentTimeMillis() - l1));
		return respData;
	}
	
	public static String sendPostRequest(String url, Map<String, String> parameterMap) {
		LOG.info("url:" + url);
		HttpClient client = new HttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(20000);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(120000);
		PostMethod postMethod = new PostMethod(url);
		NameValuePair[] nameValuePairs = new NameValuePair[parameterMap.size()];
		int i = 0;
		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			nameValuePairs[i] = new NameValuePair();
			nameValuePairs[i].setName(entry.getKey());
			nameValuePairs[i].setValue(entry.getValue());
			i++;
		}
		postMethod.addParameters(nameValuePairs);
		postMethod.addRequestHeader("Connection", "close");
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
	
	@SuppressWarnings("deprecation")
	public static String sendPostRequestBody(String url, String param, int timeout) {
		LOG.info("url:" + url);
		HttpClient client = new HttpClient();
		client.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
		// 设置连接超时时间(单位毫秒)
		managerParams.setConnectionTimeout(timeout);
		// 设置读数据超时时间(单位毫秒)
		managerParams.setSoTimeout(timeout);
		PostMethod postMethod = new PostMethod(url);
		
		postMethod.setRequestBody(param);
		
		postMethod.addRequestHeader("Connection", "close");
		postMethod.addRequestHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		postMethod.addRequestHeader("X -Forwarded-For", "58.132.181.104");
		
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

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("X -Forwarded-For", "58.132.181.104");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			LOG.error("sendPost error.", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static byte[] doPostClient(byte[] bt, String url, int timeout) {
		byte[] result = null;
		long l1 = System.currentTimeMillis();
		PostMethod postMethod = new PostMethod(url);
		int status = 0;
		try {
			HttpClient httpClient = new HttpClient();
			postMethod.setRequestHeader(new Header("Content-Type", "application/x-protobuf"));
			postMethod.setRequestEntity(new ByteArrayRequestEntity(bt));
			HttpClientParams params = new HttpClientParams();
			params.setConnectionManagerTimeout(timeout);
			httpClient.setParams(params);
			status = httpClient.executeMethod(postMethod);
			// 获取二进制的byte流
			result = postMethod.getResponseBody();
		} catch (Exception e) {
			LOG.info("doPostClient error. url: {}", url, e);
		} finally {
			postMethod.releaseConnection();
		}
		LOG.info("doPostClient status: {}, result: {}, elpased: {}", status, result, (System.currentTimeMillis() - l1));
		return result;
	}
	
	public static String FuncGetMinute(String source){
		String result = null;
		try {
			if(StringUtils.isBlank(source) || !NumberUtils.isDigits(source)){
				return null;
			}
			int temp = Integer.valueOf(source);
			if((temp + 5) % 5 == 0){
				return String.valueOf(temp < 10 ? "0" + temp : temp);
			}
			temp = (temp + 5) / 5 * 5;
			return String.valueOf(temp < 10 ? "0" + temp : temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void test() {
		try {
			List<String> list = new ArrayList<>();
			
//			list.add("http://182.92.219.195:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://182.92.219.195:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://182.92.219.159:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://182.92.219.159:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://182.92.234.165:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://182.92.234.165:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://123.56.42.30:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://123.56.42.30:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://123.56.139.175:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://123.56.139.175:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://112.126.72.25:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://112.126.72.25:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			
//			list.add("http://101.200.1.47:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.1.47:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.195:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.195:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.60:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.60:9041/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.99:9040/adx/pc/weight?o2pcweight=0&adxId=60");
//			list.add("http://101.200.3.99:9041/adx/pc/weight?o2pcweight=0&adxId=60");
			
//			list.add("http://182.92.219.195:9040/adx/filter/pc?isFilter=false");
//			list.add("http://182.92.219.195:9041/adx/filter/pc?isFilter=false");
//			list.add("http://182.92.219.159:9040/adx/filter/pc?isFilter=false");
//			list.add("http://182.92.219.159:9041/adx/filter/pc?isFilter=false");
//			list.add("http://182.92.234.165:9040/adx/filter/pc?isFilter=false");
//			list.add("http://182.92.234.165:9041/adx/filter/pc?isFilter=false");
//			list.add("http://123.56.42.30:9040/adx/filter/pc?isFilter=false");
//			list.add("http://123.56.42.30:9041/adx/filter/pc?isFilter=false");
//			list.add("http://123.56.139.175:9040/adx/filter/pc?isFilter=false");
//			list.add("http://123.56.139.175:9041/adx/filter/pc?isFilter=false");
//			list.add("http://112.126.72.25:9040/adx/filter/pc?isFilter=false");
//			list.add("http://112.126.72.25:9041/adx/filter/pc?isFilter=false");
			
			for (int i = 0; i < list.size(); i++) {
				String string = getHttpRespData(list.get(i));
				System.out.println(i + "-----" + string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testRequestBody(){
		try {
			File file = new File("/Users/shilianjun/Documents/CRT/url.txt");
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			int i = 0;
			int notin = 0;
			int notbidding = 0;
			int ok = 0;
			while ((line = reader.readLine()) != null) {
				i++;
//				System.out.println("-----" + line);
				String string = getHttpRespData(line.replaceAll(",", ""));
				System.out.println(i + "-----" + string);
//				FileUtils.writeToFile(string, "/data/o2_result.txt", true);
				
				if(string.contains("loc_id")){
					ok++;
				}
				if(string.contains("not in cookiemapping")){
					notin++;
				}
				if(string.contains("not bidding")){
					notbidding++;
				}
				
			}
			
			System.out.println("total: " + i + ", ok: " + ok + ", notin: " + notin + ", notbidding: " + notbidding);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		testRequestBody();
		System.out.println("-------ok------");
	}
}
