package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.WarningSettingMapper;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.WarningSettingExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.dao.model.support.WarningSettingVo;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.QuotaFlowService;
import com.iwanvi.nvwa.web.service.WarningSettingService;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class WarningSettingServiceImpl implements WarningSettingService {

	@Autowired
	private WarningSettingMapper warningSettingMapper;

	@Autowired
	private AppChannelMapper appChannelMapper;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private QuotaFlowService quotaFlowService;

	@Autowired
	private RedisDao redisDao;

	@Override
	@Transactional
	public void update(WarningSetting warningSetting) {
		buildWarningSetting(warningSetting);
		checkUpEmailFormat(warningSetting.getEmail());
		if (warningSetting.getId() == null) {
			checkExist(warningSetting);
			warningSettingMapper.insertSelective(warningSetting);
			writeRedis();
			return;
		}
		warningSettingMapper.updateByPrimaryKeySelective(warningSetting);
		writeRedis();
	}

	private void checkUpEmailFormat(String email) {
		if (StringUtils.isBlank(email)) {
			throw new ServiceException("请输入电子邮箱.");
		}
		if (email.endsWith(Constants.SIGN_COMMA)) {
			throw new ServiceException("请输入正确的电子邮箱.");
		}
		String[] emails = email.split(Constants.SIGN_COMMA);
		for (int i = 0; i < emails.length; i++) {
			if (StringUtils.isBlank(emails[i]) || !MatcherUtils.isEmail(emails[i])) {
				throw new ServiceException("第" + (i + 1) + "个邮箱格式有误，请重新输入 。");
			}
		}
	}

	private void writeRedis() {
		WarningSettingExample example = new WarningSettingExample();
		example.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
		List<WarningSetting> list = warningSettingMapper.selectByExample(example);
		List<WarningSettingVo> res = buildList(list);
		redisDao.set(WebConstants.KEY_WARTN_SETTINGS, JsonUtils.TO_JSON(res));
	}

	private List<WarningSettingVo> buildList(List<WarningSetting> list) {
		List<WarningSettingVo> res = new ArrayList<WarningSettingVo>();
		list.forEach(ws -> {
			WarningSettingVo settingVo = new WarningSettingVo();
			settingVo.setAppId(appsMapper.selectByPrimaryKey(ws.getAppId()).getAppId());
			settingVo.setChannelId(appChannelMapper.selectByPrimaryKey(ws.getChannelId()).getCname());
			settingVo.setEmail(ws.getEmail());
			settingVo.setClkHb(ws.getClkHb() / 100.0);
			settingVo.setClkTb(ws.getClkTb() / 100.0);
			settingVo.setClkTj(ws.getClkTj());
			settingVo.setPvHb(ws.getPvHb() / 100.0);
			settingVo.setPvTb(ws.getPvTb() / 100.0);
			settingVo.setPvTj(ws.getPvTj());
			res.add(settingVo);
		});
		return res;
	}

	private void buildWarningSetting(WarningSetting warningSetting) {
		if (warningSetting.getPvTb() == Constants.INTEGER_0 && warningSetting.getPvHb() == Constants.INTEGER_0) {
			warningSetting.setPvTj(null);
		} else {
			if (warningSetting.getPvTj() == null) {
				throw new ServiceException("请选择PV环比同比预警条件.");
			}
		}
		if (warningSetting.getClkTb() == Constants.INTEGER_0 && warningSetting.getClkHb() == Constants.INTEGER_0) {
			warningSetting.setClkTj(null);
		} else {
			if (warningSetting.getClkTj() == null) {
				throw new ServiceException("请选择点击环比同比预警条件.");
			}
		}
		if ((warningSetting.getPvTb() == Constants.INTEGER_0 && warningSetting.getPvHb() != Constants.INTEGER_0)
				|| (warningSetting.getPvTb() != Constants.INTEGER_0
						&& warningSetting.getPvHb() == Constants.INTEGER_0)) {
			warningSetting.setPvTj(Constants.WARN_SETTING_ONE);
		}
		if ((warningSetting.getClkTb() == Constants.INTEGER_0 && warningSetting.getClkHb() != Constants.INTEGER_0)
				|| (warningSetting.getClkTb() != Constants.INTEGER_0
						&& warningSetting.getClkHb() == Constants.INTEGER_0)) {
			warningSetting.setClkTj(Constants.WARN_SETTING_ONE);
		}
	}

	private void checkExist(WarningSetting warningSetting) {
		WarningSettingExample warningSettingExample = new WarningSettingExample();
		warningSettingExample.createCriteria().andChannelIdEqualTo(warningSetting.getChannelId())
				.andAppIdEqualTo(warningSetting.getAppId());
		List<WarningSetting> list = warningSettingMapper.selectByExample(warningSettingExample);
		if (!CollectionUtils.isEmpty(list)) {
			throw new ServiceException("该组合已存在，请重新选择 .");
		}
	}

	@Override
	@Transactional
	public void updateStatus(Integer id, Integer status) {
		if (status == Constants.STATE_VALID) {
			WarningSetting warningSetting = warningSettingMapper.selectByPrimaryKey(id);
			Apps apps = appsMapper.selectByPrimaryKey(warningSetting.getAppId());
			if (apps.getStatus() == Constants.STATE_INVALID || apps.getAppStatus() == Constants.STATE_INVALID) {
				throw new ServiceException("该App已删除或状态关闭  .  ");
			}
			AppChannel appChannel = appChannelMapper.selectByPrimaryKey(warningSetting.getChannelId());
			if (appChannel.getIsDeleted()) {
				throw new ServiceException("该渠道已删除  .  ");
			}
		}
		WarningSetting setting = new WarningSetting();
		setting.setId(id);
		setting.setStatus(status);
		warningSettingMapper.updateByPrimaryKeySelective(setting);
		writeRedis();
	}

	private void build(WarningSetting setting) {
		int clk_one = Constants.INTEGER_0;
		int clk_seven = Constants.INTEGER_0;
		int pv_one = Constants.INTEGER_0;
		int pv_seven = Constants.INTEGER_0;
		Integer yesterday = Integer.parseInt(DateUtils.getSpecifyTime(Constants.INTEGER_1));
		Integer last_week = Integer.parseInt(DateUtils.getSpecifyTime(Constants.INTEGER_7));
		Apps app = appsMapper.selectByPrimaryKey(setting.getAppId());
		AppChannel appChannel = appChannelMapper.selectByPrimaryKey(setting.getChannelId());
		String aid = app.getAppId();
		String cid = appChannel.getName();
		List<QuotaFlow> yesterday_list = quotaFlowService.sumWithDate(null, aid, null, cid, null, null, yesterday, yesterday);
		if (!CollectionUtils.isEmpty(yesterday_list)) {
			QuotaFlow quotaFlow = yesterday_list.get(Constants.INTEGER_0);
			clk_one = quotaFlow.getClk();
			pv_one = quotaFlow.getExp();
		}
		List<QuotaFlow> last_week_list = quotaFlowService.sumWithDate(null, aid, null, cid, null, null, last_week, last_week);
		if (!CollectionUtils.isEmpty(last_week_list)) {
			QuotaFlow quotaFlow = last_week_list.get(Constants.INTEGER_0);
			clk_seven = quotaFlow.getClk();
			pv_seven = quotaFlow.getExp();
		}
		setting.setClk_one(clk_one);
		setting.setClk_seven(clk_seven);
		setting.setPv_one(pv_one);
		setting.setPv_seven(pv_seven);
	}

	@Override
	public WarningSetting get(Integer id) {
		WarningSetting warningSetting = warningSettingMapper.selectByPrimaryKey(id);
		AppChannel appChannel = appChannelMapper.selectByPrimaryKey(warningSetting.getChannelId());
		Apps apps = appsMapper.selectByPrimaryKey(warningSetting.getAppId());
		warningSetting.setApp_name(apps.getAppName());
		warningSetting.setChannel_name(appChannel.getCname());
		build(warningSetting);
		return warningSetting;
	}

	@Override
	public Page<WarningSetting> getPage(WarningSetting warningSetting) {
		Page<WarningSetting> page = null;
		WarningSettingExample example = buildExample(warningSetting);
		int count = (int) warningSettingMapper.countByExample(example);
		if (warningSetting.getCurrentPage() != null && warningSetting.getPageSize() != null) {
			page = new Page<WarningSetting>(count, warningSetting.getCurrentPage(), warningSetting.getPageSize());
		} else {
			page = new Page<WarningSetting>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		List<WarningSetting> list = warningSettingMapper.selectByExample(example);
		fillWarningSetting(list);
		page.bindData(list);
		return page;
	}

	private void fillWarningSetting(List<WarningSetting> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		list.forEach(ws -> {
			ws.setChannel_name(appChannelMapper.selectByPrimaryKey(ws.getChannelId()).getCname());
			ws.setApp_name(appsMapper.selectByPrimaryKey(ws.getAppId()).getAppName());
			ws.setClk_hb_name(StringUtils.concat(ws.getClkHb(), Constants.SIGN_PERCENT));
			ws.setClk_tb_name(StringUtils.concat(ws.getClkTb(), Constants.SIGN_PERCENT));
			ws.setPv_hb_name(StringUtils.concat(ws.getPvHb(), Constants.SIGN_PERCENT));
			ws.setPv_tb_name(StringUtils.concat(ws.getPvTb(), Constants.SIGN_PERCENT));
			if (ws.getPvTj() == null) {
				ws.setPv_tj_name(null);
			} else {
				ws.setPv_tj_name(adDicService.getDic(ws.getPvTj()).getDicValue());
			}
			if (ws.getClkTj() == null) {
				ws.setClk_tj_name(null);
			} else {
				ws.setClk_tj_name(adDicService.getDic(ws.getClkTj()).getDicValue());
			}
			if (ws.getClkHb() == Constants.INTEGER_0) {
				ws.setClk_hb_name(StringUtils.EMPTY);
			}
			if (ws.getClkTb() == Constants.INTEGER_0) {
				ws.setClk_tb_name(StringUtils.EMPTY);
			}
			if (ws.getPvHb() == Constants.INTEGER_0) {
				ws.setPv_hb_name(StringUtils.EMPTY);
			}
			if (ws.getPvTb() == Constants.INTEGER_0) {
				ws.setPv_tb_name(StringUtils.EMPTY);
			}
		});
	}

	private WarningSettingExample buildExample(WarningSetting warningSetting) {
		WarningSettingExample example = new WarningSettingExample();
		if (warningSetting == null)
			return example;
		WarningSettingExample.Criteria criteria = example.createCriteria();
		if (warningSetting.getChannelId() != null) {
			criteria.andChannelIdEqualTo(warningSetting.getChannelId());
		}
		if (warningSetting.getAppId() != null) {
			criteria.andAppIdEqualTo(warningSetting.getAppId());
		}
		if (warningSetting.getStatus() != null) {
			criteria.andStatusEqualTo(warningSetting.getStatus());
		}
		example.setOrderByClause("status desc,id desc");
		return example;
	}

	@Override
	public WarningSetting initPage(Integer aid, Integer cid) {
		WarningSetting warningSetting = new WarningSetting();
		warningSetting.setAppId(aid);
		warningSetting.setChannelId(cid);
		build(warningSetting);
		return warningSetting;
	}
}
