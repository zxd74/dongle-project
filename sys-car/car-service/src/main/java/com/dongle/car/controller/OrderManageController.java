package com.dongle.car.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("order")
public class OrderManageController {
    
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
