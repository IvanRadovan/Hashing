//package org.example.hashing.controller;
//
//import org.example.hashing.security.IAuthenticationFacade;
//import org.example.hashing.service.AppUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//
//@Controller
//public class SecurityController {
//
//    @Autowired
//    private IAuthenticationFacade authenticationFacade;
//
//    @Autowired
//    private AppUserService userService;
//
//    @GetMapping("/")
//    public String profile(OAuth2AuthenticationToken token, Model model) {
//        model.addAttribute("name", token.getPrincipal().getAttribute("name"));
//        model.addAttribute("email", token.getPrincipal().getAttribute("email"));
//        model.addAttribute("photo", token.getPrincipal().getAttribute("picture"));
//
//        return "";
//    }
//
//    @GetMapping("/login")
//    public String login() {
//        return "custom_login";
//    }
//
//}
//
//
