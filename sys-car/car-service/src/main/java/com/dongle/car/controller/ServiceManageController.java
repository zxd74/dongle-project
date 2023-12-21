package com.dongle.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.service.DictService;
import com.dongle.commons.web.model.DongleResponse;
import com.dongle.commons.web.utils.DongleResponseUtils;

@RestController
@RequestMapping("service")
public class ServiceManageController {
        
    @Autowired
    private DictService dictService;

    @GetMapping("list")
    public DongleResponse services(@RequestParam String param) {
        return DongleResponseUtils.success("success");
    }

    @PostMapping("add")
    public DongleResponse addService() {
        //TODO: process POST request
        
        return DongleResponseUtils.success("success");
    }
    
    @PostMapping("update")
    public DongleResponse updateService() {
        //TODO: process POST request
        
        return DongleResponseUtils.success("success");
    }

    @GetMapping("disable")
    public DongleResponse disableService() {
        return DongleResponseUtils.success("success");
    }
    
}
