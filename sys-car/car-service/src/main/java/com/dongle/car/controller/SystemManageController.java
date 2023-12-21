package com.dongle.car.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.config.ann.SysAdmin;
import com.dongle.car.service.DictService;
import com.dongle.commons.web.model.DongleResponse;
import com.dongle.commons.web.utils.DongleResponseUtils;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("sys")
@SysAdmin
public class SystemManageController {

    @Autowired
    private DictService dictService;
    
    @GetMapping("/setting")
    public Object  setting() {

        return null;
    }
}
