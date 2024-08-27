package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.example.hashing.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
public class HomeController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private IntegrationProperties integrationProperties;

    @RequestMapping(path = "/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {

        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        }
        model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());

        return "index.html";
    }

    @RequestMapping(path = "/user")
    public Principal user(Principal principal) {
        return principal;
    }
}
