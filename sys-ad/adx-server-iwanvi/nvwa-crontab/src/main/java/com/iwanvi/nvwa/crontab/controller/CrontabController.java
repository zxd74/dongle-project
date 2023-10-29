package com.iwanvi.nvwa.crontab.controller;

/*import org.apache.log4j.Level;
import org.apache.log4j.LogManager;*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.crontab.service.CrontabService;

@RestController
@RequestMapping("/crontab")
public class CrontabController {

	private static final Logger logger = LoggerFactory.getLogger(CrontabController.class);

	@Autowired
	private CrontabService crontabService;

	/**
	 * 全量同步
	 * 
	 * @param msgCtx
	 * @return
	 */
	@RequestMapping("/pub4Entire")
	public String pub4Entire() {
		new Thread(new Runnable() {
			public void run() {
				crontabService.pub4Entire(false);
			}
		}).start();
		return JsonUtils.STATUS_OK();
	}

	/**
	 * 增量同步
	 */
	@RequestMapping("/pub4Incremental")
	public String pub4Incremental(Integer id) {
		if (id != null) {
			new Thread(new Runnable() {
				public void run() {
					crontabService.pub4Incremental(id);
				}
			}).start();
			return JsonUtils.STATUS_OK();
		} else {
			return JsonUtils.PARAMETER_ERROR();
		}
	}
	
	/**
	 * 得到广告位底价
	 */
	@RequestMapping("/getAdpostionPrice")
	public String getAdpostionPrice(Integer id) {
		return crontabService.getAdpostionPrice(id);
	}
}
