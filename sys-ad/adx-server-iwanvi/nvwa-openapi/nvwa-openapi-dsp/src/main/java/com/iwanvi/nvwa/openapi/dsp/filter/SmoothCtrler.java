package com.iwanvi.nvwa.openapi.dsp.filter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;

import ai.houyi.dorado.rest.util.ClassLoaderUtils;
import ai.houyi.dorado.rest.util.IOUtils;
import ai.houyi.dorado.spring.SpringContainer;

/**
 * 匀速控制
 */
public class SmoothCtrler {

	private static final Logger LOG = LoggerFactory.getLogger("SmoothCtrler");

	private static final String LUA_RATELIMITER = IOUtils.toString(ClassLoaderUtils.getStream("smooth.lua"));

	@Resource
	private static RedisDao redisDao = SpringContainer.get().getBean(RedisDao.class);
	
	public static boolean isFilter(Map<String, Object> map) {
		boolean isFilter = false;
		try {
			double bidRate = calRate(map);
			int timeInterval = 1;
			if (bidRate < 1 && bidRate > 0) {
				timeInterval = (int) Math.round(1 / bidRate);
				bidRate = 1;
			}
			int limit = (int) Math.round(bidRate);
			
			List<String> keys = Collections.singletonList(NvwaUtils.obj2Empty(map.get("schId")));
			List<String> argv = Arrays.asList(String.valueOf(limit), String.valueOf(timeInterval));
			
			if (1 == Integer.valueOf(redisDao.execute(LUA_RATELIMITER, keys, argv))) {
				isFilter = true;
				LOG.info("SmoothCtrler filter. ");
			}
		} catch (Exception e) {
			LOG.error("isFilter error. ", e);
		}
		return isFilter;
	}
	
	private static double calRate(Map<String, Object> map) {
		double rate = 1;
		try {
			double expLimit = Double.parseDouble(NvwaUtils.obj2Empty(map.get("expLimit")));
			int chargeType = NvwaUtils.obj2int(map.get("chargeType"));
			float ctr = NvwaUtils.obj2float(map.get("forecastCtr"), 0);
			if (chargeType == 141 && ctr != 0) { // cpc
				expLimit = expLimit / ctr;
			}
			String timeSlice = NvwaUtils.obj2Empty(map.get("timeSlice"));
			if (StringUtils.isBlank(timeSlice)) { // 全时段
				long now = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				long overTime = (now - (sdf.parse(sdf.format(now)).getTime()))/1000;
				rate = expLimit / overTime;
			} else {
				String[] array = timeSlice.split(",");
				int b = 0;
				int e = 0;
				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				long second = 0;
				for (int i = 0; i < array.length; i++) {
					b = Integer.valueOf(array[i].split("-")[0]);
					e = Integer.valueOf(array[i].split("-")[1]);
					if (hour > e) {
						continue;
					}
					if (hour >= b && hour <= e) {
						StringBuilder builder = new StringBuilder(
								DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
						builder = e < 10 ? builder.append("0").append(e) : builder.append(e);
						Date enDate = DateUtils.parse(builder.toString(), DateUtils.SHORT_FORMAT_HOUR);
						second += (enDate.getTime() - new Date().getTime()) / 1000;
						continue;
					}
					if (hour < b) {
						StringBuilder builder0 = new StringBuilder(
								DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
						builder0 = b < 10 ? builder0.append("0").append(b) : builder0.append(b);
						Date beginDate = DateUtils.parse(builder0.toString(), DateUtils.SHORT_FORMAT_HOUR);
						
						StringBuilder builder = new StringBuilder(
								DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
						builder = e < 10 ? builder.append("0").append(e) : builder.append(e);
						Date enDate = DateUtils.parse(builder.toString(), DateUtils.SHORT_FORMAT_HOUR);
						
						second += (enDate.getTime() - beginDate.getTime()) / 1000;
					}
				}
				if (second > 0) {
					rate = expLimit / second;
				}
			}
		} catch (Exception e) {
			LOG.error("calRate error. ", e);
			e.printStackTrace();
		}
		return rate;
	}
	
	public static void main(String[] args) {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
