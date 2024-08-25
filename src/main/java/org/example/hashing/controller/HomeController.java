package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.example.hashing.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private IntegrationProperties integrationProperties;

    @GetMapping(path = "/")
    String empty(Model model) {
        String blankProfile = integrationProperties.getUserData().getBlankProfile();

        authenticationFacade
                .loggedInUserProvider()
                .ifPresentOrElse(user -> model.addAttribute("user", user),
                        () -> model.addAttribute("blank", blankProfile));

        return "index.html";
    }
}
