/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.App;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Device;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest.Imp.Banner;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse;

import okhttp3.ConnectionPool;
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
	public static OkHttpClient okhttpclient = new OkHttpClient();

	public static final int dspTimeout = MinervaCfg.get().getDspTimeout();

	public static OkHttpClient dspHttpClient = new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(20, 5, TimeUnit.MINUTES))
			.callTimeout(Duration.ofMillis(MinervaCfg.get().getDspTimeout())).readTimeout(Duration.ofMillis(dspTimeout))
			.followRedirects(false).build();

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

	public static void writeBodyToFile(String url, File file) {
		Request request = new Request.Builder().get().url(url).build();

		Response response = null;
		try {
			response = okhttpclient.newCall(request).execute();
			if (response.isSuccessful()) {
				FileUtils.writeByteArrayToFile(file, response.body().bytes());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (response != null)
				response.close();
		}
	}

	public static void writeBodyToFile(String url, String filePath) {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			return;
		}
		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		writeBodyToFile(url, file);
	}

	public static void main(String[] args) throws Exception {
		BidRequest bidRequest = BidRequest.newBuilder().setId(UUIDUtils.uuid())
				.setApp(App.newBuilder().setBundle("com.iqiyi").setId("EnkpVs"))
				.setDevice(Device.newBuilder().setAdid("11111111111111111111").setCarrier("1").setConnectiontype(3)
						.setDidmd5(DigestUtils.md5Hex("11111111111111")).setH(300).setW(100)
						.setIfa("111111111111111111")
						.setUa("Mozilla/5.0 (Linux; Android 7.0; BND-TL10 Build/HONORBND-TL10; wv) AppleWebKit/537.36 \"\n"
								+ "						+ \"(KHTML, like Gecko) Version/4.0 Chrome/59.0.3071.125 Mobile Safari/537.36 MicroMessenger/7.0.3.1400(0x2700033C) \"\n"
								+ "						+ \"Process/appbrand0 NetType/4G Language/zh_CN\""))
				.addImp(Imp.newBuilder().setId(UUIDUtils.uuid()).setBanner(Banner.newBuilder().setH(100).setW(100)))
				.build();

		System.out.println(bidRequest.toString());
		Request getAdReq = new Request.Builder().url("http://sdk.gotoline.cn/iwanvi/18/bidapi.php")
				.post(RequestBody.create(MediaType.parse("application/x-protobuf"), bidRequest.toByteArray())).build();

		try (Response response = dspHttpClient.newCall(getAdReq).execute()) {
			int code = response.code();
			byte[] data = response.body().bytes();
			System.out.println(code);
			BidResponse bidRsp = com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse.parseFrom(data);
			System.out.println(bidRsp.toString());
		} catch (Exception ex) {
			throw ex;
		}
		// Response response = dspHttpClient.newCall(getAdReq).execute();
	}
}
