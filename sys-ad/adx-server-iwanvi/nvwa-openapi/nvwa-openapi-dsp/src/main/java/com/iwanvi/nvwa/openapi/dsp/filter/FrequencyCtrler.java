package com.iwanvi.nvwa.openapi.dsp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;

import ai.houyi.dorado.spring.SpringContainer;

/**
 * 频次控制
 */
public class FrequencyCtrler {

	private static final Logger LOG = LoggerFactory.getLogger("FrequencyCtrler");

	private static RedisDao redisDao = SpringContainer.get().getBean("redisDao");
	
	public static boolean isFilter(int platformId, int frequency, String did) {
		boolean isFilter = false;
		try {
			if (StringUtils.isBlank(did) || frequency <= 0) {
				return isFilter;
			}
			String key = StringUtils.buildString(Constants.KEY_REDIS_FREQUENCY, platformId, did);
			isFilter = NvwaUtils.obj2int(redisDao.get(key)) >= frequency;
			if (isFilter) {
				LOG.info("FrequencyCtrler filter. ");
			}
		} catch (Exception e) {
			LOG.error("isFilter error. ", e);
		}
		return isFilter;
	}
	
	public static void updateFrequency(int platformId, String did) {
		try {
			String key = StringUtils.buildString(Constants.KEY_REDIS_FREQUENCY, platformId, did);
			if (NvwaUtils.obj2int(redisDao.get(key)) > 0) {
				redisDao.incr(key);
			} else {
				redisDao.set(key, String.valueOf(1), DateUtils.EXPIRE_TIME);
			}
		} catch (Exception e) {
			LOG.error("updateFrequency error. ", e);
		}
	}
}
