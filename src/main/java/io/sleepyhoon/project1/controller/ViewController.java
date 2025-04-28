package io.sleepyhoon.project1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ViewController {

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

//    @PostMapping("/signup")
//    public String processSignUp(@ModelAttribute SignupDto signupDto) {
//        // form data라서 ModelAttribute 써야함
//        log.info("폼에 입력된 회원가입 정보 = {}", signupDto);
//        memberService.save(signupDto);
//
//        return "redirect:/signin";
//    }

    @GetMapping("/signin")
    public String showSignInPage() {
        return "signin";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "redirect:/signin"; // permitAll로 접근할 수 없게 리디렉션
    }

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

}
