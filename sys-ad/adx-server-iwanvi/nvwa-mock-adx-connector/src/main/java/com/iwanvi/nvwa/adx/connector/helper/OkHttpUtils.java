/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.adx.connector.helper;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * @author wangwp
 */
public final class OkHttpUtils {
	public static OkHttpClient okhttpclient = new OkHttpClient.Builder().callTimeout(5, TimeUnit.MINUTES)
			.connectTimeout(3, TimeUnit.SECONDS).readTimeout(5, TimeUnit.MINUTES).build();

	public static String getBodyAsString(String url) {
		Request request = new Request.Builder().get().url(url).build();

		Response response = null;
		try {
			response = okhttpclient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().string();
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null)
				response.close();
		}
	}

	public static byte[] getBodyAsBytes(String url) {
		Request request = new Request.Builder().get().url(url).build();

		Response response = null;
		try {
			response = okhttpclient.newCall(request).execute();
			if (response.isSuccessful()) {
				return response.body().bytes();
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null)
				response.close();
		}
	}

	public static <T> T getBodyAsObject(String url, Class<T> type) {
		Request request = new Request.Builder().get().url(url).build();

		Response response = null;
		try {
			response = okhttpclient.newCall(request).execute();
			if (response.isSuccessful()) {
				return JSON.parseObject(response.body().string(), type);
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null)
				response.close();
		}
	}

	public static <T> T getBodyAsObject(String url, Object requestBody, Class<T> type) {
		String strBody = JSON.toJSONString(requestBody);
		Request request = new Request.Builder()
				.post(RequestBody.create(MediaType.parse("application/json;charset=utf8"), strBody)).url(url).build();
		Response response = null;
		try {
			response = okhttpclient.newCall(request).execute();
			if (response.isSuccessful()) {
				return JSON.parseObject(response.body().string(), type);
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null)
				response.close();
		}
	}

	public static void main(String[] args) throws Exception {

	}
}
