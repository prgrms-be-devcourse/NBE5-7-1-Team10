package io.sleepyhoon.project1.config;


import io.sleepyhoon.project1.dao.MemberRepository;
import io.sleepyhoon.project1.dto.Role;
import io.sleepyhoon.project1.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // CommandLineRunner: 스프링이 애플리케이션을 다 띄운 뒤 자동으로 run을 실행한다
    @Bean // 빈 등록해줘야 반환되는 CommandLineRunner 객체를 다른 곳에서 주입받을 수 있다
    public CommandLineRunner createAdminAccount() {
        // return void인 람다 함수를 반환해주면 실행됨
        return args -> {
            // 이미 같은 이름의 관리자 계정이 없는 경우만 생성
            if (memberRepository.findByUsername("admin").isEmpty()) {
                Member admin = Member.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@admin.com")
                        .build();
                admin.setRole(Role.ADMIN);
                memberRepository.save(admin);
                log.info("관리자 계정 생성 완료");
            }
        };
    }

}
