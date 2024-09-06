package org.example.hashing.controller;

import org.example.hashing.utility.HashGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/decrypt")
public class DecryptController {

    @Value("${local_file.hashed_passwords}")
    private String hashedPasswordsPath;

    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public String decrypt() {
        return "decrypt.html";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping()
    public String decryptPost(@RequestParam("input") String input, Model model) {

        String decryptedPassword = HashGenerator.decryptHash(input.trim(), hashedPasswordsPath);
        model.addAttribute("password", decryptedPassword);

        return "decrypt.html";
    }
}
