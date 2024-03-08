package org.mailbox.blossom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.dto.response.JwtTokenDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.usecase.ReissueJWTUseCase;
import org.mailbox.blossom.utility.CookieUtil;
import org.mailbox.blossom.utility.HeaderUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final ReissueJWTUseCase reissueJWTUseCase;

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_AUTHORIZATION_HEADER));

        JwtTokenDto jwtTokenDto = reissueJWTUseCase.reissueJWT(refreshToken);

        CookieUtil.addCookie(response, "accessToken", jwtTokenDto.getAccessToken());
        CookieUtil.addSecureCookie(response, "refreshToken", jwtTokenDto.getRefreshToken(), jwtTokenDto.getExpiresInRefreshToken());

        return ResponseDto.ok(null);
    }
}
