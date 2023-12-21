package com.dongle.car.service;

import java.util.List;

import com.dongle.car.model.ServiceModel;

/**
 * 服务类型管理服务
 */
public interface ServiceManagerService {
        
    List<ServiceModel> getServiceModelList();

    void addServiceModel(ServiceModel serviceModel);
    void updateServiceModel(ServiceModel serviceModel);
    /**
     * 只能禁用服务，不可删除服务
     * @param serviceModel
     */
    void disableServiceModel(ServiceModel serviceModel);
}
