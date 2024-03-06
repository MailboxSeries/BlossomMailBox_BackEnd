package org.mailbox.blossom.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EProvider {

    KAKAO("kakao"),
    GOOGLE("google"),
    NAVER("naver"),

    ;

    private final String provider;

    public static EProvider fromName(String provider) {
        return EProvider.valueOf(provider.toUpperCase());
    }
}
