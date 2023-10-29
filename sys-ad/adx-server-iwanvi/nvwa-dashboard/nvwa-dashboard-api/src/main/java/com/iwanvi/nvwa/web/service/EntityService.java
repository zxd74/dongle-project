package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.vo.EntityQuery;

public interface EntityService {

	Page<Entity> selectPage(EntityQuery entityQuery, Integer custId, List<Integer> uids);

	List<Entity> list(EntityQuery entityQuery, Integer adverId, List<Integer> uids);

	void update(Entity entity, Entity oldEntity) throws Exception;

	void add(Entity entity) throws Exception;

	Entity info(Integer id);

	boolean entityNameIsExist(Entity entity);

	void auditAdx(AdxRelation adxRelation);

	Page<Entity> auditPage(EntityQuery entityQuery);

	Entity auditInfo(Integer id);

	void updateEntityByPid(Entity entity, Integer pid);

	void checkExtIdsOfDate();

	Entity getOneByPid(Integer pid);
	
	void updateByOrderOrBottom(Entity entity);

	Map<String, Object> checkEntity(Integer id);
}
