package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.DictionaryMapper;
import com.iwanvi.nvwa.dao.SdkAdCarouselMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.dao.model.DictionaryExample;
import com.iwanvi.nvwa.dao.model.SdkAdCarousel;
import com.iwanvi.nvwa.dao.model.SdkAdCarouselExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.SdkAdCarouselService;
import com.iwanvi.nvwa.web.util.WebConstants;

@Service
public class SdkAdCarouselServiceImpl implements SdkAdCarouselService {

	@Autowired
	private SdkAdCarouselMapper sdkAdCarouselMapper;

	@Autowired
	private AdDicService adDicService;

	@Autowired
	private AppsMapper appsMapper;

	@Autowired
	private AdPositionMapper adPositionMapper;

	@Autowired
	private DictionaryMapper dictionaryMapper;

	@Autowired
	private RedisDao redisDao;

	@Override
	@Transactional
	public void setDefult(List<SdkAdCarousel> list) {
		for (SdkAdCarousel sdkAdCarousel : list) {
			SdkAdCarouselExample sdkAdCarouselExample = new SdkAdCarouselExample();
			sdkAdCarouselExample.createCriteria().andIsDelEqualTo(Constants.STATE_INVALID)
					.andIsDefaultEqualTo(Constants.STATE_VALID).andAdTypeEqualTo(sdkAdCarousel.getAdType());

			sdkAdCarouselMapper.updateByExampleSelective(sdkAdCarousel, sdkAdCarouselExample);
			writeRedisAll();
		}
	}

	private void writeRedisAll() {
		List<Map<String, Object>> all = sdkAdCarouselMapper.getAll(null);
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		all.forEach(sdk -> {
			/*
			 * int id = (int) sdk.get("id"); if (Constants.DEFULT_IDS.contains(id)) {
			 * return; }
			 */
			String uuid = String.valueOf(sdk.get("appuuid"));
			if (resultMap.containsKey(uuid)) {
				resultMap.get(uuid).add(sdk);
			} else {
				List<Map<String, Object>> sdks = new ArrayList<>();
				sdks.add(sdk);
				resultMap.put(uuid, sdks);
			}
		});
		resultMap.entrySet().forEach(entry -> {
			String key = StringUtils.buildString(WebConstants.KEY_REDIS_AD_CAROUSEL, entry.getKey());
			redisDao.set(key, JsonUtils.TO_JSON(entry.getValue()));
		});
	}

	@SuppressWarnings("serial")
	@Override
	public List<SdkAdCarousel> getDefult() {
		List<SdkAdCarousel> result = new ArrayList<SdkAdCarousel>();

		SdkAdCarouselExample adCarouselExample = new SdkAdCarouselExample();
		adCarouselExample.createCriteria().andIsDelEqualTo(Constants.STATE_INVALID)
				.andIsDefaultEqualTo(Constants.STATE_VALID);
		List<SdkAdCarousel> list = sdkAdCarouselMapper.selectByExample(adCarouselExample);

		// List<Dictionary> adtypes = adDicService.getDicByGroupId(1);
		List<Integer> ids = new ArrayList<Integer>() {
			{
				// add(5); // 横幅
				add(7);// 信息流
				// add(218);// 插屏

			}
		};
		DictionaryExample dictionaryExample = new DictionaryExample();
		dictionaryExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(ids);
		List<Dictionary> adtypes = dictionaryMapper.selectByExample(dictionaryExample);
		for (Dictionary dictionary : adtypes) {
			SdkAdCarousel sdkAdCarousel = new SdkAdCarousel();
			sdkAdCarousel.setAdTypeName(dictionary.getDicValue());
			sdkAdCarousel.setAdType(dictionary.getId());
			sdkAdCarousel.setChangeType(Constants.INTEGER_0);
			if (CollectionUtils.isEmpty(list)) {// 表里没值
				sdkAdCarousel.setDurationValue(Constants.INTEGER_0);
				sdkAdCarousel.setPageValue(Constants.INTEGER_0);
			} else {
				sdkAdCarousel.setDurationValue(list.get(0).getDurationValue());
				sdkAdCarousel.setPageValue(list.get(0).getPageValue());
				sdkAdCarousel.setChangeType(list.get(0).getChangeType());
			}

			SdkAdCarouselExample sdkAdCarouselExample = new SdkAdCarouselExample();
			sdkAdCarouselExample.createCriteria().andIsDelEqualTo(Constants.STATE_INVALID)
					.andIsDefaultEqualTo(Constants.STATE_VALID).andAdTypeEqualTo(dictionary.getId());
			List<SdkAdCarousel> slist = sdkAdCarouselMapper.selectByExample(sdkAdCarouselExample);
			if (!CollectionUtils.isEmpty(slist)) {
				sdkAdCarousel.setChangeType(slist.get(0).getChangeType());
			}
			result.add(sdkAdCarousel);
		}
		return result;
	}

