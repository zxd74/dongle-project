/*
 * Copyright 2019 xcar.com.cn All right reserved.
 */
package com.iwanvi.nvwa.adx.connector.testsuite;

import com.alibaba.fastjson.JSON;
import com.iwanvi.nvwa.adx.connector.helper.AdxResponse;
import com.iwanvi.nvwa.adx.connector.helper.OkHttpUtils;
import com.iwanvi.nvwa.openapi.adx.model.Advertiser;

/**
 * 
 * @author wangwp
 */
public class MockDspTests {

	public static void main(String[] args) {
		// 上传广告主
		Advertiser advertiser = new Advertiser();
		advertiser.setName("WWP_网易");
		advertiser.setAddress("北京市朝阳区光华路soho2");
		advertiser.setAdvertiserId("rtb-test-netease");
		advertiser.setBusinessLicense("http://cdn.f2time.com/image/20180613/4ff4477ec8714b20bbc7ddfc2093118f_tmp.jpg");
		advertiser.setQualification("http://cdn.f2time.com/image/20180613/4ff4477ec8714b20bbc7ddfc2093118f_tmp.jpg");

		advertiser.setIndustryType(27);

		// 20932476a4eb4026bf54eb88fa49cc69
		AdxResponse resp = OkHttpUtils.getBodyAsObject(
				"http://localhost:18888/adx/v1/advertiser?token=20932476a4eb4026bf54eb88fa49cc69", advertiser,
				AdxResponse.class);
		System.out.println(JSON.toJSONString(resp));
		// 上传素材
	}
}
