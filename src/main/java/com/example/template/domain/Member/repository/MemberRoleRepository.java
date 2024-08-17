package com.example.template.domain.Member.repository;

import com.example.template.domain.Member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    Optional<MemberRole> findByAuthority(String authority);
}