	@Override
	@Transactional
	public void insertOrUpdate(SdkAdCarousel carousel) {// 因为存在默认值 所以添加即根据广告位修改
		// carousel.setIsDel(Constants.STATE_INVALID);
		/*
		 * if (carousel.getChapterNum() == 0 && carousel.getPageNum() == 0) { throw new
		 * ServiceException("插页间隔章数和页数至少选择一个。 "); }
		 */
		/*
		 * if (Constants.AD_STRATEGY_DURATION.equals(carousel.getChangeType())) {
		 * carousel.setDurationValue(carousel.getValue()); carousel.setPageValue(null);
		 * } else if (Constants.AD_STRATEGY_PAGE.equals(carousel.getChangeType())) {
		 * carousel.setPageValue(carousel.getValue()); carousel.setDurationValue(null);
		 * }s
		 */
		if (carousel.getId() != null) {
			sdkAdCarouselMapper.updateByPrimaryKey(carousel);
			writeRedisOne(carousel);
			return;
		}
		/*
		 * Integer adPosition = carousel.getAdPosition(); SdkAdCarouselExample example =
		 * new SdkAdCarouselExample();
		 * example.createCriteria().andAdPositionEqualTo(adPosition);
		 * List<SdkAdCarousel> list = sdkAdCarouselMapper.selectByExample(example); if
		 * (!CollectionUtils.isEmpty(list) && list.size() > Constants.INTEGER_0) {
		 * sdkAdCarouselMapper.updateByExampleSelective(carousel, example);
		 * writeRedisOne(carousel); return; }
		 */
		/*
		 * SdkAdCarouselExample adCarouselExample = new SdkAdCarouselExample();
		 * adCarouselExample.createCriteria().andIsDefaultEqualTo(Constants.STATE_VALID)
		 * .andIsDelEqualTo(Constants.STATE_INVALID).andAdPositionEqualTo(carousel.
		 * getAdPosition()); carousel.setIsDefault(Constants.STATE_INVALID);
		 * sdkAdCarouselMapper.updateByExampleSelective(carousel, adCarouselExample);
		 */
		sdkAdCarouselMapper.insert(carousel);
		writeRedisOne(carousel);
	}

