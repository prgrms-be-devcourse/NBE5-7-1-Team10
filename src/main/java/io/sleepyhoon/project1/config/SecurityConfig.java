package io.sleepyhoon.project1.config;

import io.sleepyhoon.project1.dto.Role;
import io.sleepyhoon.project1.exception.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // HttpSecurity라는 빈을 주입 받아야 한다
        // 보안에 관한걸 설정해준다
        log.info("securityconfig ");
        return http
                .formLogin(form -> {
                            form.loginPage("/login")
                                    .permitAll(); // 기본제공 로그인 폼 보이지 않게 하기
                        }
                )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                        auth
                            .requestMatchers("/signin", "/signup")
                                .anonymous()
                            .requestMatchers("/css/**", "/js/**", "/img/**", "/error",
                                    "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html")
                                .permitAll()
                            .requestMatchers("/index/**, /user/**")
                                .hasAnyAuthority(Role.ADMIN.name(), Role.MEMBER.name())
                            .requestMatchers("/admin")
                                .hasAnyAuthority(Role.ADMIN.name())
                            .anyRequest()
                                .authenticated();
                    }
                )
                .exceptionHandling(exception -> {
                    exception
                            .accessDeniedHandler(accessDeniedHandler()); //아래의 bean을 주입받음
                })
                .logout(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler("/");
    } // 다른 곳으로 리디렉션하는 핸들러가 필요하면 비슷하게 만들어서 쓰면 된다

}
