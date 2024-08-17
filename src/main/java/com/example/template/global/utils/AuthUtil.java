package com.example.template.global.utils;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Member.repository.MemberRepository;
import com.example.template.global.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Long getLoginMemberId() {
        Member member = this.getLoginMember();
        return member.getId();
    }

    public Member getLoginMember() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Member) {
            return (Member) authentication.getPrincipal();
        } else {
            throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
        }
    }
}