package com.devlogmoa.config.auth.dto;

import com.devlogmoa.domain.member.MailReceiptStatus;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 함.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
//        if ("naver".equals(registrationId)) {
//            return ofNaver("id", attributes);
//        }

        return ofGoogle(userNameAttributeName, attributes);
    }
//
//    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
//        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//        return OAuthAttributes.builder()
//                .name((String) response.get("name"))
//                .email((String) response.get("email"))
//                .attributes(response)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할 때 인데
    // 가입 시 권한을 USER로 주기 위해서 role 빌더값에 USER 사용
    // 가입 시 메일 수신으로 셋팅
    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .mailReceiptStatus(MailReceiptStatus.Y)
                .build();
    }
}