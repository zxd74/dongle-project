package com.iwanvi.nvwa.common.utils;

import com.iwanvi.nvwa.common.exception.HttpInvokeException;
import com.google.common.collect.Lists;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

	private static final Logger logger = LoggerFactory.getLogger("httpInvoker");
	private static final NameValuePair[] EMPTY_NAMEVALUE_PAIRS = new NameValuePair[] {};
	private static final String DEFAULT_CHARET = "UTF-8";

	private static MultiThreadedHttpConnectionManager connectionManager;
	private static HttpClient httpClient;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setConnectionTimeout(30000);
		connectionManager.getParams().setSoTimeout(120000);
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(5);
		httpClient = new HttpClient(connectionManager);
	}

	public static String getQuietly(String url) {
		try {
			return get(url);
		} catch (Exception ex) {
			// 捕获异常，但不返回给调用方
		}
		return null;
	}

	public static String get(String url) throws HttpInvokeException {
		return executeMethod(new GetMethod(url));
	}

	public static String get(String url,Map<String,String> headers, String sdd) throws HttpInvokeException {
		GetMethod get = new GetMethod(url);
		if (headers != null && !headers.isEmpty()) {

			for (Map.Entry<String, String> entry : headers.entrySet()) {
				get.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		return executeMethod(get);
	}

	public static String get(String url, Map<String, String> parameters) throws HttpInvokeException {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setQueryString(buildNameValuePair(parameters));
		return executeMethod(getMethod);
	}

	public static byte[] getResultByte(String url) throws HttpInvokeException {
		return executeMethodResultByte(new GetMethod(url));
	}

	public static String postQuietly(String url, Map<String, String> parameters) {
		try {
			return post(url, parameters, null, null, null);
		} catch (Exception ex) {
			// ignore exception
		}
		return null;
	}

	public static String postQuietly(String url, Map<String, String> parameters, String contentType, String charset,
			String requestBody) {
		try {
			return post(url, parameters, contentType, charset, requestBody);
		} catch (Exception ex) {
			// ignore exception
		}
		return null;
	}

	public static String post(String url, Map<String, String> parameters) throws HttpInvokeException {
		return post(url, parameters, null, null, null);
	}
	public static String post(String url) throws  HttpInvokeException{
		return post(url,null,null,null,null);
	}
	public static String post(String url, Map<String, String> parameters, String contentType, String charset,
			String requestBody) throws HttpInvokeException {
		return post(url, parameters, null, contentType, charset, requestBody);
	}

	public static String post(String url, Map<String, String> parameters, Map<String, String> headers,
			String contentType, String charset, String requestBody) throws HttpInvokeException {
		PostMethod post = new PostMethod(url);
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				post.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if (requestBody != null) {
			post.setQueryString(buildNameValuePair(parameters));
			try {
                logger.info("request url: {}, post request body: {}, contentType: {},header:{}",url,requestBody,contentType,JsonUtils.TO_JSON(headers));
				post.setRequestEntity(
						new StringRequestEntity(requestBody, contentType, charset == null ? DEFAULT_CHARET : charset));
			} catch (Exception ex) {
				logger.error("", ex);
				throw new HttpInvokeException(ex);
			}
		} else {
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARET);
			post.setRequestBody(buildNameValuePair(parameters));
		}
//		return executeMethod(post);
        String response=executeMethod(post);
        logger.info("invoke result: {}",response);
        return response;
	}

	public static String post(String url, Map<String, String> headers, byte[] content) throws HttpInvokeException {
		PostMethod post = new PostMethod(url);
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				post.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if (content != null) {
			post.setRequestEntity(new ByteArrayRequestEntity(content));
		}
		return executeMethod(post);
	}

	public static String post(String url, Map<String, String> headers, File file) throws HttpInvokeException {
		PostMethod post = new PostMethod(url);
		if (headers != null && !headers.isEmpty()) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				post.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}
		if (file != null) {
			Part filePart;
			try {
				filePart = new FilePart(file.getName(),file);
			} catch (FileNotFoundException e) {
				throw new HttpInvokeException(e);
			}
			List<Part> parts = Lists.newArrayList();
			parts.add(filePart);
			Part[] partArray = new Part[parts.size()];
			parts.toArray(partArray);

			MultipartRequestEntity mre = new MultipartRequestEntity(partArray,post.getParams());
			post.setRequestEntity(mre);
		}
		return executeMethod(post);
	}



	/**
	 * 文件上传
	 *
	 * @param url
	 *            上传接口url
	 * @param file
	 *            上传文件
	 * @param fileName
	 *            上传文件name
	 * @param parameters
	 *            上传参数
	 * @return 上传接口返回结果
	 */
	public static String uploadFile(String url, File file, String fileName, Map<String, String> parameters)
			throws HttpInvokeException {
		PostMethod post = new PostMethod(url);
		Part[] parts = buildFileAndValuePart(file, fileName, parameters);
		MultipartRequestEntity mre = new MultipartRequestEntity(parts, post.getParams());
		post.setRequestEntity(mre);
		return executeMethod(post);
	}

	/**
	 * 多文件上传
	 * @param url
	 * @param files 上传文件Map key分别为：fileName 文件名 file 文件
	 * @param parameters
	 * @return
	 * @throws HttpInvokeException
	 */
	public static String uploadFiles(String url, List<Map<String,Object>> files, Map<String, String> parameters)
			throws HttpInvokeException {
		PostMethod post = new PostMethod(url);
		Part[] parts = buildFilesAndValuePart(files, parameters);
		MultipartRequestEntity mre = new MultipartRequestEntity(parts, post.getParams());
		post.setRequestEntity(mre);
		return executeMethod(post);
	}

	private static String executeMethod(HttpMethod method) throws HttpInvokeException {
		try {
			return new String(executeMethodResultByte(method), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new HttpInvokeException(e);
		}
	}

	private static byte[] executeMethodResultByte(HttpMethod method) throws HttpInvokeException {
		if (method == null) {
			throw new IllegalArgumentException("method is required");
		}

		long startTime = System.currentTimeMillis();
		int statusCode = HttpStatus.SC_OK;
		long elapsedTime = 0;

		try {
			method.setRequestHeader("Connection", "close");
			method.setFollowRedirects(false);
			statusCode = httpClient.executeMethod(method);
			elapsedTime = System.currentTimeMillis() - startTime;

			if (statusCode != HttpStatus.SC_OK) {
//				logger.error("调用http请求失败: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " +
//						statusCode + ", 响应信息: " + new String(IOUtils.toByteArray(method.getResponseBodyAsStream())));
				logger.error("调用http请求失败: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " +
						statusCode);
//				throw new HttpInvokeException(statusCode,
//						"调用http服务返回响应错误, url: " + method.getURI() + ",响应码：" + statusCode);
			} else {
				logger.info("调用http请求成功: " + method.getURI() + ",耗时：" + elapsedTime + "ms, 响应码: " + statusCode);
			}
			return IOUtils.toByteArray(method.getResponseBodyAsStream());
		} catch (Exception ex) {
			statusCode = 499;
			try {
				logger.info(
						"调用http请求异常: " + method.getURI() + ",耗时：" + elapsedTime + "ms, exception:" + ex.getMessage());
			} catch (URIException uriex) {
				// ignore this exception
			}
			if (ex instanceof HttpInvokeException) {
				throw (HttpInvokeException) ex;
			} else {
				throw new HttpInvokeException(statusCode, ex);
			}
		} finally {
			method.releaseConnection();
		}
	}

	private static NameValuePair[] buildNameValuePairWithArray(Map<String, String[]> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return EMPTY_NAMEVALUE_PAIRS;
		}
		//NameValuePair[] nameValuePairs = new NameValuePair[parameters.values().size()];

		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(parameters.size());
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			logger.info(entry.getKey()+"="+entry.getValue());
			for(String value:entry.getValue()){
				nameValuePairList.add(new NameValuePair(entry.getKey(), value));
			}
		}
		NameValuePair[] nameValuePairs=nameValuePairList.toArray(new NameValuePair[]{});
		return nameValuePairs;
	}
	
	private static NameValuePair[] buildNameValuePair(Map<String, String> parameters) {
		if (parameters == null || parameters.isEmpty()) {
			return EMPTY_NAMEVALUE_PAIRS;
		}
		NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];

		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>(parameters.size());
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			nameValuePairList.add(new NameValuePair(entry.getKey(), entry.getValue()));
		}
		nameValuePairList.toArray(nameValuePairs);
		return nameValuePairs;
	}

	private static Part[] buildFileAndValuePart(File file, String fileName, Map<String, String> parameters) {
		Part filePart = null;
		try {
			filePart = new FilePart(fileName, file);
		} catch (FileNotFoundException ex) {
			logger.error("", ex);
			throw new HttpInvokeException(ex);
		}
		if (parameters == null || parameters.isEmpty()) {
			return new Part[] { filePart };
		}
		Part[] parts = new Part[parameters.size() + 1];
		List<Part> partList = new ArrayList<Part>(parameters.size() + 1);
		partList.add(filePart);
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			partList.add(new StringPart(entry.getKey(), entry.getValue(),DEFAULT_CHARET));
		}
		partList.toArray(parts);
		return parts;
	}

	private static Part[] buildFilesAndValuePart(List<Map<String,Object>> files, Map<String, String> parameters) {
		Part filePart = null;
		List<Part> fileParts = Lists.newArrayList();
		if ( files != null ) {
			try {
				for (int i = 0; i < files.size(); i++) {
					Map<String,Object> file = files.get( i );
					filePart = new FilePart((String)file.get("fileName"), (File)file.get("file"));
					fileParts.add( filePart );
				}
			} catch (FileNotFoundException ex) {
				logger.error("", ex);
				throw new HttpInvokeException(ex);
			}
		}
		if (parameters == null || parameters.isEmpty()) {
			return new Part[] { filePart };
		}
		Part[] parts = new Part[parameters.size() + fileParts.size()];
		List<Part> partList = new ArrayList<Part>(parameters.size() + fileParts.size());
		partList.addAll(fileParts);
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			partList.add(new StringPart(entry.getKey(), entry.getValue(),DEFAULT_CHARET));
		}
		partList.toArray(parts);
		return parts;
	}

	public static void main(String[] args) throws Exception {

	}

	public static String postWithMultiParameters(String url, Map<String, String[]> parameters, String contentType,
			String charset, String requestBody) {
		PostMethod post = new PostMethod(url);
		
		if (requestBody != null) {
			post.setQueryString(buildNameValuePairWithArray(parameters));
			try {
                logger.info("request url: {}, post request body: {}, contentType: {}",url,requestBody,contentType);
				post.setRequestEntity(
						new StringRequestEntity(requestBody, contentType, charset == null ? DEFAULT_CHARET : charset));
			} catch (Exception ex) {
				logger.error("", ex);
				throw new HttpInvokeException(ex);
			}
		} else {
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, DEFAULT_CHARET);
			post.setRequestBody(buildNameValuePairWithArray(parameters));
		}
//		return executeMethod(post);
        String response=executeMethod(post);
        logger.info("invoke result: {}",response);
        return response;
	}

}
