package com.iwanvi.nvwa.web.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ServiceException;
import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AppChannelMapper;
import com.iwanvi.nvwa.dao.FlowControlMapper;
import com.iwanvi.nvwa.dao.WarningSettingMapper;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.FlowControl;
import com.iwanvi.nvwa.dao.model.FlowControlExample;
import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.WarningSettingExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppChannelService;
import com.iwanvi.nvwa.web.service.FlowControlServicce;
import com.iwanvi.nvwa.web.service.WarningSettingService;
import com.iwanvi.nvwa.web.vo.AppChannelQueryReq;

@Service
public class AppChannelServiceImpl implements AppChannelService {
	@Autowired
	private AppChannelMapper appChannelMapper;

	@Autowired
	private FlowControlMapper flowControlMapper;

	@Autowired
	private FlowControlServicce flowControlService;

	@Autowired
	private WarningSettingMapper warningSettingMapper;

	@Autowired
	private WarningSettingService warningSettingService;

	@Override
	public void insert(AppChannel record) {
		AppChannelExample example = AppChannelExample.newAndCreateCriteria().andNameEqualTo(record.getName()).example();
		AppChannel channel = appChannelMapper.selectOneByExample(example);

		if (channel != null) {
			if (channel.getIsDeleted()) {
				appChannelMapper.updateByPrimaryKeySelective(
						AppChannel.builder().id(channel.getId()).cname(record.getCname()).isDeleted(false).build());
			} else {
				throw new ServiceException(String.format("渠道号%s已经存在", channel.getName()));
			}
		} else {
			appChannelMapper.insertSelective(record);
		}

	}

	@Override
	@Transactional
	public void delete(Integer id) {
		AppChannel appChannel = AppChannel.builder().id(id).isDeleted(true).build();
		appChannelMapper.updateByPrimaryKeySelective(appChannel);
		updateFlowControlStatus(id); // 删除后将流量控制状态关闭
		updateWarnSettingStatus(id); // 删除后预警设置状态关闭
	}

