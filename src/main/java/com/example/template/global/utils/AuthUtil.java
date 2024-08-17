package com.example.template.global.utils;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Member.repository.MemberRepository;
import com.example.template.global.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final MemberRepository memberRepository;

    public Long getLoginMemberIdOrNull() {
        try {
            final String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.valueOf(memberId);
        } catch (Exception e) {
            return -1L;
        }
    }

    public Long getLoginMemberId() {
        try {
            final String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
            return Long.valueOf(memberId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public Member getLoginMember() {
        try {
            final String memberId = SecurityContextHolder.getContext().getAuthentication().getName();
            return memberRepository.findByEmail(memberId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}