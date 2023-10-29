package com.iwanvi.adserv.ngx.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.iwanvi.adserv.ngx.BiddingContext;
import com.iwanvi.adserv.ngx.util.ProtobufUtils;
import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;

/**
 * 通用扩展定向过滤
 * 
 * @author oyy
 */
public class ExtTargetsAdFilter implements AdFilter {

	@Override
	public boolean filterAd(BiddingContext context, AdUnit adUnit) {
		UserInfo userInfo = context.getUserInfo();
		PosInfo posInfo = context.getPosInfo();

		// 如果广告有扩展定向且用户和广告位信息上都没有扩展定向则不出广告
		if (userInfo.getExtCount() == 0 && posInfo.getExtCount() == 0) {
			if (adUnit.getExtTargetsCount() > 0) {
				return true;
			}
		}

		List<MapEntry> extList = new ArrayList<>(userInfo.getExtCount() + posInfo.getExtCount());
		extList.addAll(userInfo.getExtList());
		extList.addAll(posInfo.getExtList());

		Map<String, String> extMap = ProtobufUtils.convertListToMap(extList);
		Map<String, String> extTargetsMap = ProtobufUtils.convertListToMap(adUnit.getExtTargetsList());

		context.setUserExt(extMap);
		context.setAdExt(extTargetsMap);

		// 如果广告没有扩展定向，则允许广告投放
		if (adUnit.getExtTargetsCount() == 0) {
			return false;
		}

		for (Entry<String, String> entry : extMap.entrySet()) {
			if (extTargetsMap.containsKey(entry.getKey())) {
				if ("dx_model".equals(entry.getKey()) || "dx_screen".equals(entry.getKey()))
					continue;
				Boolean existValue = ProtobufUtils.ArrayCommonElement(extMap.get(entry.getKey()),
						extTargetsMap.get(entry.getKey()));
				// 如果广告中扩展定向在广告位信息和用户信息中无匹value配值，则不出广告
				if (!existValue) {
					return true;
				}
			}
		}
		return false;
	}

}
