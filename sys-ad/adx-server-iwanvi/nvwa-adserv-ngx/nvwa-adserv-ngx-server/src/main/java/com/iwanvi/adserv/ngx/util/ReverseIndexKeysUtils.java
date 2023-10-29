/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import static com.iwanvi.adserv.ngx.util.Constants.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.proto.AdModelsProto.AdUnit;
import com.iwanvi.nvwa.proto.AdModelsProto.AreaTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.ConnectTypeTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.GenderTarget;
import com.iwanvi.nvwa.proto.AdModelsProto.OsTarget;
import com.iwanvi.nvwa.proto.BiddingProto.PosInfo;
import com.iwanvi.nvwa.proto.BiddingProto.UserInfo;
import com.iwanvi.nvwa.proto.CommonProto.ConnectType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;
import com.iwanvi.nvwa.proto.CommonProto.MapEntry;

/**
 * 倒排索引key生成工具
 * 
 * @author wangwp
 */
public class ReverseIndexKeysUtils {
	static final Logger LOG = LoggerFactory.getLogger("MinervaNotifyConsumer");

	public static List<Long> buildReverseIndexKeys(PosInfo posInfo, UserInfo userInfo) {
		boolean isOttAdRequest = isOttAdRequest(posInfo, userInfo);
		if (isOttAdRequest) {
			return buildOttReverseIndexKeys(posInfo, userInfo);
		}
		// 地域定向,支持三级城市定向
		List<String> areas = new ArrayList<>(4);
		areas.add(DEFAULT_TARGET_STRING);
		if (userInfo.hasAreaCode()) {
			int areaCode = userInfo.getAreaCode();
			int provinceCode = (areaCode / 10000) * 10000; // 省
			int cityCode = (areaCode / 100) * 100; // 市

			areas.add(String.valueOf(provinceCode));
			areas.add(String.valueOf(cityCode));
			areas.add(String.valueOf(areaCode));
		}

		// 性别相关
		List<String> genders = new ArrayList<>(2);
		genders.add(DEFAULT_TARGET_STRING);
		if (userInfo.hasGenderType()) {
			genders.add(String.valueOf(userInfo.getGenderType().getNumber()));
		}

		// 网络类型
		List<String> connectTypes = new ArrayList<>(2);
		connectTypes.add(DEFAULT_TARGET_STRING);
		if (userInfo.hasConnectType()) {
			ConnectType connectType = userInfo.getConnectType();
			connectTypes.add(String.valueOf(connectType.getNumber()));
		}

		// 操作系统
		List<String> osTypes = new ArrayList<>(2);
		osTypes.add(DEFAULT_TARGET_STRING);
		if (userInfo.hasOs()) {
			osTypes.add(String.valueOf(userInfo.getOs().getNumber()));
		}

		// 其他fields
		List<String> otherFields = new ArrayList<>();
		for (int i = 0; i < posInfo.getCreativeTypeCount(); i++) {
			CreativeType creativeType = posInfo.getCreativeType(i);

			switch (creativeType) {
			case kPic:
			case kDynamic:
				otherFields.add(creativeType.getNumber() + REVERSE_KEY_JOIN_SIGN
						+ String.format("%d^%d", posInfo.getWidth(), posInfo.getHeight()));
				break;
			case kNative:
				// 原生广告按照广告类型和广告位id进行匹配
				otherFields.add(creativeType.getNumber() + REVERSE_KEY_JOIN_SIGN + posInfo.getAdTypeId());
				break;
			case kVideo:
				// 视频和动态广告匹配在这里优先通过广告类型匹配，在IndexSearcher会进行进一步筛选
				otherFields.add(String.valueOf(creativeType.getNumber()));
				break;
			default:
				otherFields.add(String.valueOf(creativeType.getNumber()));
				break;
			}
		}

		Set<Long> reverseIndexKeySet = new HashSet<Long>();

		for (String area : areas) {
			for (String gender : genders) {
				for (String connectType : connectTypes) {
					for (String osType : osTypes) {
						for (String otherField : otherFields) {
							String keystr = StringUtils.join(
									new Object[] { area, gender, connectType, osType, otherField },
									REVERSE_KEY_JOIN_SIGN);
							long reverseIndexKey = MurmurHash.hash2_64(keystr, HASHING_SEED);
							reverseIndexKeySet.add(reverseIndexKey);
						}
					}
				}
			}
		}
		List<Long> reverseIndexKeys = new ArrayList<Long>(reverseIndexKeySet.size());
		reverseIndexKeys.addAll(reverseIndexKeySet);
		return reverseIndexKeys;
	}

	private static List<Long> buildOttReverseIndexKeys(PosInfo posInfo, UserInfo userInfo) {
		List<MapEntry> allExtProps = new ArrayList<>();

		allExtProps.addAll(posInfo.getExtList());
		allExtProps.addAll(userInfo.getExtList());

		List<Long> reverseKeys = new ArrayList<>();
		for (MapEntry ext : allExtProps) {
			if ("dx_screen".equals(ext.getKey())) {
				reverseKeys.add(MurmurHash.hash2_64(ext.getValue(), HASHING_SEED));
				break;
			}
		}
		return reverseKeys;
	}

	private static boolean isOttAdRequest(PosInfo posInfo, UserInfo userInfo) {
		List<MapEntry> allExtProps = new ArrayList<>();

		allExtProps.addAll(posInfo.getExtList());
		allExtProps.addAll(userInfo.getExtList());

		for (MapEntry ext : allExtProps) {
			if ("dx_screen".equals(ext.getKey())) {
				return true;
			}
		}
		return false;
	}

