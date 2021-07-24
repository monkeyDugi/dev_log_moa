package com.devlogmoa.domain.blog;

/**
 * 메일링을 위해 신규컨텐츠가 있는지 없는지 체크하기 위한 클래스
 */
public enum ContentsStatus {

    NEW(true, "신규 컨텐츠 있음"),
    DEFAULT(false, "신규 컨텐츠 없음, 클래스 설명용");

    ContentsStatus(boolean status, String description) {
    }
}
