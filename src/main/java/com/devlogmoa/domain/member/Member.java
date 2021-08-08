package com.devlogmoa.domain.member;

import com.devlogmoa.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailReceiptStatus mailReceiptStatus;


    @Builder
    public Member(String name, String email, Role role, MailReceiptStatus mailReceiptStatus) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.mailReceiptStatus = mailReceiptStatus;
    }

    public Member update(String name) {
        this.name = name;

        return this;
    }

    public void updateMailReceiptStatus(String mailReceiptStatus) {
        this.mailReceiptStatus = MailReceiptStatus.createMailReceiptStatus(mailReceiptStatus);
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
