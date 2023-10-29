package com.iwanvi.nvwa.crontab.service.impl;

import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.crontab.service.AdDicService;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.DictionaryMapper;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.Dictionary;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class AdDicServiceImpl implements AdDicService {

	@Autowired
	private DictionaryMapper dictionaryMapper;
	
	@Autowired
	private AreaMapper areaMapper;
	
	private LoadingCache<Integer, Dictionary> adDicIdCache;
	
	private LoadingCache<Integer, Area> adAreaIdCache;

	@PostConstruct
	private void init() {
		adDicIdCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Dictionary>() {
			@Override
			public Dictionary load(Integer id) throws Exception {
				return dictionaryMapper.selectByPrimaryKey(id);
			}
		});
		adAreaIdCache = CacheBuilder.newBuilder().build(new CacheLoader<Integer, Area>() {
			@Override
			public Area load(Integer id) throws Exception {
				return areaMapper.selectByPrimaryKey(id);
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
	public Area getArea(Integer id) throws ServiceException {
		try {
			return adAreaIdCache.get(id);
		} catch (ExecutionException e) {
			throw new ServiceException("get area by id error!", e);
		}
	}

}
