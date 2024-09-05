package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.example.hashing.utility.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/decrypt")
public class DecryptController {

    @Autowired
    private IntegrationProperties integrationProperties;

    @Value("${local_file.hashed_passwords}")
    private String hashedPasswords;

    @GetMapping()
    public String showForm2(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
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

        return "decrypt.html";
    }

    @PostMapping()
    public String processForm2(@RequestParam("input") String input, Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
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

        model.addAttribute("password",   HashGenerator.decryptHash(input.trim(), hashedPasswords));

        return "decrypt.html";
    }
}
