package com.iwanvi.nvwa.openapi.dsp.service.distribution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Task implements Callable<Customer> {

	private Customer customer;

	public Task setCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

	private Customer doGet(String urlStr, int timeOut) {
		long l1 = System.currentTimeMillis();
		HttpURLConnection conn = null;
		BufferedReader reader = null;

		String response = StringUtils.EMPTY;
		String exception = StringUtils.EMPTY;
		int responseCode = 0;

		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			System.out.println("-----1---" + (System.currentTimeMillis() - l1));

			if ((System.currentTimeMillis() - l1) < timeOut) {
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("GET");
				conn.setUseCaches(false);
				conn.setReadTimeout(timeOut);
				conn.setConnectTimeout(timeOut);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setInstanceFollowRedirects(false);

				long l2 = System.currentTimeMillis();
				// responseCode = conn.getResponseCode();

				System.out.println("-------2--------" + (System.currentTimeMillis() - l2));

				// if (responseCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
				response = sb.toString();
				// }
			} else {
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			}

		} catch (Exception e) {
			exception = e.getMessage();
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
			customer.setElapsed(System.currentTimeMillis() - l1);
			customer.setException(exception);
			customer.setResponseString(response);
			customer.setResponseCode(responseCode);
		}
		return this.customer;
	}

	private Customer apacheHttpClientGet(String urlStr, int timeOut) {
		long l1 = System.currentTimeMillis();
		String response = StringUtils.EMPTY;
		String exception = StringUtils.EMPTY;
		int responseCode = 0;

		CloseableHttpClient httpclient = null;
		CloseableHttpResponse responseObj = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpget = new HttpGet(urlStr);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut)
					.setConnectionRequestTimeout(timeOut).build();// 设置请求和传输超时时间
			httpget.setConfig(requestConfig);

//			System.out.println("executing request " + httpget.getURI());
			responseObj = httpclient.execute(httpget);
			// 获取响应实体
			HttpEntity entity = responseObj.getEntity();
			// 打印响应状态
//			System.out.println(responseObj.getStatusLine().getStatusCode());
			if (entity != null) {
				// 打印响应内容长度
				System.out.println("Response content length: " + entity.getContentLength());
				// 打印响应内容
				System.out.println("Response content: " + EntityUtils.toString(entity));
			}
		} catch (Exception e) {
			exception = e.getMessage();
		} finally {
			// 关闭连接,释放资源
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (Exception e2) {
				}
			}
			if (responseObj != null) {
				try {
					responseObj.close();
				} catch (Exception e2) {
				}
			}
			customer.setElapsed(System.currentTimeMillis() - l1);
			customer.setException(exception);
			customer.setResponseString(response);
			customer.setResponseCode(responseCode);
		}
		return this.customer;
	}

	private Customer doPostString(String url, String json, int timeOut){
		long l1 = System.currentTimeMillis();
		String response = StringUtils.EMPTY;
		String exception = StringUtils.EMPTY;
		int responseCode = 0;
		try {
			HttpClient client = HttpClients.createDefault();
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-Type", "application/json");
			
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut)
					.setConnectionRequestTimeout(timeOut).build();// 设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			StringEntity param = new StringEntity(json, CharEncoding.UTF_8);
			httpPost.setEntity(param);
//			if(paramObj instanceof String) {
//				StringEntity param = new StringEntity(json, CharEncoding.UTF_8);
//				httpPost.setEntity(param);
//			} else if(paramObj instanceof Byte[]) {
//				ByteArrayEntity param = new ByteArrayEntity((byte[])paramObj);
//				httpPost.setEntity(param);
//			}
			HttpResponse responseObj = client.execute(httpPost);
			HttpEntity entity = responseObj.getEntity();
			responseCode = responseObj.getStatusLine().getStatusCode();
			if (entity != null) {
				response = EntityUtils.toString(entity);
			}
		} catch(Exception e) {
			exception = e.getMessage();
		} finally {
			customer.setElapsed(System.currentTimeMillis() - l1);
			customer.setException(exception);
			customer.setResponseString(response);
			customer.setResponseCode(responseCode);
		}
		return this.customer;
	}
	
	@Override
	public Customer call() throws Exception {
		return doPostString(customer.getRequestUrl(), customer.getParameterString(), customer.getTimeOut());
//		return okHttpGet(customer.getRequest(), customer.getTimeOut());
//		return doGet(customer.getRequest(), customer.getTimeOut());
//		long l1 = System.currentTimeMillis();
//		connectNetwork(customer.getRequest(), customer.getTimeOut());
//		System.out.println("============" + (System.currentTimeMillis() - l1));
//		
//		Thread.sleep(3000);
//		
//		return apacheHttpClientGet(customer.getRequest(), customer.getTimeOut());
	}

}
