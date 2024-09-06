package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private IntegrationProperties integrationProperties;

    @ModelAttribute
    public void addAttributes(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            model.addAttribute("login", oauth2User.getAttribute("login"));
            model.addAttribute("avatar", oauth2User.getAttribute("avatar_url"));
            model.addAttribute("profileUrl", oauth2User.getAttribute("html_url"));
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }
    }
}
