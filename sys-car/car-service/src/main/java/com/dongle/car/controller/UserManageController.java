package com.dongle.car.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.config.SysAdmin;
import com.dongle.car.utils.Generator;
import com.dongle.commons.utils.DongleException;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("user")
public class UserManageController {

    @RequestMapping(value = "user-list", method=RequestMethod.GET)
    @SysAdmin
    public Object requestMethodName(@RequestParam String param) {
        return new Object();
    }
    
    @RequestMapping(value = "login", method=RequestMethod.GET)
    public Object login(@RequestParam String param,HttpServletResponse response) {
        String token = Generator.token();
        response.setHeader("X-SYS-TOKEN", token);
        return new Object();
    }

    @RequestMapping(value = "info", method=RequestMethod.GET)
    public Object info(@RequestHeader("X-SYS-TOKEN") String token) {
        if (token.equals("invalid_token")) {
            throw new DongleException("Invalid token");
        }
        // TODO 获取用户信息
        return new Object();
    }
    
    @RequestMapping(value = "update", method=RequestMethod.POST)
    public Object update(Object userInfo){
        // TODO 用户信息更新
        return false;
    }
    
}
