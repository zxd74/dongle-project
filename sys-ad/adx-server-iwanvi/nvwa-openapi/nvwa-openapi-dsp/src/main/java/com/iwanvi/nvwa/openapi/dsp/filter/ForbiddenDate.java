package com.iwanvi.nvwa.openapi.dsp.filter;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.openapi.dsp.utils.Constants;

/**
 *  禁投过滤
 */
public class ForbiddenDate {

	private static final Logger LOG = LoggerFactory.getLogger("ForbiddenDate");

	public static boolean isFilter() {
		boolean isFilter = false;
		try {
			String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
			isFilter = Constants.FORBIDDEN_DATE.contains(today);
			if (isFilter) {
				LOG.info("ForbiddenDate filter. ");
			}
		} catch (Exception e) {
			LOG.error("isFilter error. ", e);
		}
		return isFilter;
	}
}
