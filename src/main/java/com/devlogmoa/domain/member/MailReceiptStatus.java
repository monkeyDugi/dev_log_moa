package com.devlogmoa.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailReceiptStatus {

    Y("Y", "메일 수신"),
    N("N", "메일 미수신");

    private final String key;
    private final String value;

    public static MailReceiptStatus  createMailReceiptStatus(String mailReceiptStatus) {
        if ("Y".equals(mailReceiptStatus)) {
            return MailReceiptStatus.Y;
        }

        return MailReceiptStatus.N;
    }
}
