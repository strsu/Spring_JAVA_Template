package com.example.template.domain.Member.service;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Member.entity.MemberRole;
import com.example.template.domain.Member.repository.MemberRepository;
import com.example.template.domain.Member.repository.MemberRoleRepository;
import com.example.template.domain.Member.repository.dto.MemberDto;
import com.example.template.global.error.ErrorCode;
import com.example.template.global.error.exception.EntityAlreadyExistException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final PasswordEncoder passwordEncoder; // config폴더 밑에 SecurityConfig를 정의해야 사용가능

    @Transactional(readOnly = true)
    public Member getUserInfo(MemberDto memberDto) {
        Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(() -> new EntityNotFoundException("err"));;
        return member;
    }


    @Transactional
    public Member signup(MemberDto memberDto) {

        if (memberRepository.findByEmail(memberDto.getEmail()).isPresent()) {
            throw new EntityAlreadyExistException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        Optional<MemberRole> optionalRole = memberRoleRepository.findByAuthority("ROLE_USER");
        if(optionalRole.isEmpty()) {
            throw new EntityNotFoundException("Role을 찾을 수 없습니다.");
        }

        MemberRole memberRole = optionalRole.get();
        Set<MemberRole> memberRoles = new HashSet<>();
        memberRoles.add(memberRole);

        // 유저 정보를 만들어서 save
        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .email(memberDto.getEmail())
                .activated(true)
                .authorities(memberRoles)
                .build();

        return memberRepository.save(member);
    }

    @Transactional
    public boolean signin(String email, String password) {
        /*
        * Optional<Member> member = memberRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
        * 다른 해시 값:
        *  passwordEncoder.encode(password)는 매번 다른 해시 값을 생성합니다.
        *  즉, 동일한 비밀번호라도 매번 다른 해시가 생성되므로, 데이터베이스에 저장된 해시 값과 일치하지 않습니다.
        * 해시 비교 방법:
        *  비밀번호를 저장할 때는 passwordEncoder.encode()를 사용해 해시를 생성하고,
        *  로그인할 때는 사용자가 입력한 비밀번호를 passwordEncoder.matches() 메서드를 사용하여 데이터베이스에 저장된 해시와 비교해야 합니다.
        *
        * */

        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent() && passwordEncoder.matches(password, member.get().getPassword())) {
            return true;
        }
        return false;
    }

}
