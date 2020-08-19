package com.udacity.jwdnd.SuperDuperDrive.controller;

import com.udacity.jwdnd.SuperDuperDrive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
