package com.devlogmoa.config.auth.dto;

import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.member.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private final String name;
    private final String email;
    private final Role role;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
    }
}