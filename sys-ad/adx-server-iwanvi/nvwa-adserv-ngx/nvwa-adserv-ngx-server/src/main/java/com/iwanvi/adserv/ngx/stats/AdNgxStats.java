/**
 * 
 */
package com.iwanvi.adserv.ngx.stats;

import javax.annotation.Generated;

/**
 * @author wangweiping
 *
 */
public class AdNgxStats {
	private double avgRt;
	private int dspRouterActiveThreadCount;
	
	public double getAvgRt() {
		return avgRt;
	}
	public int getDspRouterActiveThreadCount() {
		return dspRouterActiveThreadCount;
	}
	@Generated("SparkTools")
	private AdNgxStats(Builder builder) {
		this.avgRt = builder.avgRt;
		this.dspRouterActiveThreadCount = builder.dspRouterActiveThreadCount;
	}
	@Generated("SparkTools")
	public AdNgxStats() {
	}
	/**
	 * Creates builder to build {@link AdNgxStats}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link AdNgxStats}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private double avgRt;
		private int dspRouterActiveThreadCount;

		private Builder() {
		}

		public Builder withAvgRt(double avgRt) {
			this.avgRt = avgRt;
			return this;
		}

		public Builder withDspRouterActiveThreadCount(int dspRouterActiveThreadCount) {
			this.dspRouterActiveThreadCount = dspRouterActiveThreadCount;
			return this;
		}

		public AdNgxStats build() {
			return new AdNgxStats(this);
		}
	}
	
	
}
