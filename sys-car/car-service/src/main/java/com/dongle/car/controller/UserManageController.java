package com.dongle.car.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongle.car.config.ann.SysAdmin;
import com.dongle.car.model.UserModel;
import com.dongle.car.service.UserService;
import com.dongle.car.utils.Generator;
import com.dongle.commons.utils.DongleException;
import com.dongle.commons.web.model.DongleResponse;
import com.dongle.commons.web.utils.DongleResponseUtils;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("user")
public class UserManageController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user-list", method=RequestMethod.GET)
    @SysAdmin
    public DongleResponse requestMethodName(@RequestParam String param) {
        UserModel model = userService.queryModel(null);
        return DongleResponseUtils.success(model);
    }
    
    @RequestMapping(value = "login", method=RequestMethod.GET)
    public DongleResponse login(@RequestParam String param,HttpServletResponse response) {
        String token = Generator.token();
        response.setHeader("X-SYS-TOKEN", token);
        return DongleResponseUtils.success();
    }

    @RequestMapping(value = "info", method=RequestMethod.GET)
    public DongleResponse info(@RequestHeader(value = "X-SYS-TOKEN",required=false) String token) {
        if (token.equals("invalid_token")) {
            throw new DongleException("Invalid token");
        }
        // TODO 获取用户信息
        return DongleResponseUtils.success("获取用户信息成功");
    }
    
    @RequestMapping(value = "update", method=RequestMethod.POST)
    public DongleResponse update(Object userInfo){
        // TODO 用户信息更新
        return DongleResponseUtils.success();
    }
    
}
