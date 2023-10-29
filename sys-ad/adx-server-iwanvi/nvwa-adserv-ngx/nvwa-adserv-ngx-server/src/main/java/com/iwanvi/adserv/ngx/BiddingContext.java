/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.search.SearchItem;
import com.iwanvi.adserv.ngx.util.BiddingTracer;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.ProtobufUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdCommonConfig;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingReq;
import com.iwanvi.nvwa.proto.BiddingProto.BiddingRsp;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;

/**
 * 
 * @author wangwp
 */
public class BiddingContext {
	private static final Logger LOG = LoggerFactory.getLogger("biddingTracer");

	private final BiddingReq biddingReq;
	private final PosInfo posInfo;
	private final UserInfo userInfo;

	private final boolean isVideoReq;
	private final boolean isNativeReq;
	private final boolean isDynamicReq;
	private final boolean isOttReq;
	private final boolean isPmp;

	private BiddingRsp biddingRsp;
	private List<SearchItem.Builder> searchResults;
	private boolean skipSmoothBudgetDelivery = true;
	private Map<String, String> userExt;
	private Map<String, String> adExt;
	private boolean isDebug;
	private BiddingTracer tracer;

	private static MinervaCfg minervaCfg = MinervaCfg.get();
	private static AdCommonConfig commonCfg = RepositoryFactory.getRepository().loadAdCommonConfig();

	public BiddingContext(BiddingReq biddingReq, PosInfo posInfo, UserInfo userInfo) {
		this.biddingReq = biddingReq;
		this.posInfo = posInfo;
		this.userInfo = userInfo;

		this.isNativeReq = checkRequestType(CreativeType.kNative);
		this.isDynamicReq = checkRequestType(CreativeType.kDynamic);
		this.isVideoReq = checkRequestType(CreativeType.kVideo);
		this.isOttReq = isOttReq();
		this.isDebug = biddingReq.getIsDebug();

		this.isPmp = posInfo.getDealidCount() > 0;
		// this.isPmp = posInfo.get
		if (isDebug) {
			tracer = BiddingTracer.newTracer();
		}
	}

	public BiddingContext(BiddingReq biddingReq) {
		this(biddingReq, biddingReq.getPosInfo(0), biddingReq.getUserInfo());
	}

	public static BiddingContext create(BiddingReq biddingReq) {
		return new BiddingContext(biddingReq);
	}

	public static BiddingContext create(BiddingReq biddingReq, PosInfo posInfo) {
		return new BiddingContext(biddingReq, posInfo, biddingReq.getUserInfo());
	}

	private boolean isOttReq() {
		// PosInfo posInfo = this.posInfo;
		for (MapEntry ext : posInfo.getExtList()) {
			if ("dx_screen".equals(ext.getKey()))
				return true;
		}
		return false;
	}

	private boolean checkRequestType(CreativeType creativeType) {
		// PosInfo posInfo = this.posInfo;
		for (int i = 0; i < posInfo.getCreativeTypeCount(); i++) {
			if (creativeType == posInfo.getCreativeType(i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the posInfo
	 */
	public PosInfo getPosInfo() {
		return posInfo;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setBiddingRsp(BiddingRsp biddingRsp) {
		this.biddingRsp = biddingRsp;
	}

	/**
	 * @return the biddingRsp
	 */
	public BiddingRsp getBiddingRsp() {
		return biddingRsp;
	}

	/**
	 * @return the biddingReq
	 */
	public BiddingReq getBiddingReq() {
		return biddingReq;
	}

	/**
	 * @return the adCommonConfig
	 */
	public AdCommonConfig getAdCommonConfig() {
		return commonCfg;
	}

	/**
	 * @return the hits
	 */
	public List<SearchItem.Builder> getSearchResults() {
		return searchResults;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setSearchResults(List<SearchItem.Builder> searchResults) {
		this.searchResults = searchResults;
	}

	public boolean isOttAdReq() {
		return this.isOttReq;
	}

	/**
	 * @return the isVideoReq
	 */
	public boolean isVideoReq() {
		return isVideoReq;
	}

	/**
	 * @return the isNativeReq
	 */
	public boolean isNativeReq() {
		return isNativeReq;
	}

	/**
	 * @return the isDynamicReq
	 */
	public boolean isDynamicReq() {
		return isDynamicReq;
	}

	public MinervaCfg getMinervaCfg() {
		return minervaCfg;
	}

	/**
	 * @return the skipSmoothBudgetDelivery
	 */
	public boolean isSkipSmoothBudgetDelivery() {
		return skipSmoothBudgetDelivery;
	}

	public void setSkipSmoothBudgetDelivery(boolean skipSmoothBudgetDelivery) {
		this.skipSmoothBudgetDelivery = skipSmoothBudgetDelivery;
	}

	public void setUserExt(Map<String, String> ext) {
		this.userExt = ext;
	}

	public void setAdExt(Map<String, String> ext) {
		this.adExt = ext;
	}

	public Map<String, String> getUserExt() {
		return this.userExt;
	}

	public Map<String, String> getAdExt() {
		return this.adExt;
	}

	public long getUserExtPropertyAsLong(String key) {
		return NumberUtils.toLong(getUserExtProperty(key));
	}

	public int getUserExtPropertyAsInt(String key) {
		return NumberUtils.toInt(getUserExtProperty(key));
	}

	public String getUserExtProperty(String key) {
		if (this.userExt == null) {
			initUserExt();
		}
		if (userExt == null)
			return StringUtils.EMPTY;
		String value = userExt.get(key);
		return value == null ? StringUtils.EMPTY : value;
	}

	private void initUserExt() {
		List<MapEntry> extList = new ArrayList<>(userInfo.getExtCount() + posInfo.getExtCount());
		extList.addAll(userInfo.getExtList());
		extList.addAll(posInfo.getExtList());

		this.userExt = ProtobufUtils.convertListToMap(extList);
	}

	public int getAdExtPropertyAsInt(String key) {
		return NumberUtils.toInt(getAdExtProperty(key));
	}

	public long getAdExtPropertyAsLong(String key) {
		return NumberUtils.toLong(getAdExtProperty(key));
	}

	public String getAdExtProperty(String key) {
		if (adExt == null) {
			return StringUtils.EMPTY;
		}

		String value = adExt.get(key);
		return value == null ? StringUtils.EMPTY : value;
	}

	public String[] getAdExtPropertyAsArray(String key) {
		if (adExt == null) {
			return null;
		}

		String value = adExt.get(key);
		return value == null ? null : value.split(Constants.SIGN_COMMA);
	}

	public boolean isPmp() {
		return this.isPmp;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void trace(String format, Object... args) {
		if (isDebug) {
			this.tracer.trace(format, args);
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug(format, args);
			}
		}
	}
}
