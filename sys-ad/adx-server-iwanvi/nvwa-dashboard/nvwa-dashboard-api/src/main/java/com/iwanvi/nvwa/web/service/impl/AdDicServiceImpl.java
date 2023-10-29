package com.iwanvi.nvwa.web.service.impl;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.DictionaryMapper;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.iwanvi.nvwa.dao.model.DictionaryExample;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class AdDicServiceImpl implements AdDicService {

	@Autowired
	private DictionaryMapper adDicMapper;

	private LoadingCache<Integer, Dictionary> adDicIdCache;
	private LoadingCache<Integer, List<Dictionary>> adDicListIdCache;
	private static final Integer DEFAULT_DEPTH = 1;

	@PostConstruct
	public void init() {
		adDicIdCache = CacheBuilder.newBuilder().expireAfterWrite(600, TimeUnit.SECONDS).build(new CacheLoader<Integer, Dictionary>() {
			@Override
			public Dictionary load(Integer id) throws Exception {
				return adDicMapper.selectByPrimaryKey(id);
			}
		});
		adDicListIdCache = CacheBuilder.newBuilder().expireAfterWrite(600, TimeUnit.SECONDS).build(new CacheLoader<Integer, List<Dictionary>>() {
			@Override
			public List<Dictionary> load(Integer id) throws Exception {
				DictionaryExample dictionaryExample = new DictionaryExample();
				dictionaryExample.createCriteria().andDicGroupEqualTo(id).andStatusEqualTo(Constants.STATE_VALID);
				dictionaryExample.setOrderByClause(" id ");
				return adDicMapper.selectByExample(dictionaryExample);
			}
		});
	}

	@Override
	public Dictionary getDic(Integer id) throws ServiceException {
		try {
			return adDicIdCache.get(id);
		} catch (ExecutionException e) {
			throw new ServiceException("get dic by id error!", e);
		}
	}

	@Override
	public List<Dictionary> getDicByGroupId(Integer groupId) throws ServiceException {
		if (Constants.ADVERTIDER_INDUSTRY.equals(groupId)){
			DictionaryExample example = new DictionaryExample();
			example.createCriteria().andDicGroupEqualTo(groupId).andStatusEqualTo(Constants.STATE_VALID);
			return adDicMapper.selectByExample(example);
		}else{
			return getDicByGroupId(groupId, DEFAULT_DEPTH);
		}
	}

	@Override
	public List<Dictionary> getDicByGroupId(Integer groupId, Integer depth) throws ServiceException {
		try {
			List<Dictionary> adDics = adDicListIdCache.get(groupId);
			if (depth != null && depth == 2 && !CollectionUtils.isEmpty(adDics)) {
				for (Dictionary adDic : adDics) {
					List<Dictionary> childs = adDicListIdCache.get(adDic.getId());
					adDic.setChilds(childs);
				}
			}
			return adDics;
		} catch (ExecutionException e) {
			throw new ServiceException("get dic by group id error!", e);
		}
	}

	@Override
	public List<Dictionary> getDicByIds(List<Integer> ids) throws ServiceException {
		DictionaryExample adDicExample = new DictionaryExample();
		adDicExample.createCriteria().andIdIn(ids).andStatusEqualTo(Constants.STATE_VALID);
		return adDicMapper.selectByExample(adDicExample);
	}

	/**
	 * 添加广告主行业,返回id
	 * @param industry
	 */
	@Override
	@Transactional
	public void addIndustry(Dictionary dictionary) {
		if (StringUtils.isNotBlank(dictionary.getDicValue())) {
			dictionary.setDicValue(dictionary.getDicValue());
			dictionary.setDicGroup(Constants.ADVERTIDER_INDUSTRY);
			dictionary.setDicType(Constants.INTEGER_1);
			dictionary.setStatus(Constants.STATE_VALID);
			adDicMapper.insert(dictionary);
		}
	}

	@Override
	public Map<String, Integer> getDicByGroupIdWithMap(Integer gid) {
		Map<String, Integer> dicMap = Maps.newConcurrentMap();
		DictionaryExample dictionaryExample = new DictionaryExample();
		dictionaryExample.createCriteria().andDicGroupEqualTo(gid).andStatusEqualTo(Constants.STATE_VALID);
		List<Dictionary> dictionaries = adDicMapper.selectByExample(dictionaryExample);
		for (Dictionary dictionary : dictionaries) {
			dicMap.put(dictionary.getDicValue(), dictionary.getId());
		}
		return dicMap;
	}
}
