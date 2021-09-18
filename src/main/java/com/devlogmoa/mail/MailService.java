package com.devlogmoa.mail;

import com.devlogmoa.domain.member.MailReceiptStatus;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final MemberRepository memberRepository;

    public void sendEmail() {
        List<Member> findMembers = memberRepository.findAll();
        SimpleMailMessage message = new SimpleMailMessage();

        for (Member findMember : findMembers) {
            sendEmail(message, findMember);
        }
    }

    private void sendEmail(SimpleMailMessage message, Member findMember) {
        if (findMember.getMailReceiptStatus() == MailReceiptStatus.Y) {
            message.setTo(findMember.getEmail());
            message.setSubject("devlogmoa");
            message.setText("devlogmoa에서 새로운 소식이 올라왔어요!! 확인하세요!! http://www.devlogmoa.shop");

            mailSender.send(message);
        }
    }
}
