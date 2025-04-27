package io.sleepyhoon.project1.entity;

import io.sleepyhoon.project1.dto.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "members") // 테이블 이름 복수형으로 통일
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

//    @Id
//    @Column(name = "member_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Setter
    @Enumerated(EnumType.STRING)
    private Role role = Role.MEMBER;

    @Builder
    public Member(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }


}
