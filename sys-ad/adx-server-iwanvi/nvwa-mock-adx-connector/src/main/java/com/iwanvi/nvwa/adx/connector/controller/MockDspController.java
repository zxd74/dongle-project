/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.adx.connector.controller;

import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.adx.connector.Application;
import com.iwanvi.nvwa.adx.connector.helper.AdxResponse;
import com.iwanvi.nvwa.adx.connector.helper.OkHttpUtils;
import com.iwanvi.nvwa.adx.connector.model.DspSetting;
import com.iwanvi.nvwa.openapi.adx.model.Advertiser;
import com.iwanvi.nvwa.openapi.adx.model.Creative;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidRequest;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse.SeatBid;
import com.iwanvi.nvwa.rtb.proto.NvwaRtb.BidResponse.SeatBid.Bid;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.POST;
import ai.houyi.dorado.rest.annotation.Path;

/**
 * @author wangwp
 */
@Controller
@Path("/mock")
public class MockDspController {
	static final Logger LOG = LoggerFactory.getLogger(MockDspController.class);

	static DspSetting setting = new DspSetting();
	static Properties config = new Properties();
	static {
		try {
			config.load(Application.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (Exception ex) {
		}
	}

	@Path("/setting")
	public void setDspSetting(DspSetting setting) {
		MockDspController.setting = setting;
		LOG.info("==update global dsp setting: {}==", setting);
	}

	@POST
	@Path("/exchange/bid")
	public BidResponse bid(BidRequest bidRequest) {
		BidResponse.Builder bidResponse = BidResponse.newBuilder().setId(bidRequest.getId());
		LOG.info("received ad-exchange bid request: {}", bidRequest.toString());

		int bidPrice = setting.getBidPrice() == null ? 1000 : setting.getBidPrice();
		String crid = setting.getCrid() == null ? "rtb-test-creative" : setting.getCrid();
		// 构造模拟竞价响应
		bidRequest.getImpList().forEach(imp -> {
			Bid.Builder bid = Bid.newBuilder().setImpid(imp.getId()).setCrid("rtb-test-creative")
					.setId(UUID.randomUUID().toString().replace("-", ""))
					.addClktrackers("http://c.miaozhen.com").addImptrackers("http://m.miaozhen.com").setPrice(bidPrice);

			SeatBid.Builder seatBid = SeatBid.newBuilder().addBid(bid);
			bidResponse.addSeatbid(seatBid);
		});

		return bidResponse.build();
	}

	@Path("/upload/creative")
	public AdxResponse uploadMockCreative() {
		LOG.info("upload mocked creative for test");
		Creative creative = new Creative();
		creative.setAdvertiserId(setting.getAdvertiserId());
		creative.setCreativeUrl("http://cdn.f2time.com/image/20180613/4ff4477ec8714b20bbc7ddfc2093118f_tmp.jpg");
		creative.setLandingPage("http://www.iqiyi.com");
		creative.setName("网易-大话西游-信息流-"+setting.getDspId());
		creative.setCreativeId(setting.getCrid());
		//creative.setAdPositionId(setting.getAdPosId());

		String serviceUrl = String.format(config.getProperty("adx.service.url.creative.add"), setting.getToken());
		AdxResponse response = OkHttpUtils.getBodyAsObject(serviceUrl, creative, AdxResponse.class);
		return response;
	}
	
	@Path("/upload/advertiser")
	public AdxResponse uploadMockAdvertiser() {
		LOG.info("upload mocked advertiser for test");

		Advertiser advertiser = new Advertiser();
		advertiser.setName("完美世界-"+setting.getDspId());
		advertiser.setAddress("北京市朝阳区光华路soho2");
		advertiser.setAdvertiserId(setting.getAdvertiserId());
		advertiser.setBusinessLicense("http://cdn.f2time.com/image/20180613/4ff4477ec8714b20bbc7ddfc2093118f_tmp.jpg");
		advertiser.setQualification("http://cdn.f2time.com/image/20180613/4ff4477ec8714b20bbc7ddfc2093118f_tmp.jpg");

		advertiser.setIndustryType(setting.getIndustry());

		String serviceUrl = String.format(config.getProperty("adx.service.url.advertiser.add"), setting.getToken());
		AdxResponse response = OkHttpUtils.getBodyAsObject(serviceUrl, advertiser, AdxResponse.class);
		return response;
	}
}
