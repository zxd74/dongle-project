package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface PlanService {

	boolean checkNameIsExist(String planName);

	SwaggerPage<List<Plan>> selectByPage(Plan plan, List<Integer> uids, Integer cp, Integer ps);

	void add(Plan plan);

	List<Plan> selectByList(Plan plan, List<Integer> uids);

	Plan info(Integer id);

	Plan getById(Integer id);

	void update(Plan plan);
	
	void updatePlanByUid(Plan plan, Integer uid, Integer status);

	void delete(Integer id);

	boolean noticePlanLimit(Plan plan);

	void deletePlan(Integer integer);


}
