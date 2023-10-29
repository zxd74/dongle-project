package com.iwanvi.nvwa.openapi.dsp.filter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;

/**
 * SDK 平台流量控制
 */
public class PlatformCtrler {

	private static final Logger LOG = LoggerFactory.getLogger("PlatformCtrl");

	public static boolean isFilter(String appId, String channel, String version) {
		boolean isFilter = false;
		try {
			Map<String, Object> map = null;
			for (String str : Constants.PLATFORM_LIMIT) {
				map = JsonUtils.parse2Map(str);
				if (appId.equals(NvwaUtils.obj2Empty(map.get("app_uuid")))
						&& channel.equals(NvwaUtils.obj2Empty(map.get("c_id")))
						&& version.equals(NvwaUtils.obj2Empty(map.get("v_id")))) {
					isFilter = true;
					LOG.info("PlatformCtrl filter. ");
				}
			}
		} catch (Exception e) {
			LOG.error("isFilter error. ", e);
		}
		return isFilter;
	}
	
}
