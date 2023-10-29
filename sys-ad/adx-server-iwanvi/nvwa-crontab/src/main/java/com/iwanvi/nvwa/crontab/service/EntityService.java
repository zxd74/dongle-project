package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.proto.AdModelsProto.Creative;

public interface EntityService {

	List<Entity> getValidEntityByPut(Integer id);

	Creative buildCreative(Entity entity, Put put);

	Creative buildOrderCreative(Entity ampEntity, OrderPut put);

}
