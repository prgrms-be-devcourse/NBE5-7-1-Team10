package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.MemberDetails;
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

        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        result.put("email", memberDetails.getEmail());
        result.put("username", memberDetails.getUsername());
        result.put("role", memberDetails.getAuthorities());

        return result;
    }


}
