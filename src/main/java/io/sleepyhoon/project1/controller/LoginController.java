package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.SignupDto;
import io.sleepyhoon.project1.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute SignupDto signupDto) {
        // form data라서 ModelAttribute 써야함
        log.info("폼에 입력된 회원가입 정보 = {}", signupDto);

        memberService.save(signupDto);

        return "redirect:/login"; // 자동으로 /login으로 이동
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/user")
    public String showUserPage() {
        return "user";
    }

}
