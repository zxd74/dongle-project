package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.PutPositionTime;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.OrderPutQuery;

public interface OrderPutService {

	Page<OrderPut> selectPage(OrderPutQuery orderPutQuery, List<Integer> advers);

	List<OrderPut> selectList(OrderPutQuery orderPutQuery, List<Integer> advers);
	
	void update(OrderPut orderPut);

	void update(OrderPut orderPut, OrderPut oldOrderPut);

	void updateOrderPutByOid(OrderPut orderPut, Integer oid);

	OrderPut info(Integer orderPutId);

	public void checkPutState(OrderPut orderPut, OrderPut oldOrdePut);

	void updateByOrderPutExample(OrderPut orderPut,OrderPutExample orderPutExample);

	void add(OrderPut orderPut);

	boolean checkNameIsExistByOrder(OrderPut orderPut);

	void delete(OrderPut orderPut);
}
