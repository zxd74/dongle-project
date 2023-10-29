package com.iwanvi.nvwa.web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.AppChannelQueryReq;

public interface AppChannelService {

	void insert(AppChannel record);

	void delete(Integer id);

	List<AppChannel> select(String name);

	Page<AppChannel> selectPage(AppChannelQueryReq queryReq);

	List<AppChannel> getAllByFc();

	boolean AppChanneNameIsExist(String name);

	List<AppChannel> selectOneLevel();

	List<AppChannel> selectTwoLevel();

	List<AppChannel> selectAppChannel();

	boolean batchImport(String fileName, MultipartFile file) throws Exception;

	List<AppChannel> selectByLevel(List<String> level1ChannelNames, Integer level, String name);
}
