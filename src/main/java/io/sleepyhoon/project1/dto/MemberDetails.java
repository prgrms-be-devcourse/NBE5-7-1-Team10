package io.sleepyhoon.project1.dto;

import io.sleepyhoon.project1.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MemberDetails implements UserDetails {

    @Getter
    private final String email;
    @Getter
    private final String username;
    @Getter
    private final String password;
    private final String role;

    public MemberDetails(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }
}
