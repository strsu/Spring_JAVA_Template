package com.example.template.domain.Member.repository.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // jackson library는 빈 생성자가 없는 모델을 생성하는 방법을 모르기 때문에 이 어노테이션이 없으면 Dto 생성이 안 된다.
public class MemberDto {
    @NotNull
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;
}