	public static List<Long> buildReverseIndexKeys(AdUnit adUnit) {
		boolean isOttAd = isOttAd(adUnit);
		if (isOttAd) {
			return buildOttAdReverseIndexes(adUnit);
		}
		// 地域定向
		List<String> areas = new ArrayList<>();
		if (adUnit.hasAreaTarget()) {
			AreaTarget areaTarget = adUnit.getAreaTarget();
			for (int i = 0; i < areaTarget.getAreaCodeCount(); i++) {
				areas.add(String.valueOf(areaTarget.getAreaCode(i)));
			}
		} else {
			areas.add(DEFAULT_TARGET_STRING);
		}

		// 性别定向
		List<String> genders = new ArrayList<>();
		if (adUnit.hasGenderTarget()) {
			GenderTarget genderTarget = adUnit.getGenderTarget();
			for (int i = 0; i < genderTarget.getGenderCount(); i++) {
				genders.add(String.valueOf(genderTarget.getGender(i).getNumber()));
			}
		} else {
			genders.add(DEFAULT_TARGET_STRING);
		}

		// 网络类型
		List<String> connectTypes = new ArrayList<>();
		if (adUnit.hasConnectTypeTarget()) {
			ConnectTypeTarget connectTypeTarget = adUnit.getConnectTypeTarget();
			for (int i = 0; i < connectTypeTarget.getConnectTypeCount(); i++) {
				connectTypes.add(String.valueOf(connectTypeTarget.getConnectType(i).getNumber()));
			}
		} else {
			connectTypes.add(DEFAULT_TARGET_STRING);
		}

		// 操作系统定向
		List<String> osTypes = new ArrayList<>();
		if (adUnit.hasOsTarget()) {
			OsTarget osTarget = adUnit.getOsTarget();
			for (int i = 0; i < osTarget.getOsCount(); i++) {
				osTypes.add(String.valueOf(osTarget.getOs(i).getNumber()));
			}
		} else {
			osTypes.add(DEFAULT_TARGET_STRING);
		}

		// 广告计费类型、广告类型、时长等
		StringBuilder compositeField = new StringBuilder();
		List<String> composites = new ArrayList<>();
		CreativeType creativeType = adUnit.getCreativeType();

		switch (creativeType) {
		case kFirstScreen:
		case kFocusPic:
		case kPic:
		case kScreen:
			// 这里需要将所有图片类型的创意类型都统一转换为kPic
			// creative_type^ad_type_id^width^height
			compositeField.append(CreativeType.kPic_VALUE);
			compositeField.append(REVERSE_KEY_JOIN_SIGN);
			compositeField.append(adUnit.getWidth()).append(REVERSE_KEY_JOIN_SIGN).append(adUnit.getHeight());
			composites.add(compositeField.toString());
			break;
		case kNative:
			String[] adxAdTypeIds = StringUtils.split(adUnit.getAdxAdTypeId(), SIGN_COMMA);
			for (String adxAdTypeId : adxAdTypeIds) {
				// creative_type^adx_ad_type_id
				compositeField = new StringBuilder();
				compositeField.append(CreativeType.kNative_VALUE);
				compositeField.append(REVERSE_KEY_JOIN_SIGN).append(adxAdTypeId);
				composites.add(compositeField.toString());
			}
			break;
		case kDynamic:
			compositeField.append(CreativeType.kDynamic_VALUE);
			compositeField.append(REVERSE_KEY_JOIN_SIGN);
			compositeField.append(adUnit.getWidth()).append(REVERSE_KEY_JOIN_SIGN).append(adUnit.getHeight());
			composites.add(compositeField.toString());
			break;
		default:
			compositeField.append(creativeType.getNumber());
			composites.add(compositeField.toString());
			break;
		}

		Set<Long> reverseIndexKeySet = new HashSet<Long>();

		for (String area : areas) {
			for (String gender : genders) {
				for (String connectType : connectTypes) {
					for (String osType : osTypes) {
						for (String composite : composites) {
							String str = StringUtils.join(new Object[] { area, gender, connectType, osType, composite },
									REVERSE_KEY_JOIN_SIGN);
							long key = MurmurHash.hash2_64(str, HASHING_SEED);
							reverseIndexKeySet.add(key);
						}
					}
				}
			}
		}

		List<Long> reverseKeys = new ArrayList<>(reverseIndexKeySet.size());
		reverseKeys.addAll(reverseIndexKeySet);
		reverseIndexKeySet = null; // GC helper
		return reverseKeys;
	}

	private static List<Long> buildOttAdReverseIndexes(AdUnit adUnit) {
		String[] targetOttScreens = null;
		for (MapEntry extTarget : adUnit.getExtTargetsList()) {
			String extTargetKey = extTarget.getKey();
			if ("dx_screen".equals(extTargetKey)) {
				targetOttScreens = extTarget.getValue().split(Constants.SIGN_COMMA);
				break;
			}
		}

		if (targetOttScreens == null)
			return null;

		LOG.info("target ott screens's size: " + targetOttScreens.length);
		Set<Long> reverseKeySets = new HashSet<>();
		for (String targetOttScreen : targetOttScreens) {
			LOG.info("target ott screen: " + targetOttScreen);
			reverseKeySets.add(MurmurHash.hash2_64(targetOttScreen, HASHING_SEED));
		}

		List<Long> result = new ArrayList<>();
		result.addAll(reverseKeySets);
		return result;
	}

	private static boolean isOttAd(AdUnit adUnit) {
		for (MapEntry extTarget : adUnit.getExtTargetsList()) {
			String extTargetKey = extTarget.getKey();
			if ("dx_screen".equals(extTargetKey)) {
				LOG.info("is ott ad, adunit's id: " + adUnit.getUnitId());
				return true;
			}
		}
		return false;
	}
}
