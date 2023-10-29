package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface PutService {

	SwaggerPage<List<Put>> listForPage(Put put, List<Integer> uids, Integer adverId, List<Integer> advers, Integer cp, Integer ps);

	List<Put> list(Put put, List<Integer> uids, Integer adverId, List<Integer> advers);

	void update(Put put, Put oldPut);

	void add(Put put);

	Put info(Integer id);
	
	Put getById(Integer id);

	boolean checkNameExistInOther(String putName, Integer pid);

//	Map<String, Object> selectSchedules(Map<String, Object> paramMap);

	void update(Put put);

	boolean resetPutLimit(Put put);
	
	public void checkPutState(Put put, Put oldPut);

	void delete(Put put);

	void updatePutByPid(Put put, Integer pid);
	
//	Integer getPutCountByOidOrPid(Integer pid,Integer oid);

	void updatePutByPid(Put put, Integer pid, Integer status);
	
	void deletePutCustomDx(List<Integer> tags);

}
