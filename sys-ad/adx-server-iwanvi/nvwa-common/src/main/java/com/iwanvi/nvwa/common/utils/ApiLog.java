package com.iwanvi.nvwa.common.utils;

import javax.annotation.Generated;

import com.iwanvi.nvwa.common.utils.StringUtils;

public class ApiLog {

	// timestamp|reqId|appId|posId|channel1|version|did|os|ip|areacode|channel2|lev1|lev2|lev3
	private static final String FORMAT = "{0}|{1}|{2}|{3}|{4}|{5}|{6}|{7}|{8}|{9}|{10}|{11}|{12}|{13}";
	private String timestamp;
	private String reqId;
	private String appId;
	private String posId;
	private String vesion;
	private String channel1;
	private String channel2;
	private String did;
	private String os;
	private String ip;
	private String areacode;
	private String lev1;
	private String lev2;
	private String lev3;
	@Generated("SparkTools")
	private ApiLog(Builder builder) {
		this.timestamp = builder.timestamp;
		this.reqId = builder.reqId;
		this.appId = builder.appId;
		this.posId = builder.posId;
		this.vesion = builder.vesion;
		this.channel1 = builder.channel1;
		this.channel2 = builder.channel2;
		this.did = builder.did;
		this.os = builder.os;
		this.ip = builder.ip;
		this.areacode = builder.areacode;
		this.lev1 = builder.lev1;
		this.lev2 = builder.lev2;
		this.lev3 = builder.lev3;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public String getVesion() {
		return vesion;
	}
	public void setVesion(String vesion) {
		this.vesion = vesion;
	}
	public String getChannel1() {
		return channel1;
	}
	public void setChannel1(String channel1) {
		this.channel1 = channel1;
	}
	public String getChannel2() {
		return channel2;
	}
	public void setChannel2(String channel2) {
		this.channel2 = channel2;
	}
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getLev1() {
		return lev1;
	}
	public void setLev1(String lev1) {
		this.lev1 = lev1;
	}
	public String getLev2() {
		return lev2;
	}
	public void setLev2(String lev2) {
		this.lev2 = lev2;
	}
	public String getLev3() {
		return lev3;
	}
	public void setLev3(String lev3) {
		this.lev3 = lev3;
	}
	/**
	 * Creates builder to build {@link ApiLog}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link ApiLog}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String timestamp;
		private String reqId;
		private String appId;
		private String posId;
		private String vesion;
		private String channel1;
		private String channel2;
		private String did;
		private String os;
		private String ip;
		private String areacode;
		private String lev1;
		private String lev2;
		private String lev3;

		private Builder() {
		}

		public Builder withTimestamp(String timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder withReqId(String reqId) {
			this.reqId = reqId;
			return this;
		}

		public Builder withAppId(String appId) {
			this.appId = appId;
			return this;
		}

		public Builder withPosId(String posId) {
			this.posId = posId;
			return this;
		}

		public Builder withVesion(String vesion) {
			this.vesion = vesion;
			return this;
		}

		public Builder withChannel1(String channel1) {
			this.channel1 = channel1;
			return this;
		}

		public Builder withChannel2(String channel2) {
			this.channel2 = channel2;
			return this;
		}

		public Builder withDid(String did) {
			this.did = did;
			return this;
		}

		public Builder withOs(String os) {
			this.os = os;
			return this;
		}

		public Builder withIp(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder withAreacode(String areacode) {
			this.areacode = areacode;
			return this;
		}

		public Builder withLev1(String lev1) {
			this.lev1 = lev1;
			return this;
		}

		public Builder withLev2(String lev2) {
			this.lev2 = lev2;
			return this;
		}

		public Builder withLev3(String lev3) {
			this.lev3 = lev3;
			return this;
		}

		public ApiLog build() {
			return new ApiLog(this);
		}
	}

	public String toString() {
		return StringUtils.buildString(FORMAT, getTimestamp(), getReqId(), getAppId(), getPosId(), getChannel1(), getVesion(),
				getDid(), getOs(), getIp(), getAreacode(), getChannel2(), getLev1(), getLev2(), getLev3());
	}
}
