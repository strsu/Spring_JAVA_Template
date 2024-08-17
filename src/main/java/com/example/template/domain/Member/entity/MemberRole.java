package com.example.template.domain.Member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
public class MemberRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long id;

    @Column
    private String authority; // 반드시 ROLE_ 로 시작해야 한다!! 내부에서 ROLE_를 prefix로 찾기 때문!
}