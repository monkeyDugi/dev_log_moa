package com.devlogmoa.repository.member;

import com.devlogmoa.domain.member.MailReceiptStatus;
import com.devlogmoa.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    List<Member> findByMailReceiptStatus(MailReceiptStatus mailReceiptStatus);
}
