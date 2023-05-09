package com.dongle.question.controller;

import com.dongle.question.config.annotation.DongleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index(HttpServletResponse response){
        System.out.println("access index");
        response.setHeader("Access-Control-Allow-Origin","*");
        return "OK";
    }

    @RequestMapping("/login")
    public Object login(){
        return null;
    }

}
