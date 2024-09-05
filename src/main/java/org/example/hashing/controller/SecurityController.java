package org.example.hashing.controller;

import org.example.hashing.security.IAuthenticationFacade;
import org.example.hashing.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SecurityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppUserService userService;

    @RequestMapping(path = "/login")
    public String login(Model model) {
        return "login.html";
    }

}


