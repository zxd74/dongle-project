package com.dongle.car.service;

import java.util.List;

import com.dongle.car.model.ProductModel;

/**
 * 产品详情服务：类型管理，类型校验
 */
public interface ProductService {
    List<ProductModel> getProductModelList();
    void addProductModel(List<ProductModel> models);
    void updateProductModel(List<ProductModel> models);
    void disableProductModel(List<ProductModel> models);
}
