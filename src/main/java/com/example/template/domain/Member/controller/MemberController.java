package com.example.template.domain.Member.controller;

import com.example.template.domain.Member.entity.Member;
import com.example.template.domain.Member.repository.dto.MemberDto;
import com.example.template.domain.Member.service.MemberService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member") // 하위 Controller에 모두 /api/ 이 붙는다
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Member> signup(
            @Valid @RequestBody MemberDto memberDto
    ) {
        System.out.println(memberDto);
        return ResponseEntity.ok(memberService.signup(memberDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signin(
            // @RequestBody Map<String, String> json
            @RequestBody ObjectNode json
    ) {
        /*
        * body는 하나의 object만 받을 수 있다.
        * 때문에 여러개의 위처럼 Map을 통해서 받거나 ObjectNode를 이용해야 한다.
        * */
        // boolean exist = memberService.signin(json.get("email"), json.get("password")); // For Map
        boolean exist = memberService.signin(json.get("email").asText(), json.get("password").asText()); // For ObjectNode

        if (exist) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
