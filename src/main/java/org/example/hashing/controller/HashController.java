package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.example.hashing.model.hash.MD5Password;
import org.example.hashing.model.hash.SHA256Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/hash")
public class HashController {

    @Autowired
    private IntegrationProperties integrationProperties;

    @GetMapping()
    public String showForm(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        return "hash.html";
    }

    @PostMapping()
    public String processForm(@RequestParam("input") String input, Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");
            String profileUrl = oauth2User.getAttribute("html_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
            model.addAttribute("profileUrl", profileUrl);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        model.addAttribute("md5", new MD5Password(input.trim()));
        model.addAttribute("sha256", new SHA256Password(input.trim()));

        return "hash.html";
    }

}
