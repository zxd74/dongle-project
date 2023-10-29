package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutAll;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.PutPositionTime;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.OrderPutAllQuery;

public interface OrderPutAllService {

	Page<OrderPutAll> selectPage(OrderPutAllQuery orderPutAllQuery, Integer adverId, List<Integer> advers);

	List<OrderPutAll> selectList(OrderPutAllQuery orderPutAllQuery, Integer adverId, List<Integer> advers);
	
	void update(OrderPutAll orderPutAll);

	OrderPutAll info(Integer orderPutId);

	void checkPutState(PutPositionTime putPositionTime, PutPositionTime oldPositionTime);

	void add(OrderPutAll orderPutAll);

	boolean checkNameIsExistByOrder(OrderPutAll orderPutAll);

	void delete(OrderPutAll orderPutAll);

	List<Map<String, Object>> getAdpositionsByPut(Integer id);

	void updateByRunState(OrderPutAll orderPutAll);
}
