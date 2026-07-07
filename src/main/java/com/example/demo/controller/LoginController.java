package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // This maps directly to http://localhost:8081/login cleanly!
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }
}