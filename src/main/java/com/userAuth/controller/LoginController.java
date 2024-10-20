package com.userAuth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/welcome")
    public String welcomePage(Model model, Authentication authentication) {
        String message;
        if (authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            message = "Hey welcome Admin.";
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            String authProvider = user.getAuthorities().iterator().next().getAuthority();
            if (authProvider.contains("FACEBOOK")) {
                message = "Welcome " + user.getAttribute("name") + ", you have chosen Facebook Authentication";
            } else {
                message = "Welcome " + user.getAttribute("name") + ", you have chosen Google Authentication";
            }
        } else {
            message = "Welcome User";
        }
        model.addAttribute("message", message);
        return "welcome";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}