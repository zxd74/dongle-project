/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.search;

import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.AdTypeConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.Advertiser;
import com.iwanvi.nvwa.proto.AdModelsProto.Agent;
import com.iwanvi.nvwa.proto.AdModelsProto.AgentFloorPriceConfig;
import com.iwanvi.nvwa.proto.AdModelsProto.App;

/**
 * 
 * @author wangwp
 */
public class SearchItem {
	private AdUnit adUnit;
	private AdPlan adPlan;
	private Advertiser advertiser;
	private AdTypeConfig adTypeConfig;
	private Agent agent;
	private AgentFloorPriceConfig agentFloorPriceConfig;
	private App app;

	private int bidPrice; // dsp平台出价
	private int adxBidPrice;// adx出价
	private int paidPrice;
	private float score; // 用来做rank的score

	private SearchItem(Builder builder) {
		this.adPlan = builder.adPlan;
		this.adUnit = builder.adUnit;
		this.advertiser = builder.advertiser;
		this.adTypeConfig = builder.adTypeConfig;
		this.bidPrice = builder.bidPrice;
		this.adxBidPrice = builder.adxBidPrice;
		this.paidPrice = builder.paidPrice;
		this.score = builder.score;
		this.agent = builder.agent;
		this.agentFloorPriceConfig = builder.agentFloorPriceConfig;
		this.app = builder.app;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private AdUnit adUnit;
		private AdPlan adPlan;
		private Advertiser advertiser;
		private AdTypeConfig adTypeConfig;

		private int bidPrice; // dsp平台出价
		private int adxBidPrice;// adx出价
		private int paidPrice;
		private float score; // 用来做rank的score
		private Agent agent;
		private AgentFloorPriceConfig agentFloorPriceConfig;
		private App app;

		public Builder setAgent(Agent agent) {
			this.agent = agent;
			return this;
		}

		/**
		 * @return the agent
		 */
		public Agent getAgent() {
			return agent;
		}

		/**
		 * @return the agentFloorPriceConfig
		 */
		public AgentFloorPriceConfig getAgentFloorPriceConfig() {
			return agentFloorPriceConfig;
		}

		public Builder setAgentFloorPriceConfig(AgentFloorPriceConfig agentFloorPriceConfig) {
			this.agentFloorPriceConfig = agentFloorPriceConfig;
			return this;
		}

		public App getApp() {
			return this.app;
		}

		public Builder setApp(App app) {
			this.app = app;
			return this;
		}

		/**
		 * @param adUnit
		 *            the adUnit to set
		 */
		public Builder setAdUnit(AdUnit adUnit) {
			this.adUnit = adUnit;
			return this;
		}

		/**
		 * @param adPlan
		 *            the adPlan to set
		 */
		public Builder setAdPlan(AdPlan adPlan) {
			this.adPlan = adPlan;
			return this;
		}

		/**
		 * @param advertiser
		 *            the advertiser to set
		 */
		public Builder setAdvertiser(Advertiser advertiser) {
			this.advertiser = advertiser;
			return this;
		}

		/**
		 * @param adTypeConfig
		 *            the adTypeConfig to set
		 */
		public Builder setAdTypeConfig(AdTypeConfig adTypeConfig) {
			this.adTypeConfig = adTypeConfig;
			return this;
		}

		/**
		 * @param bidPrice
		 *            the bidPrice to set
		 */
		public Builder setBidPrice(int bidPrice) {
			this.bidPrice = bidPrice;
			return this;
		}

		/**
		 * @param adxBidPrice
		 *            the adxBidPrice to set
		 */
		public Builder setAdxBidPrice(int adxBidPrice) {
			this.adxBidPrice = adxBidPrice;
			return this;
		}

		/**
		 * @param paidPrice
		 *            the paidPrice to set
		 */
		public Builder setPaidPrice(int paidPrice) {
			this.paidPrice = paidPrice;
			return this;
		}

		/**
		 * @param score
		 *            the score to set
		 */
		public Builder setScore(float score) {
			this.score = score;
			return this;
		}

		/**
		 * @return the adUnit
		 */
		public AdUnit getAdUnit() {
			return adUnit;
		}

		/**
		 * @return the adPlan
		 */
		public AdPlan getAdPlan() {
			return adPlan;
		}

		/**
		 * @return the advertiser
		 */
		public Advertiser getAdvertiser() {
			return advertiser;
		}

		/**
		 * @return the adTypeConfig
		 */
		public AdTypeConfig getAdTypeConfig() {
			return adTypeConfig;
		}

		/**
		 * @return the bidPrice
		 */
		public int getBidPrice() {
			return bidPrice;
		}

		/**
		 * @return the adxBidPrice
		 */
		public int getAdxBidPrice() {
			return adxBidPrice;
		}

		/**
		 * @return the paidPrice
		 */
		public int getPaidPrice() {
			return paidPrice;
		}

		/**
		 * @return the score
		 */
		public float getScore() {
			return score;
		}

		public SearchItem build() {
			return new SearchItem(this);
		}
	}

	/**
	 * @return the adUnit
	 */
	public AdUnit getAdUnit() {
		return adUnit;
	}

	/**
	 * @return the adPlan
	 */
	public AdPlan getAdPlan() {
		return adPlan;
	}

	/**
	 * @return the advertiser
	 */
	public Advertiser getAdvertiser() {
		return advertiser;
	}

	/**
	 * @return the adTypeConfig
	 */
	public AdTypeConfig getAdTypeConfig() {
		return adTypeConfig;
	}

	/**
	 * @return the bidPrice
	 */
	public int getBidPrice() {
		return bidPrice;
	}

	/**
	 * @return the adxBidPrice
	 */
	public int getAdxBidPrice() {
		return adxBidPrice;
	}

	/**
	 * @return the paidPrice
	 */
	public int getPaidPrice() {
		return paidPrice;
	}

	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}

	/**
	 * @return the agent
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * @return the agentFloorPriceConfig
	 */
	public AgentFloorPriceConfig getAgentFloorPriceConfig() {
		return agentFloorPriceConfig;
	}

	/**
	 * @return the app
	 */
	public App getApp() {
		return app;
	}
}
