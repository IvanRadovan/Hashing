package org.example.hashing.controller;

import org.example.hashing.model.AppUser;
import org.example.hashing.security.IAuthenticationFacade;
import org.example.hashing.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SecurityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppUserService userService;

    @RequestMapping("/login")
    public String loginRegular(Model model) {
        model.addAttribute("user", new AppUser());

        return "login.html";
    }

    @RequestMapping("/login/oauth2")
    public String loginGitHub(Model model) {
        model.addAttribute("user", new AppUser());

        return "login.html";
    }

    @GetMapping("/login/success")
    public String loginSuccessful(RedirectAttributes redirectAttributes) {
        AppUser user = authenticationFacade.loggedInUserProvider()
                .orElseThrow(() -> new UsernameNotFoundException("No Authorized user was found."));

        // TODO: Add message but use div with th:if to render the message if someone is logged in
//        redirectAttributes.addFlashAttribute("alertMessage", "Hello %s".formatted(user));

        redirectAttributes.addFlashAttribute("user", user);

        return "redirect:/";
    }

//
    @GetMapping("/logout/success")
    public String logout(RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("alertMessage", "You have been logged out!");

        return "redirect:/";
    }

//
//    @GetMapping("/login/new-user")
//    public String signUp(Model model, @ModelAttribute("errorMessage") String errorMessage) {
//        model.addAttribute("pageTitle", "Skapa Konto");
//        model.addAttribute("header", "Registrera konto");
//        model.addAttribute("errorMessage", "Lösenorden matchar inte. Försök igen.");
//        model.addAttribute("usernameLabelText", "Ange e-postadress:");
//        model.addAttribute("usernamePlaceholder", "email@exempel.com");
//        model.addAttribute("passwordLabelText", "Ange lösenord:");
//        model.addAttribute("passwordPlaceholder", "Lösenord");
//        model.addAttribute("passwordConfirmLabelText", "Upprepa lösenord:");
//        model.addAttribute("passwordConfirmPlaceholder", "Lösenord");
//        model.addAttribute("buttonText", "Skapa Konto");
//        model.addAttribute("user", new AppUser());
//
//        return "security/new-user.html";
//    }
//
//    // TODO: Add validation for existing usernames
//    @PostMapping("/login/register")
//    public String register(RedirectAttributes redirectAttributes, @ModelAttribute AppUser user) {
//        if (userService.addUser(user)) {
//            redirectAttributes.addFlashAttribute("alertMessage", "Kontot '%s' har skapats!".formatted(user.getUsername()));
//            return "redirect:/";
//        } else {
//            redirectAttributes.addFlashAttribute("errorMessage", "Användarnamnet är redan upptaget!");
//            return "redirect:/login/new-user";
//        }
//    }


}


