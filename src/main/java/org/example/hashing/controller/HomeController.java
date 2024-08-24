package se.systementor.javasecstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import se.systementor.javasecstart.security.IAuthenticationFacade;

@Controller
public class HomeController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping(path = "/")
    String empty(Model model) {
        authenticationFacade.loggedInUserProvider()
                .ifPresent(appUser -> model.addAttribute("profileImage", appUser.getProfileImage()));
        model.addAttribute("activeFunction", "home");

        return "home";
    }
}
