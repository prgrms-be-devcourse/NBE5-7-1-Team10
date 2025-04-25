package io.sleepyhoon.project1.service;

import io.sleepyhoon.project1.dao.MemberRepository;
import io.sleepyhoon.project1.dto.MemberDetails;
import io.sleepyhoon.project1.dto.SignupDto;
import io.sleepyhoon.project1.entity.Member;
import io.sleepyhoon.project1.exception.MemberDuplicateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    // user create read 구현

    private final PasswordEncoder passwordEncoder; //주입

    private final MemberRepository memberRepository; // 주입

    public void save(SignupDto signupDto) {

////         중복된 이메일, username으로 가입 불가
//        if (memberRepository.findByEmail(signupDto.getEmail()).isPresent()){
//            throw new MemberDuplicateException("이미 가입된 이메일입니다.");
//        }
//        if (memberRepository.findByUsername(signupDto.getUsername()).isPresent()) {
//            throw new MemberDuplicateException("이미 사용 중인 이름입니다.");
//        }

        Member member = Member.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .email(signupDto.getEmail())
                .build();

        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByUsername(username);
        Member foundMember = memberOptional.orElseThrow(
                () -> new UsernameNotFoundException("Username" + username + " not found")
        );
        log.info("load by user name 불러온 멤버 정보 = {}", foundMember);
        return new MemberDetails(foundMember);
    }
}
