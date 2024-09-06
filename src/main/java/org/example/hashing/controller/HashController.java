package org.example.hashing.controller;

import org.example.hashing.model.hash.MD5Password;
import org.example.hashing.model.hash.SHA256Password;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/hash")
public class HashController {

    @GetMapping()
    public String hash() {
        return "hash.html";
    }

    @PostMapping()
    public String hashPost(@RequestParam("input") String input, Model model) {

        model.addAttribute("md5", new MD5Password(input.trim()));
        model.addAttribute("sha256", new SHA256Password(input.trim()));

        return "hash.html";
    }
}
