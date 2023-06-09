package com.dongle.question.controller;

import com.dongle.question.config.annotation.DongleResponse;
import com.dongle.question.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@DongleResponse
public class UserController {

    @Autowired
    private UserService userService;

}
