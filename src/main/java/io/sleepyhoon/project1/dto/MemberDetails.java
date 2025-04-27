package io.sleepyhoon.project1.dto;

import io.sleepyhoon.project1.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails implements UserDetails {

    private String email;
    private String username;
    private String password;
    private Role role;

    public MemberDetails(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.role = member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
