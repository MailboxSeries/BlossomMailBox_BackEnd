package org.mailbox.blossom.security.handler.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.dto.response.JwtTokenDto;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.security.info.UserPrincipal;
import org.mailbox.blossom.utility.CookieUtil;
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
    private String address;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(userPrincipal.getId(), userPrincipal.getRole());

        userRepository.updateRefreshToken(userPrincipal.getId(), jwtTokenDto.getRefreshToken());

        CookieUtil.addCookie(response, "accessToken", jwtTokenDto.getAccessToken());
        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokenDto.getRefreshToken(), jwtTokenDto.getExpiresInRefreshToken());

        response.sendRedirect(address + "/redirect");
    }
}
