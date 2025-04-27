package io.sleepyhoon.project1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/me")
    public Map<String, Object> currentUserInfo(Authentication authentication) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", authentication.getName());
        result.put("email", authentication.getPrincipal());
        result.put("authorities", authentication.getAuthorities());
        return result;
    }


}
