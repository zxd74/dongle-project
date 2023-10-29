package com.iwanvi.nvwa.openapi.dsp.model;

import javax.annotation.Generated;

public class SdkConf {

	private String posId; // 广告位ID
	private int posType; // 广告位类型
	private int straChapter; // 翻章
	private int straPage; // 翻页
	private int straDuration; // 展示时长
	private int isDef; // 策略值是否是默认值

	@Generated("SparkTools")
	private SdkConf(Builder builder) {
		this.posId = builder.posId;
		this.posType = builder.posType;
		this.straChapter = builder.straChapter;
		this.straPage = builder.straPage;
		this.straDuration = builder.straDuration;
		this.isDef = builder.isDef;
	}
	
	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public int getPosType() {
		return posType;
	}

	public void setPosType(int posType) {
		this.posType = posType;
	}

	public int getIsDef() {
		return isDef;
	}

	public void setIsDef(int isDef) {
		this.isDef = isDef;
	}

	public int getStraChapter() {
		return straChapter;
	}

	public void setStraChapter(int straChapter) {
		this.straChapter = straChapter;
	}

	public int getStraPage() {
		return straPage;
	}

	public void setStraPage(int straPage) {
		this.straPage = straPage;
	}

	public int getStraDuration() {
		return straDuration;
	}

	public void setStraDuration(int straDuration) {
		this.straDuration = straDuration;
	}

	/**
	 * Creates builder to build {@link SdkConf}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link SdkConf}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String posId;
		private int posType;
		private int straChapter;
		private int straPage;
		private int straDuration;
		private int isDef;

		private Builder() {
		}

		public Builder posId(String posId) {
			this.posId = posId;
			return this;
		}

		public Builder posType(int posType) {
			this.posType = posType;
			return this;
		}

		public Builder straChapter(int straChapter) {
			this.straChapter = straChapter;
			return this;
		}
		
		public Builder straPage(int straPage) {
			this.straPage = straPage;
			return this;
		}

		public Builder straDuration(int straDuration) {
			this.straDuration = straDuration;
			return this;
		}

		public Builder isDef(int isDef) {
			this.isDef = isDef;
			return this;
		}

		public SdkConf build() {
			return new SdkConf(this);
		}
	}
}
