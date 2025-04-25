package io.sleepyhoon.project1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                                    .permitAll();
                        }
                )
//                .formLogin(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                        auth
                            .requestMatchers("/login/**", "/signup/**")
                            .anonymous()
                            .requestMatchers("/css/**", "/js/**", "/img/**")
                            .permitAll()
                            .requestMatchers("/index/**")
                            .hasAnyRole("ADMIN", "USER")
                            .requestMatchers("/admin/**")
                            .hasAnyRole("ADMIN")
                            .anyRequest()
                            .authenticated();
                    }
                )
                .logout(Customizer.withDefaults())
                .build();
    }

}
