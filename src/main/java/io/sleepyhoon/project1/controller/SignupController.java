package io.sleepyhoon.project1.controller;

import io.sleepyhoon.project1.dto.ApiResponse;
import io.sleepyhoon.project1.dto.SignupDto;
import io.sleepyhoon.project1.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> processSignUp(@RequestBody SignupDto signupDto) {
        log.info("폼에 입력된 회원가입 정보 = {}", signupDto);
        memberService.save(signupDto);

        return ResponseEntity.ok(new ApiResponse<>(true, "저장 성공", 201));
    }

}
