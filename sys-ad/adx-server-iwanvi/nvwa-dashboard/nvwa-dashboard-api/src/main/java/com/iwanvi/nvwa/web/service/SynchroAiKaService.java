package com.iwanvi.nvwa.web.service;

import java.util.List;

public interface SynchroAiKaService {
	void synchroAiKaOrderPutByOrderId(Integer orderId);

    void syncCustFormXcar(List<Integer> xcarCustIdList);

    void synchroAiKaTemplate(Integer templateId);

    void syncTask();
    void syncTask(String date);
    void deleteTemplateAndPosition(List<Integer> list);

}
