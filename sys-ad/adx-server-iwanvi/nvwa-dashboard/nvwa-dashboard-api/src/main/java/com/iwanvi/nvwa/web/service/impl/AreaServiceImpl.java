package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AreaGroupMapper;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.AreaExample.Criteria;
import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.dao.model.AreaGroupExample;
import com.iwanvi.nvwa.web.service.AreaService;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

	// 排除个别数据
	private List<Integer> excludeArea = Lists.newArrayList(1036, 1037,1038,1039,1110,1111,1220,1272,1273,1274,1275,1380);
	// 直辖市
	private List<Integer> specialCity = Lists.newArrayList(1002, 1003, 1010, 1023);

	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private AreaGroupMapper areaGroupMapper;

	@Override
	public List<Area> selectForList(Area area) {
		List<Area> list = Lists.newArrayList();
		List<AreaGroup> groupList = Lists.newArrayList();

		AreaExample example = new AreaExample();
		AreaExample.Criteria criteria = example.createCriteria();

		AreaExample specialExample = new AreaExample();
		AreaExample.Criteria specialCriteria = specialExample.createCriteria();
		if (StringUtils.isNotBlank(area.getAreaName())) {
			criteria.andAreaNameLike("%" + area.getAreaName() + "%");
			specialCriteria.andAreaNameLike("%" + area.getAreaName() + "%");
		}

		AreaGroupExample groupExample = new AreaGroupExample();
		AreaGroupExample.Criteria gropupCriteria = groupExample.createCriteria();

		List<Area> respList = Lists.newArrayList();
		if (Constants.INTEGER_1.equals(area.getIsCityLevel())) {
			// 页面定位属性包含的城市
			criteria.andIsCityLevelEqualTo(Constants.INTEGER_1);
			list = areaMapper.selectByExample(example);
			gropupCriteria.andGroupTypeEqualTo(Constants.INTEGER_1);
			groupList = areaGroupMapper.selectByExampleWithBLOBs(groupExample);
		} else {
			criteria.andTypeNotEqualTo(Constants.INTEGER_3).andIdNotIn(excludeArea);
			list = areaMapper.selectByExample(example);
			for (Area area1 : list) {
				// 如果搜的是省，列出所包含的城市
				if (area1.getType().equals(Constants.INTEGER_1) && !specialCity.contains(area1.getId())) {
					AreaExample subExample = new AreaExample();
					subExample.createCriteria().andSuperiorAreaEqualTo(area1.getId()).andIdNotIn(excludeArea);
					respList.addAll(areaMapper.selectByExample(subExample));
				} else {
					List<Integer> areaIds = Lists.transform(respList,Area::getId);
					if (!areaIds.contains(area1.getId())) {
						respList.add(area1);
					}
				}
			}
			gropupCriteria.andGroupTypeEqualTo(Constants.INTEGER_2);
			groupList = areaGroupMapper.selectByExampleWithBLOBs(groupExample);
		}
		respList = checkArea(respList, groupList);
		return respList;

	}

	/**
	 * 判断城市是否已经分组
	 */
	private List<Area> checkArea(List<Area> list, List<AreaGroup> groupList) {
		for (Area tempArea : list) {
			for (AreaGroup areaGroup : groupList) {
				if (StringUtils.isNotBlank(areaGroup.getAreaIds())) {
					List<String> areaIds = Arrays.asList(areaGroup.getAreaIds().split(Constants.SIGN_COMMA));
					if (areaIds.contains(tempArea.getId().toString())) {
						// tempArea.setAreaName(tempArea.getAreaName() +
						// Constants.SIGN_MINUS + areaGroup.getGroupName());
						tempArea.setIsDistribution(Constants.INTEGER_1);
						break;
					}
				}
			}
		}
		Iterator<Area> iter = list.iterator();
		while (iter.hasNext()) {
			if (Constants.INTEGER_1.equals(iter.next().getIsDistribution())) {
				iter.remove();
			}
		}
		return list;
	}

	@Override
	public List<Area> selectListByNameAndType(Integer findType, String areaName) {
		AreaExample areaExample = new AreaExample();
		Criteria criteria = areaExample.createCriteria();
		List<Area> areas = Lists.newArrayList();
		criteria.andStatusEqualTo(Constants.STATE_VALID).andAreaNameLike(
				StringUtils.concat(Constants.SIGN_PERCENT, StringUtils.trim(areaName), Constants.SIGN_PERCENT));
		if (Constants.AREA_TYPE_PROVINCE.equals(findType)) {
			criteria.andTypeEqualTo(Constants.AREA_TYPE_PROVINCE);
			List<Area> provinceAreas = areaMapper.selectByExample(areaExample);
			if (provinceAreas != null && provinceAreas.size() > 0) {
				for (Area area : provinceAreas) {
					if (area.getAreaName().contains("市")) {
						areas.add(area);
					}
				}
				if (areas.size() > 0) {
					return areas;
				}
				List<Integer> provinceId = Lists.transform(provinceAreas, (Area oneArea) -> oneArea.getId());
				areaExample.clear();
				areaExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andSuperiorAreaIn(provinceId)
						.andTypeEqualTo(Constants.AREA_TYPE_CITY);
				areas = areaMapper.selectByExample(areaExample);
			}
		} else if (Constants.AREA_TYPE_CITY.equals(findType)) {
			criteria.andTypeEqualTo(Constants.AREA_TYPE_CITY).andIdNotIn(excludeArea);
			areas = areaMapper.selectByExample(areaExample);
		}
		return areas;
	}

	@Override
	public List<Integer> getAllAreaCityId() {
		List<Integer> areaAllId = Lists.newArrayList();
		AreaExample areaExample = new AreaExample();
		areaExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andTypeEqualTo(Constants.AREA_TYPE_CITY);
		List<Area> areas = areaMapper.selectByExample(areaExample);
		List<Integer> areaIds = Lists.transform(areas, (Area area) -> area.getId());
		areaAllId.addAll(areaIds);
		areaAllId.removeAll(excludeArea);
		areaAllId.addAll(specialCity);
		return areaAllId;
	}

	@Override
	public List<Area> getDicByIds(List<Integer> ids) {
		AreaExample areaExample = new AreaExample();
		areaExample.createCriteria().andIdIn(ids).andStatusEqualTo(Constants.STATE_VALID);
		return areaMapper.selectByExample(areaExample);
	}

}
