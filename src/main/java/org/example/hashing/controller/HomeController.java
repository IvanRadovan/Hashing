package org.example.hashing.controller;

import org.example.hashing.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping(path = "/")
    String empty(Model model) {
        authenticationFacade.loggedInUserProvider()
                .ifPresent(appUser -> model.addAttribute("profileImage", appUser.getProfilePicture()));

        return "index.html";
    }
}
