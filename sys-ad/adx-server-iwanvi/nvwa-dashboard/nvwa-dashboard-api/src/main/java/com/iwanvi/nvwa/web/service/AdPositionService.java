package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.Module;
import com.iwanvi.nvwa.dao.model.Template;
import com.iwanvi.nvwa.dao.model.TemplateModuleRelation;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

public interface AdPositionService {

    void add(AdPosition adPosition);

    void update(AdPosition adPosition);

    void delete(AdPosition adPosition);

    void mappingSwitch(AdPosition adPosition);

    void deleteByExample(AdPosition adPosition);

    AdPosition get(Integer id);

    List<AdPosition> selectForList(AdPosition adPosition);

    Page<AdPosition> selectForPage(AdPosition adPosition);

    Page<AdPosition> selectForPageByUser(AdPosition adPosition, Integer cp, Integer ps, Integer uid);

	List<AdPosition> getCollectionList(Integer platForm, String mediaUuids);

    void addPageAttribute(String name, Integer type, Integer superiorId);

    boolean checkAttributeExist(String name, Integer type);

    boolean checkPositionExist(AdPosition adPosition);

    Map<String, List> getExcludeList(Integer id, Integer type);

    List<AdPosition> getAiKaPosition(Integer type, Integer status);

    List<Module> getModulesByAdpositionId(Integer adPositionId);
    
    List<TemplateModuleRelation> getRelationModulesByAdpositionId(Integer adPositionId, Integer templateId);
    
    List<TemplateModuleRelation> getRelationModulesByTemplateId(Integer templateId);
    
    Map<String, Object> getModulesMapByAdpositionId(Integer adPositionId, Integer templateId);

    public boolean checkUuidIsExisted(String uuid);
    
    boolean isSspAdPosition(AdPosition adPosition);

    void writeToRedis(AdPosition adPosition);

    void forecastExposureTask();

    Map<Integer, Map<String, Object>> stock(List<Map<String, Object>> positionList, Integer type);

    boolean modifiable(Integer id);

    List<AdPosition> getPositionByConsumer(String uuids);

    List<AdPosition> getPositionByApp(String uuids);

	List<Template> getTemplatesById(Integer positionId);

    List<AdPosition> listByConsumerAndApp(String consumerIds, String appIds);
}


