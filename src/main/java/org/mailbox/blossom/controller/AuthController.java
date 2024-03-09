package org.mailbox.blossom.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.annotation.UserId;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.dto.common.ResponseDto;
import org.mailbox.blossom.dto.response.JwtTokenDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.usecase.ReissueJWTUseCase;
import org.mailbox.blossom.usecase.WithdrawalUseCase;
import org.mailbox.blossom.utility.CookieUtil;
import org.mailbox.blossom.utility.HeaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${server.cookie-address}")
    private String cookieDomain;

    private final ReissueJWTUseCase reissueJWTUseCase;
    private final WithdrawalUseCase withdrawalUseCase;

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = CookieUtil.refineCookie(request, Constants.REFRESH_TOKEN)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        JwtTokenDto jwtTokenDto = reissueJWTUseCase.reissueJWT(refreshToken);

        CookieUtil.addCookie(response, cookieDomain, Constants.ACCESS_TOKEN, jwtTokenDto.getAccessToken());
        CookieUtil.addSecureCookie(response, cookieDomain, Constants.REFRESH_TOKEN, jwtTokenDto.getRefreshToken(), jwtTokenDto.getExpiresInRefreshToken());

        return ResponseDto.ok(null);
    }

    @DeleteMapping("/withdrawal")
    public ResponseDto<?> withdrawal(
            @UserId String userId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        withdrawalUseCase.withdrawal(userId);
        CookieUtil.deleteCookie(request, response, cookieDomain, Constants.ACCESS_TOKEN);
        CookieUtil.deleteCookie(request, response, cookieDomain, Constants.REFRESH_TOKEN);
        CookieUtil.deleteCookie(request, response, null, "JSESSIONID");

        return ResponseDto.ok(null);
    }
}
