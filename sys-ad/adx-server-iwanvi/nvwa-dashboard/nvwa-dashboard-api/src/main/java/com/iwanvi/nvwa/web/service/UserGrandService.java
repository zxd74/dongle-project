package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.UserGrand;

public interface UserGrandService {

	void insertBatchUserGrand(Integer uid, String[] aids, Integer userId, Integer type);

	void delete(Integer uid);

	void update(Integer uid, Integer aid);

	void insert(Integer uid, Integer aid, Integer userId, Integer type);

	List<UserGrand> get(Integer uid);

	List<UserGrand> getByAidAndType(Integer aid, Integer type);
	
	List<UserGrand> getByUidAndType(Integer uid, Integer type);
	
	List<Integer> getIdListByUidAndType(Integer uid, Integer type);

	List<UserGrand> selectForList(UserGrand userGrand);

	void add(UserGrand userGrand);

	void addForFs(UserGrand userGrand);

	void deleteById(Integer id);

	void delete(UserGrand userGrand);
}
