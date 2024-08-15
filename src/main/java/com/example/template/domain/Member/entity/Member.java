package com.example.template.domain.Member.entity;

import com.example.template.domain.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="email", length = 64, nullable = false, unique = true)
    private String email;

    @Column(length = 32, nullable = false)
    private String username;

    @JsonIgnore // 이게 있으면 Json으로 만들어질 때 생략된다.
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

}
