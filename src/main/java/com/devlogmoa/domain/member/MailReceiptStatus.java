package com.devlogmoa.domain.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MailReceiptStatus {

    USE("USE"),
    UNUSED("UNUSED");

    private final String key;

    public static MailReceiptStatus createMailReceiptStatus(String mailReceiptStatus) {
        if (USE.key.equals(mailReceiptStatus)) {
            return MailReceiptStatus.USE;
        }

        return MailReceiptStatus.UNUSED;
    }
}
