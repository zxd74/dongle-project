package com.dongle.car.service.impl;

import java.util.List;

import com.dongle.car.model.ServiceModel;
import com.dongle.car.service.ServiceManagerService;

import lombok.Data;

@Data
public class ServiceManagerServiceImpl implements ServiceManagerService{
    
    @Override
    public List<ServiceModel> getServiceModelList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getServiceModelList'");
    }

    @Override
    public void addServiceModel(ServiceModel serviceModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addServiceModel'");
    }

    @Override
    public void updateServiceModel(ServiceModel serviceModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateServiceModel'");
    }

    @Override
    public void disableServiceModel(ServiceModel serviceModel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableServiceModel'");
    }

}
