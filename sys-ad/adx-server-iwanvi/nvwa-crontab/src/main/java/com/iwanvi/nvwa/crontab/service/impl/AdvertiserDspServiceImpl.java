package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.crontab.service.AdvertiserDspService;
import com.iwanvi.nvwa.crontab.service.EntityDspService;
import com.iwanvi.nvwa.dao.DspCreativeMapper;
import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.DspCreativeExample;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class AdvertiserDspServiceImpl implements AdvertiserDspService {

	@Autowired
	private DspCreativeMapper dspCreativeMapper;

	@Autowired
	private EntityDspService entityDspService;

	@Override
	public void buildPosSend(Integer objectId, OpType opType) {
		DspCreativeExample example = new DspCreativeExample();
		example.createCriteria().andAdvertiserIdEqualTo(objectId);
		List<DspCreative> list = dspCreativeMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			entityDspService.buildPosSend(action.getId(), opType);
		});
	}
}
