package com.dongle.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.service.OrderService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("order")
public class OrderManageController {

    @Autowired
    private OrderService orderService;
    
    @RequestMapping("list")
    public Object list(){
        return null;
    }

    @RequestMapping("update")
    public Object update(){
        return null;
    }

    @RequestMapping(value = "new", method=RequestMethod.GET)
    public Object createOrder(@RequestParam String param) {
        return new Object();
    }
    
    @RequestMapping(value = "order-detail", method=RequestMethod.GET)
    public Object orderDetail(@RequestParam String param) {
        return new Object();
    }
}
