package io.sleepyhoon.project1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    // 공백이나 비어있을 경우 Spring에서 MethodArgumentNotValidException을 발생시킨다
//    @NotBlank(message = "이메일은 필수 입력입니다.")
    private String email;
//    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String username;
//    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    private String password;
}
