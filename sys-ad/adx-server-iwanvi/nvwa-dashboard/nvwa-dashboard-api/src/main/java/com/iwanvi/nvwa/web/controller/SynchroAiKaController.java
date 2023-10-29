package com.iwanvi.nvwa.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.web.service.SynchroAiKaService;

@RestController
@RequestMapping("synchroAiKa")
public class SynchroAiKaController {

//	public static final Logger logger = LoggerFactory.getLogger(SynchroAiKaController.class);
//	
//	@Autowired
//	private SynchroAiKaService synchroAiKaService;
//	
//	@RequestMapping("getOrderByOrderId")
//	public String synchroAiKaOrderByOrderId(Integer orderId){
//		String result;
//		if (orderId != null) {
//			try {
//				synchroAiKaService.synchroAiKaOrderPutByOrderId(orderId);
//				logger.info("synchroAiKa getOrderByOrderId success orderId : {}",orderId);
//				result = JsonUtils.STATUS_OK();
//			} catch (Exception e) {
//				result = JsonUtils.EXCEPTION_ERROR();
//				logger.error("synchroAiKa getOrderByOrderId error",e);
//			}
//		}else {
//			logger.error("synchroAiKa getOrderByOrderId error beacuse orderId is null");
//			result = JsonUtils.PARAMETER_ERROR();
//		}
//		return result;
//	};
//	
//	@RequestMapping("syncByDateStr")
//	public String syncByDate(String date){
//		String result;
//		if (StringUtils.isNotBlank(date)) {
//			try {
//				synchroAiKaService.syncTask(date);
//				result = JsonUtils.STATUS_OK();
//				logger.info("synchroAiKa syncByDate success");
//			} catch (Exception e) {
//				result = JsonUtils.EXCEPTION_ERROR();
//				logger.error("synchroAiKa syncByDate error",e);
//			}
//		}else {
//			result = JsonUtils.PARAMETER_ERROR();
//			logger.error("synchroAiKa syncByDate error beacuse param is error, date: {}",date);
//		}
//		return result;
//	}
}
