package com.iwanvi.nvwa.web.service;

import java.rmi.ServerException;
import java.util.List;

import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.vo.OrdersQuery;

public interface OrdersService {

	Page<Orders> listForPage(OrdersQuery ordersQuery, List<Integer> adverIds);

	void update(Orders orders);

	void add(Orders orders);

	Orders info(Integer id);

	List<Orders> list(OrdersQuery ordersQuery, List<Integer> adverIds);

	boolean ordersNameIsExist(String ordersName);

	void delete(Integer id);
	
	boolean checkOrdersIsTrue(Orders orders);
	
	Integer getCustIdByPutId(Integer putId);

}
