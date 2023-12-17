package com.dongle.car.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.config.SysAdmin;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("service")
public class ServiceManageController {
    
    @RequestMapping(value = "list", method=RequestMethod.GET)
    public Object list(@RequestParam String param) {
        return new Object();
    }
    
    @RequestMapping("service/add")
    @SysAdmin
    public Object  addService(String serviceName, String serviceDesc) {
        return null;
    }

    @RequestMapping(value = "update", method=RequestMethod.GET)
    @SysAdmin
    public Object update(@RequestParam String param) {
        return new Object();
    }

    @RequestMapping(value = "lists", method=RequestMethod.GET)
    public Object lists(@RequestParam String param) {
        return new Object();
    }

    @RequestMapping("good/add")
    @SysAdmin
    public Object  addGood(String serviceName, String serviceDesc) {
        return null;
    }
    
    @RequestMapping("good/update")
    @SysAdmin
    public Object  updateGood(String serviceName, String serviceDesc) {
        return null;
    }
    
}
