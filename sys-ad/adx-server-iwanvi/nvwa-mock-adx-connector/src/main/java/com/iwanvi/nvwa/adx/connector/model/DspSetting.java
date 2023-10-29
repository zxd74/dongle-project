/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.adx.connector.model;

import java.io.IOException;
import java.util.Properties;

import com.iwanvi.nvwa.adx.connector.Application;

/**
 * @author weiping wang
 *
 */
public class DspSetting {
	static Properties config = new Properties();
	static String _token;

	static {
		try {
			config.load(Application.class.getClassLoader().getResourceAsStream("application.properties"));
			_token = config.getProperty("dsp.token");
		} catch (IOException ex) {
		}
	}

	private String token;
	private String crid;
	private Integer bidPrice;
	@Override
	public String toString() {
		return "DspSetting [token=" + token + ", crid=" + crid + ", bidPrice=" + bidPrice + ", advertiserId="
				+ advertiserId + ", adPosId=" + adPosId + ", industry=" + industry + ", dspId=" + dspId + "]";
	}

	private String advertiserId;
	private Integer adPosId = 32;
	private Integer industry = 27;
	private String dspId;

	public String getDspId() {
		return dspId;
	}

	public void setDspId(String dspId) {
		this.dspId = dspId;
	}

	public String getToken() {
		return token == null ? _token : token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCrid() {
		return crid == null ? "rtb-test-creative-"+getDspId() : crid;
	}

	public void setCrid(String crid) {
		this.crid = crid;
	}

	public Integer getBidPrice() {
		return bidPrice==null?1000:bidPrice;
	}

	public void setBidPrice(Integer bidPrice) {
		this.bidPrice = bidPrice;
	}

	public String getAdvertiserId() {
		return advertiserId == null ? "rtb-test-advertiser-"+getDspId() : advertiserId;
	}

	public void setAdvertiserId(String advertiserId) {
		this.advertiserId = advertiserId;
	}

	public Integer getAdPosId() {
		return adPosId == null ? 32 : adPosId;
	}

	public void setAdPosId(Integer adPosId) {
		this.adPosId = adPosId;
	}

	public Integer getIndustry() {
		return industry == null ? 27 : industry;
	}

	public void setIndustry(Integer industry) {
		this.industry = industry;
	}
}
