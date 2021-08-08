package com.devlogmoa.config.auth.dto;

import com.devlogmoa.domain.member.MailReceiptStatus;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.member.Role;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private static final long serialVersionUID = -8498973270536986012L;

    private final String name;
    private final String email;
    private final Role role;
    private MailReceiptStatus mailReceiptStatus;

    public SessionMember(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.role = member.getRole();
        this.mailReceiptStatus = member.getMailReceiptStatus();
    }

    public void updateMailReceiptStatus(Member findMember) {
        this.mailReceiptStatus = findMember.getMailReceiptStatus();
    }
}