package com.example.template.domain.Member.entity;

import com.example.template.domain.TimeStamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/*
* Django에서 User 모델을 생성할 때 AbstractBaseUser, PermissionsMixin 을 상속 하듯,
* Spring에서도 User 모델를 생성할 때 UserDetails를 상속받는게 좋은 것 같다.
* --> UserDetailsService 등 기존객체타입을 유지할 수 있어서
*
* 추가로! Authorities 없이는 모델을 만드는게 힘든 것 같다.
* TODO - 일단은 Authorities를 만들어서 모델링을 하고, 나중에 익숙해지면 없이도 생성할 수 있는지 고민하기!!
* */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends TimeStamp implements UserDetails {
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

    // ##############################################

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_member_role",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<MemberRole> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
        //return new HashSet<GrantedAuthority>();
    }

}