	private void writeRedisOne(SdkAdCarousel carousel) {
		Integer adPosition = carousel.getAdPosition();
		AdPosition position = adPositionMapper.selectByPrimaryKey(adPosition);
		Integer appId = position.getAppId();
		Apps apps = appsMapper.selectByPrimaryKey(appId);
		String uuid = apps.getAppId();
		String key = StringUtils.buildString(WebConstants.KEY_REDIS_AD_CAROUSEL, uuid);
		List<Map<String, Object>> all = sdkAdCarouselMapper.getAll(uuid);
		List<Map<String, Object>> res = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : all) {
			if (!Constants.DEFULT_IDS.contains(map.get("id"))) {
				res.add(map);
			}
		}
		redisDao.set(key, JsonUtils.TO_JSON(res));
	}

	@Override
	public SdkAdCarousel get(Integer id) {
		SdkAdCarousel sdkAdCarousel = sdkAdCarouselMapper.selectByPrimaryKey(id);
		/*
		 * if (Constants.AD_STRATEGY_DURATION.equals(sdkAdCarousel.getChangeType())) {
		 * sdkAdCarousel.setValue(sdkAdCarousel.getDurationValue()); } else if
		 * (Constants.AD_STRATEGY_PAGE.equals(sdkAdCarousel.getChangeType())) {
		 * sdkAdCarousel.setValue(sdkAdCarousel.getPageValue()); }
		 */
		AdPosition adPosition = adPositionMapper.selectByPrimaryKey(sdkAdCarousel.getAdPosition());
		Apps apps = appsMapper.selectByPrimaryKey(adPosition.getAppId());

		sdkAdCarousel.setAdPositionName(adPosition.getName());
		sdkAdCarousel.setAppName(apps.getAppName());
		sdkAdCarousel.setOs(adDicService.getDic(apps.getPlatform()).getDicValue());

		return sdkAdCarousel;
	}

	@Override
	public Page<SdkAdCarousel> getPage(SdkAdCarousel adCarousel) {
		Page<SdkAdCarousel> page = null;
		SdkAdCarouselExample example = buildExample(adCarousel);
		int count = (int) sdkAdCarouselMapper.countByExample(example);
		if (adCarousel.getCurrentPage() != null && adCarousel.getPageSize() != null) {
			page = new Page<SdkAdCarousel>(count, adCarousel.getCurrentPage(), adCarousel.getPageSize());
		} else {
			page = new Page<SdkAdCarousel>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		List<SdkAdCarousel> list = sdkAdCarouselMapper.selectByExample(example);
		fillAdCarousel(list);
		page.bindData(list);
		return page;
	}

	private void fillAdCarousel(List<SdkAdCarousel> list) {
		if (CollectionUtils.isEmpty(list))
			return;
		for (SdkAdCarousel adCarousel : list) {
			/*
			 * if (Constants.AD_STRATEGY_DURATION.equals(adCarousel.getChangeType())) {
			 * adCarousel.setValue(adCarousel.getDurationValue()); } else if
			 * (Constants.AD_STRATEGY_PAGE.equals(adCarousel.getChangeType())) {
			 * adCarousel.setValue(adCarousel.getPageValue()); }
			 */
			String setValue = "显示时间" + adCarousel.getExpTime() + "s";
			if (adCarousel.getChapterNum() != null && adCarousel.getChapterNum() != Constants.INTEGER_0.intValue()) {
				setValue = StringUtils.concat(setValue, ",插页间隔章数" + adCarousel.getChapterNum());
			}
			if (adCarousel.getPageNum() != null && adCarousel.getPageNum() != Constants.INTEGER_0.intValue()) {
				setValue = StringUtils.concat(setValue, ",插页间隔页数" + adCarousel.getPageNum());
			}
			adCarousel.setSetValue(setValue);
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(adCarousel.getAdPosition());
			Apps apps = appsMapper.selectByPrimaryKey(adPosition.getAppId());
			adCarousel.setAdPositionName(adPosition.getName());
			// adCarousel.setChangeTypeName(adDicService.getDic(adCarousel.getChangeType()).getDicValue());
			adCarousel.setAppName(apps.getAppName());
			adCarousel.setOs(adDicService.getDic(apps.getPlatform()).getDicValue());
			adCarousel.setAdTypeName(adDicService.getDic(adPosition.getType()).getDicValue());
		}
	}

	private SdkAdCarouselExample buildExample(SdkAdCarousel adCarousel) {
		SdkAdCarouselExample example = new SdkAdCarouselExample();
		if (adCarousel == null)
			return example;
		SdkAdCarouselExample.Criteria criteria = example.createCriteria();
		/*
		 * if (adCarousel.getChangeType() != null) {
		 * criteria.andChangeTypeEqualTo(adCarousel.getChangeType()); }
		 */
		if (StringUtils.isNotBlank(adCarousel.getAdPositionName())) {
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
					.andNameEqualTo(adCarousel.getAdPositionName());
			List<AdPosition> list = adPositionMapper.selectByExample(adPositionExample);
			if (!CollectionUtils.isEmpty(list)) {
				List<Integer> pids = FluentIterable.from(list).transform((AdPosition adPosition) -> {
					return adPosition.getId();
				}).toList();
				criteria.andAdPositionIn(pids);
			}
		}
		if (adCarousel.getPlatform() != null) {
			AdPositionExample adPositionExample = new AdPositionExample();
			adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
			List<AdPosition> list = adPositionMapper.selectByExample(adPositionExample);
			List<Integer> other_id = new ArrayList<Integer>();
			list.forEach(action -> {
				Apps apps = appsMapper.selectByPrimaryKey(action.getAppId());
				if (!apps.getPlatform().equals(adCarousel.getPlatform())) {
					other_id.add(action.getId());
				}
			});
			if (!CollectionUtils.isEmpty(other_id)) {
				criteria.andAdPositionNotIn(other_id);
			}
		}
		// criteria.andIsDelEqualTo(Constants.STATE_INVALID);
		// criteria.andIsDefaultEqualTo(Constants.STATE_INVALID);// 默认不展示
		// criteria.andIdNotIn(Constants.DEFULT_IDS);
		example.setOrderByClause("id desc");

		return example;
	}

	@Override
	public List<AdPosition> getAdpositionList(String name) {
		AdPositionExample adPositionExample = new AdPositionExample();
		adPositionExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andNameLike("%" + name + "%");
		List<AdPosition> list = adPositionMapper.selectByExample(adPositionExample);
		return list;
	}

	public static void main(String[] args) {

		String str1 = "hello";
		String str2 = "hello";
		System.out.println(str1 == str2);
	}
}