	private void updateWarnSettingStatus(Integer id) {
		WarningSettingExample warningSettingExample = new WarningSettingExample();
		warningSettingExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andChannelIdEqualTo(id);
		List<WarningSetting> list = warningSettingMapper.selectByExample(warningSettingExample);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			warningSettingService.updateStatus(Constants.STATE_INVALID, action.getId());
		});
	}

	private void updateFlowControlStatus(Integer id) {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andChannelIdEqualTo(id);
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			flowControlService.flowSwitch(Constants.STATE_INVALID, action.getId());
		});
	}

	@Override
	public List<AppChannel> select(String name) {
		AppChannelExample example = new AppChannelExample();
		AppChannelExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
			example.or(example.createCriteria().andCnameLike("%" + name + "%"));
		}
		return appChannelMapper.selectByExample(example);
	}

	@Override
	public Page<AppChannel> selectPage(AppChannelQueryReq queryReq) {
		AppChannelExample example = queryReq.toExample();

		String parentName = queryReq.getParentName();
		Integer type = queryReq.getType();

		List<Integer> channelIdsForLevel1 = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(parentName)) {
			List<AppChannel> parentChannels = appChannelMapper.selectByExample(
					AppChannelExample.newAndCreateCriteria().andNameLike("%" + parentName + "%").example());
			if (parentChannels == null || parentChannels.isEmpty()) {
				return Page.create(0, 1, new ArrayList<AppChannel>());
			}
			// List<Integer> parentChannelIds = new ArrayList<Integer>();
			for (AppChannel parentChannel : parentChannels) {
				channelIdsForLevel1.add(parentChannel.getId());
			}
			example.getOredCriteria().get(0).andParentIdIn(channelIdsForLevel1);
		}
		if (type != null && type != 0) {
			if (type == 1)
				example.getOredCriteria().get(0).andParentIdIsNull();
			if (type == 2)
				example.getOredCriteria().get(0).andParentIdIsNotNull();
		}

		int total = (int) appChannelMapper.countByExample(example);
		List<AppChannel> dataList = appChannelMapper.selectByExample(example);
		for (AppChannel channel : dataList) {
			Integer parentId = channel.getParentId();
			if (parentId != null) {
				AppChannel c = appChannelMapper.selectByPrimaryKey(parentId);
				if (c != null)
					channel.setParentName(c.getName());
			}
		}
		return Page.create(total, queryReq.getPageSize(), dataList);

	}

	@Override
	public List<AppChannel> getAllByFc() {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria()/* .andStatusEqualTo(Constants.STATE_VALID) */;
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list))
			return null;
		List<Integer> cids = FluentIterable.from(list).transform((FlowControl flowControl) -> {
			return flowControl.getChannelId();
		}).toList();
		cids = cids.stream().distinct().collect(Collectors.toList());
		AppChannelExample appChannelExample = new AppChannelExample();
		appChannelExample.createCriteria().andIdIn(cids).andIsDeletedEqualTo(false);
		List<AppChannel> result = appChannelMapper.selectByExample(appChannelExample);
		return result;
	}

	@Override
	public boolean AppChanneNameIsExist(String name) {
		AppChannelExample appChannel = new AppChannelExample();
		AppChannelExample.Criteria criter = appChannel.createCriteria();
		criter.andNameEqualTo(name);
		appChannel.or(appChannel.createCriteria().andCnameEqualTo(name));
		long count = appChannelMapper.countByExample(appChannel);
		return count > 0;
	}

	@Override
	public List<AppChannel> selectOneLevel() {
		AppChannelExample appExample = new AppChannelExample();
		AppChannelExample.Criteria criteria = appExample.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		criteria.andParentIdIsNull();
		return appChannelMapper.selectByExample(appExample);
	}

	@Override
	public List<AppChannel> selectTwoLevel() {
		AppChannelExample appExample = new AppChannelExample();
		AppChannelExample.Criteria criteria = appExample.createCriteria();
		criteria.andIsDeletedNotEqualTo(true).andParentIdIsNotNull();

		/*
		 * List<Integer> list = new ArrayList<Integer>(); List<AppChannel> listApp =
		 * selectOneLevel(); if (list.isEmpty()) { return Collections.emptyList(); } for
		 * (AppChannel app : listApp) { list.add(app.getId()); }
		 * appChannel.andParentIdIn(list);
		 */
		return appChannelMapper.selectByExample(appExample);

	}

	@Override
	public List<AppChannel> selectAppChannel() {
		return appChannelMapper.selectAppChannel();
	}

	@Override
	public boolean batchImport(String fileName, MultipartFile file) throws Exception {

		boolean notNull = false;
		// List<AppChannel> appList = new ArrayList<AppChannel>();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new ServiceException("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}

		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet != null) {
			notNull = true;
		}

		List<AppChannel> appChannelsForLevel1 = new ArrayList<AppChannel>();
		List<AppChannel> appChannelsForLevel2 = new ArrayList<AppChannel>();

		AppChannel appChannel;
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			Iterator<Cell> cellList = row.cellIterator();
			while (cellList.hasNext()) {
				cellList.next().setCellType(CellType.STRING);
			}
			appChannel = new AppChannel();

			// CellType type = row.getCell(0).getCellType();
			// row.getCell(0).setCellType(CellType.STRING);
			String name = row.getCell(0).getStringCellValue();
			String cname = row.getCell(1).getStringCellValue();
			if (StringUtils.isBlank(name) || StringUtils.isBlank(cname)) {
				continue;
			}

			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(name);
			if (!isNum.matches()) {
				continue;
			}

			appChannel.setName(name);
			appChannel.setCname(cname);

			String parentId = null;
			Cell cell = row.getCell(2);
			if (cell != null) {
				parentId = cell.getStringCellValue();
			}
			// String parentId = row.getCell(2).getStringCellValue();
			if (StringUtils.isBlank(parentId)) {
				appChannelsForLevel1.add(appChannel);
				// appChannelMapper.insertSelective(app);
			} else {
				appChannel.setParentName(parentId);
				appChannelsForLevel2.add(appChannel);
			}

			// appList.add(appChannel);
		}

		for (AppChannel channel : appChannelsForLevel1) {
			String name = channel.getName();
			String cname = channel.getCname();
//			int cnt = (int) appChannelMapper
//					.countByExample(AppChannelExample.newAndCreateCriteria().andNameEqualTo(name).andCnameEqualTo(cname).example());

			AppChannelExample appChannels = new AppChannelExample();
			AppChannelExample.Criteria criteria = appChannels.createCriteria();
			criteria.andNameEqualTo(name);
			appChannels.or(appChannels.createCriteria().andCnameEqualTo(cname));
			int cnt = (int) appChannelMapper.countByExample(appChannels);
			if (cnt == 0) {
				appChannelMapper.insertSelective(channel);
				System.out.println(" 插入 " + channel);
			}
		}

		for (AppChannel channel : appChannelsForLevel2) {
			String name = channel.getName();
			int cnt = appChannelMapper.selectByName(name, channel.getCname());
			if (cnt == 0) {
				String parentName = channel.getParentName();
				AppChannel parentChannel = appChannelMapper.selectOneByExample(
						AppChannelExample.newAndCreateCriteria().andNameEqualTo(parentName).example());
				if (parentChannel == null) {
					continue;
				}
				channel.setParentId(parentChannel.getId());
				appChannelMapper.insertSelective(channel);
				System.out.println(" 插入 " + channel);
			}
		}
		return notNull;

	}

	@Override
	public List<AppChannel> selectByLevel(List<String> level1ChannelNames, Integer level, String name) {
		AppChannelExample example = new AppChannelExample();
		AppChannelExample.Criteria criteria = example.createCriteria();
		AppChannelExample.Criteria orCriteria = null;
		// example.createCriteria();

		if (StringUtils.isNotBlank(name)) {
			orCriteria = example.createCriteria();
			criteria.andNameLike("%" + name + "%");
			orCriteria.andCnameLike("%" + name + "%");
			// example.or(AppChannelExample.newAndCreateCriteria().andCnameLike("%" + name +
			// "%"));
		}
		if (level == 1) {
			criteria.andParentIdIsNull();
			if (orCriteria != null)
				orCriteria.andParentIdIsNull();
		} else {
			criteria.andParentIdIsNotNull();
			if (orCriteria != null)
				orCriteria.andParentIdIsNotNull();
		}

		if (level1ChannelNames != null && !level1ChannelNames.isEmpty()) {
			List<AppChannel> level1Channels = appChannelMapper.selectByExample(AppChannelExample.newAndCreateCriteria()
					.andNameIn(level1ChannelNames).example());
			List<Integer> level1ChannelIdList = new ArrayList<Integer>();
			for (AppChannel level1Channel : level1Channels) {
				level1ChannelIdList.add(level1Channel.getId());
			}
			if (!level1ChannelIdList.isEmpty()) {
				criteria.andParentIdIn(level1ChannelIdList);
				if (orCriteria != null)
					orCriteria.andParentIdIn(level1ChannelIdList);
			}
		}

		if (orCriteria != null)
			example.or(orCriteria);
		example.page(0, 20);
		return appChannelMapper.selectByExample(example);
	}

}
