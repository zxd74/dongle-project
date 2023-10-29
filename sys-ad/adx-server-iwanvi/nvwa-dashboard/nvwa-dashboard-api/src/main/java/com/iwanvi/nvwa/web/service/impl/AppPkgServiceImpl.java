package com.iwanvi.nvwa.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AppPkgBlacklistMapper;
import com.iwanvi.nvwa.dao.AppPkgMapper;
import com.iwanvi.nvwa.dao.model.AppPkg;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklist;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklistExample;
import com.iwanvi.nvwa.dao.model.AppPkgExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppPkgService;
import com.iwanvi.nvwa.web.util.ExceptionUtils;
import com.iwanvi.nvwa.web.vo.AppPkgQuertReq;

@Service
public class AppPkgServiceImpl implements AppPkgService {
	@Autowired
	private AppPkgMapper appPkgMapper;
	@Autowired
	private AppPkgBlacklistMapper appPkgBlacklistMapper;

	@Override
	public void insert(AppPkg record) {
		if (StringUtils.isBlank(record.getAppName()) || StringUtils.isBlank(record.getPkgName())) {
			throw new ServiceException("应用名或者包名不能为空");
		}

		AppPkgExample example = AppPkgExample.newAndCreateCriteria().andAppNameEqualTo(record.getAppName()).example();
		example.or(AppPkgExample.newAndCreateCriteria().andPkgNameEqualTo(record.getPkgName()));

		AppPkg appPkg = appPkgMapper.selectOneByExample(example);
		if (appPkg != null) {
			if (appPkg.getIsDeleted()) {
				appPkg.setIsDeleted(false);
				appPkg.setBlanklist(record.getBlanklist());
				appPkgMapper.updateByPrimaryKeySelective(appPkg);
				insertOrUpdateAppPkgBlacklist(record, appPkg);
			} else {
				if (appPkg.getAppName().equals(record.getAppName()))
					ExceptionUtils.throwException("应用名称已经存在");
				if (appPkg.getPkgName().equals(record.getPkgName()))
					ExceptionUtils.throwException("应用包名已经存在");
			}
		} else {
			appPkgMapper.insertSelective(record);
			if (record.getBlanklist()) {
				AppPkgBlacklist appPkgBlanklist = new AppPkgBlacklist();
				appPkgBlanklist.setAppPkgId(record.getId());
				appPkgBlanklist.setName(record.getPkgName());
				appPkgBlanklist.setAppName(record.getAppName());

				appPkgBlacklistMapper.insertSelective(appPkgBlanklist);
			}
		}
	}

	private void insertOrUpdateAppPkgBlacklist(AppPkg record, AppPkg appPkg) {
		// update
		AppPkgBlacklistExample example = AppPkgBlacklistExample.newAndCreateCriteria()
				.andAppPkgIdEqualTo(appPkg.getId()).example();
		AppPkgBlacklist appPkgBlacklist = appPkgBlacklistMapper.selectOneByExample(example);

		if (record.getBlanklist()) {
			if (appPkgBlacklist == null) {
				appPkgBlacklistMapper.insertSelective(AppPkgBlacklist.builder().appPkgId(appPkg.getId())
						.name(record.getPkgName()).appName(record.getAppName()).build());
			} else {
				if (appPkgBlacklist.getIsDeleted()) {
					appPkgBlacklistMapper.updateByExampleSelective(AppPkgBlacklist.builder().isDeleted(false)
							.name(record.getPkgName()).appName(record.getAppName()).build(), example);
				}
			}
		} else {
			if (appPkgBlacklist != null && !appPkgBlacklist.getIsDeleted())
				appPkgBlacklistMapper.updateByExampleSelective(AppPkgBlacklist.builder().isDeleted(true).build(),
						example);
		}
	}

	@Override
	public void delete(Integer id) {
		AppPkg appPkg = AppPkg.builder().id(id).isDeleted(true).build();
		appPkgMapper.updateByPrimaryKeySelective(appPkg);

		// 软删除应用包黑名单相关数据
		AppPkgBlacklistExample example = AppPkgBlacklistExample.newAndCreateCriteria().andAppPkgIdEqualTo(id).example();
		AppPkgBlacklist appPkgBlacklist = AppPkgBlacklist.builder().isDeleted(true).build();

		appPkgBlacklistMapper.updateByExampleSelective(appPkgBlacklist, example);
	}

	@Override
	public void update(AppPkg record) {
		AppPkgExample example = AppPkgExample.newAndCreateCriteria().andAppNameEqualTo(record.getAppName())
				.andIdNotEqualTo(record.getId()).example();
		example.or(AppPkgExample.newAndCreateCriteria().andPkgNameEqualTo(record.getPkgName())
				.andIdNotEqualTo(record.getId()));

		AppPkg appPkg = appPkgMapper.selectOneByExample(example);
		if (appPkg == null) {
			appPkgMapper.updateByPrimaryKeySelective(record);
			insertOrUpdateAppPkgBlacklist(record, record);
		} else {
			if (appPkg.getAppName().equals(record.getAppName()))
				ExceptionUtils.throwException("应用名称已经存在");
			if (appPkg.getPkgName().equals(record.getPkgName()))
				ExceptionUtils.throwException("应用包名已经存在");
		}
	}

	@Override
	public List<AppPkg> selectByExample(String name) {
		AppPkgExample example = new AppPkgExample();

		AppPkgExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andAppNameLike("%" + name + "%");
			example.or(example.createCriteria().andPkgNameLike("%" + name + "%"));
		}

		return appPkgMapper.selectByExample(example);
	}

	@Override
	public AppPkg loadById(Integer id) {
		return appPkgMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<AppPkg> selectPage(AppPkgQuertReq queryReq) {
		AppPkgExample example = queryReq.toExample();

		int total = (int) appPkgMapper.countByExample(example);
		List<AppPkg> dataList = appPkgMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), dataList);

	}

	@Override
	public AppPkg addByIdBlack(Integer id) {
		return appPkgMapper.selectByPrimaryKey(id);

	}

	@Override
	public boolean appName(String name) {
		AppPkgExample appNames = new AppPkgExample();
		appNames.createCriteria().andAppNameEqualTo(name);
		long count = appPkgMapper.countByExample(appNames);
		return count > 0;

	}

	@Override
	public boolean pkgName(String name) {
		AppPkgExample appNames = new AppPkgExample();
		appNames.createCriteria().andPkgNameEqualTo(name);
		long count = appPkgMapper.countByExample(appNames);
		return count > 0;
	}

}
