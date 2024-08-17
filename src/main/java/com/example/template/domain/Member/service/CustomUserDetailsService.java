package com.example.template.domain.Member.service;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Member.repository.MemberRepository;
import com.example.template.global.error.ErrorCode;
import com.example.template.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        /*
        * 로그인시에 DB에서 유저정보와 권한정보를 가져와서 해당 정보를 기반으로 userdetails.User 객체를 생성해 리턴
        * Member를 만들 때 UserDetails를 상속받아서 형변환이 필요없다
        * */
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            return member.get();
        }
        throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
    }

}
