package com.devlogmoa.domain.blog;

public enum UsageStatus {

    USE,
    UNUSED;

    public static boolean isUse(UsageStatus usageStatus) {
        return USE == usageStatus;
    }
}
