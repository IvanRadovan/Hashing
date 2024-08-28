package org.example.hashing.controller;

import org.example.hashing.configuration.IntegrationProperties;
import org.example.hashing.model.HashPassword;
import org.example.hashing.security.IAuthenticationFacade;
import org.example.hashing.utility.FileSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
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
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        return "index.html";
    }

    @RequestMapping(path = "/user")
    public Principal user(Principal principal) {
        return principal;
    }

    @GetMapping("/hash")
    public String showForm(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        model.addAttribute("hashPassword", new HashPassword());
        return "hash.html";
    }

    @PostMapping("/hash")
    public String processForm(@RequestParam("input") String input, Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        HashPassword hashPassword = new HashPassword(input);
        model.addAttribute("md5", hashPassword.getMd5());
        model.addAttribute("sha256", hashPassword.getSha256());

        return "hash.html";
    }

    @GetMapping("/decrypt")
    public String showForm2(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        return "decrypt.html";
    }

    @PostMapping("/decrypt")
    public String processForm2(@RequestParam("input") String input, Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String login = oauth2User.getAttribute("login");
            String avatar = oauth2User.getAttribute("avatar_url");

            model.addAttribute("login", login);
            model.addAttribute("avatar", avatar);
        } else {
            model.addAttribute("blank", integrationProperties.getUserData().getBlankProfile());
        }

        model.addAttribute("password",   FileSupplier.decryptPassword(input));

        return "decrypt.html";
    }


}
