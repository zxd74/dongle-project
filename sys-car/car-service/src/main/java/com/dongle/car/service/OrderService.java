package com.dongle.car.service;

import java.util.List;

import com.dongle.car.model.OrderModel;

public interface OrderService {
    
    List<OrderModel> getOrderList(OrderModel model);
    OrderModel createOrder(OrderModel model);
    OrderModel getOrder(OrderModel model);
    void updateOrder(OrderModel model);
    void evalOrder(OrderModel model);
}
