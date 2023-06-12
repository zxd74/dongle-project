package com.dongle.stack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    @RequestMapping("/stack")
    public String gotoStack(){
        System.out.println("access stack");
        return "/pages/stock.html";
    }
}
