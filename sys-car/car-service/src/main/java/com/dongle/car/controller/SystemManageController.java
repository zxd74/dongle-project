package com.dongle.car.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.config.SysAdmin;

@RestController
@RequestMapping("sys")
@SysAdmin
public class SystemManageController {
    
    @GetMapping("/setting")
    public Object  setting() {

        return null;
    }

    
}
