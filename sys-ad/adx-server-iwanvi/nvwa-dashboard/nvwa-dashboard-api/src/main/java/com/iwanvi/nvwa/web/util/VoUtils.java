package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.support.QuotaBaseVo;
import com.iwanvi.nvwa.dao.model.support.QuotaResponseVo;

public class VoUtils {
	
	public static <T> void handleRealTimeData(Integer entId,QuotaBaseVo quotaBaseVo,RedisDao redisDao,String quotaKey,String callBackKey,String todayString,String hourString){
		String expKey = StringUtils.buildString(quotaKey, "display",entId,todayString,hourString);
		String clkKey = StringUtils.buildString(quotaKey, "click",entId,todayString,hourString);
		String costKey = StringUtils.buildString(quotaKey,"investment", entId,todayString,hourString);
		String conKey = StringUtils.buildString(quotaKey, "cost",entId,todayString,hourString);
		String expValue = redisDao.get(expKey);
		String clkValue = redisDao.get(clkKey);
		String costValue= redisDao.get(costKey);
		String conValue = redisDao.get(conKey);
		QuotaResponseVo responseVo = new QuotaResponseVo();
		if (StringUtils.isNotBlank(expValue)) {
			responseVo.setExp(Integer.parseInt(expValue));
		}
		if (StringUtils.isNotBlank(clkValue)) {
			responseVo.setClk(Integer.parseInt(clkValue));
		}else {
			clkValue = redisDao.get(StringUtils.buildString(callBackKey, entId, todayString,hourString));
			if (StringUtils.isNotBlank(clkValue)) {
				responseVo.setClk(Integer.parseInt(clkValue));
			}
		}
		if (StringUtils.isNotBlank(costValue)) {
			responseVo.setInvestment(Long.parseLong(costValue));
		}
		if (StringUtils.isNotBlank(conValue)) {
			responseVo.setCost(Long.parseLong(conValue));
		}
		VoUtils.handleResponseVo(quotaBaseVo, responseVo);
	}
	
	public static void handleResponseVo(QuotaBaseVo baseVo,QuotaResponseVo responseVo){
		baseVo.setReq(baseVo.getReq() + responseVo.getReq());
		baseVo.setExp(baseVo.getExp() + responseVo.getExp());
		baseVo.setClk(baseVo.getClk() + responseVo.getClk());
		baseVo.setCost(baseVo.getCost() + responseVo.getCost()/1000);
		baseVo.setInvestment(baseVo.getInvestment() + responseVo.getInvestment()/1000);
	}

}
