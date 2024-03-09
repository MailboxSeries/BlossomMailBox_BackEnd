package org.mailbox.blossom.security.handler.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.dto.response.JwtTokenDto;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.security.info.UserPrincipal;
import org.mailbox.blossom.utility.CookieUtil;
import org.mailbox.blossom.utility.CryptUtil;
import org.mailbox.blossom.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${server.client-address}")
    private String serverAddress;

    @Value("${server.cookie-address}")
    private String cookieDomain;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final CryptUtil cryptUtil;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());

        userRepository.updateRefreshToken(userPrincipal.getId(), jwtTokenDto.getRefreshToken());

        CookieUtil.addCookie(response, cookieDomain, Constants.ACCESS_TOKEN, jwtTokenDto.getAccessToken());
        CookieUtil.addSecureCookie(response, cookieDomain, Constants.REFRESH_TOKEN, jwtTokenDto.getRefreshToken(), jwtTokenDto.getExpiresInRefreshToken());

        String encodedUserId;

        try {
            encodedUserId = cryptUtil.encrypt(userPrincipal.getId().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(serverAddress + "/home?u=" + encodedUserId);
    }
}
