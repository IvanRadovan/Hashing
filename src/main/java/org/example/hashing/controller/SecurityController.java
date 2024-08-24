package org.example.hashing.controller;

import org.example.hashing.model.AppUser;
import org.example.hashing.security.IAuthenticationFacade;
import org.example.hashing.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SecurityController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private AppUserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new AppUser());

        return "login.html";
    }
//
//    @GetMapping("/login/success")
//    public String loginSuccessful(RedirectAttributes redirectAttributes) {
//        String username = authenticationFacade.loggedInUserProvider()
//                .orElseThrow(() -> new UsernameNotFoundException("No Authorized user was found."))
//                .getUsername();
//        redirectAttributes.addFlashAttribute("alertMessage", "Välkommen %s".formatted(username));
//
//        return "redirect:/";
//    }
//
//    @GetMapping("/logout/success")
//    public String logout(RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("alertMessage", "Du är nu utloggad!");
//
//        return "redirect:/";
//    }
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


