package org.mailbox.blossom.security.info.factory;

import org.mailbox.blossom.dto.type.EProvider;
import org.mailbox.blossom.security.info.GoogleOAuth2UserInfo;
import org.mailbox.blossom.security.info.KakaoOAuth2UserInfo;
import org.mailbox.blossom.security.info.NaverOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(EProvider provider, Map<String, Object> attributes) {
        switch (provider) {
            case GOOGLE:
                return new GoogleOAuth2UserInfo(attributes);
            case KAKAO:
                return new KakaoOAuth2UserInfo(attributes);
            case NAVER:
                return new NaverOAuth2UserInfo(attributes);
            default:
                throw new IllegalArgumentException("Unsupported provider: " + provider);
        }
    }
}
