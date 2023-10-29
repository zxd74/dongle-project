package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.ConsumerPositionPriceMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPrice;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPriceExample;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.ConsumerPriceService;
import com.iwanvi.nvwa.web.service.SysCrontabService;

@Service
public class ConsumerPriceServiceImpl implements ConsumerPriceService {

	@Autowired
	private ConsumerPositionPriceMapper consumerPositionPriceMapper;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Autowired
	private AdDicService adDicService;
	
	@Autowired
	private SysCrontabService sysCrontabService;

	@Override
	public List<Map<String, Object>> get(Integer id) {
		ConsumerPositionPriceExample consumerPositionPriceExample = new ConsumerPositionPriceExample();
		consumerPositionPriceExample.createCriteria().andCidEqualTo(id);
		List<ConsumerPositionPrice> list = consumerPositionPriceMapper.selectByExample(consumerPositionPriceExample);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		build(list);
		Map<String, List<ConsumerPositionPrice>> resultMap = new HashMap<>();
		list.forEach(cpp -> {
			if (resultMap.containsKey(String.valueOf(cpp.getA_name()))) {
				resultMap.get(String.valueOf(cpp.getA_name())).add(cpp);
			} else {
				List<ConsumerPositionPrice> cpps = new ArrayList<>();
				cpps.add(cpp);
				resultMap.put(String.valueOf(cpp.getA_name()), cpps);
			}
		});
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		resultMap.entrySet().forEach((entry -> {
			Map<String, Object> map = new HashMap<>();
			map.put("app_name", entry.getKey());
			map.put("platform", entry.getValue().get(0).getPlatform());
			map.put("adp_list", entry.getValue());
			res.add(map);
		}));
		return res;
	}

	private void build(List<ConsumerPositionPrice> list) {
		list.forEach(cpp -> {
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(cpp.getPid());
			cpp.setP_name(adPosition.getName());
			Apps apps = appsMapper.selectByPrimaryKey(adPosition.getAppId());
			cpp.setAid(apps.getId());
			cpp.setA_name(apps.getAppName());
			cpp.setPlatform(adDicService.getDic(apps.getPlatform()).getDicValue());
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void set(Map<String, Object> params) {
		int cid = (int) params.get("cid");
		ConsumerPositionPriceExample consumerPositionPriceExample = new ConsumerPositionPriceExample();
		consumerPositionPriceExample.createCriteria().andCidEqualTo(cid);
		consumerPositionPriceMapper.deleteByExample(consumerPositionPriceExample);

		List<Map<String, Object>> apps = (List<Map<String, Object>>) params.get("apps");
		apps.forEach(app -> {
			List<Map<String, Object>> adps = (List<Map<String, Object>>) app.get("adp_list");
			adps.forEach(adp -> {
				if (adp.get("price_double") == null
						|| Double.parseDouble(String.valueOf(adp.get("price_double"))) <= Constants.INTEGER_0) {
					throw new ServiceException("请输入正确的价格 .");
				}
				String[] num = String.valueOf(adp.get("price_double")).split(Constants.SIGN_COMMA);
				if (num.length == Constants.INTEGER_2 && num[1].length() > Constants.INTEGER_2) {
					throw new ServiceException("价格至多为两位小数 .");
				}
				ConsumerPositionPrice consumerPositionPrice = new ConsumerPositionPrice();
				consumerPositionPrice.setCid(cid);
				consumerPositionPrice.setPid((int) adp.get("pid"));
				consumerPositionPrice
						.setPrice((int) (Double.parseDouble(String.valueOf(adp.get("price_double"))) * 100));

				consumerPositionPriceMapper.insert(consumerPositionPrice);

			});
		});
		int count = sysCrontabService.countCrontab(cid, Constants.OBJ_APP);
		if (count > 0) {
			sysCrontabService.addSysCrontab(cid, Constants.OBJ_FLOW_CONSUMER, Constants.OP_UPDATE);
		} else {
			sysCrontabService.addSysCrontab(cid, Constants.OBJ_FLOW_CONSUMER, Constants.OP_ADD);
		}
	}
}
