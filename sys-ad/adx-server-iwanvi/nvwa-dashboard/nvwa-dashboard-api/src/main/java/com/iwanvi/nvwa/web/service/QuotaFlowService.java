package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.QuotaFlow;

import java.util.List;

public interface QuotaFlowService {

    List<QuotaFlow> sumWithDate(String flowId, String appids, String adpids, String channels1, String channels2,
                                 String versions, Integer sDate, Integer eDate);

    List<QuotaFlow> sumWithGroup(String flowId, String appids, String adpids, String channels1, String channels2,
                                 String versions, String group, Integer sDate, Integer eDate);

    List<QuotaFlow> detailReport(String id, String flowId, String appids, String adpids, String channels1, String channels2,
                                 String versions, String group, Integer sDate, Integer eDate);
}
