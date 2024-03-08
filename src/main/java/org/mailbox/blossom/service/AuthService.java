package org.mailbox.blossom.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.constant.Constants;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.JwtTokenDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.ReissueJWTUseCase;
import org.mailbox.blossom.usecase.WithdrawalUseCase;
import org.mailbox.blossom.utility.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements ReissueJWTUseCase, WithdrawalUseCase {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public JwtTokenDto reissueJWT(String refreshToken) {
        String userId;

        try {
            Claims claims = jwtUtil.validateToken(refreshToken);
            userId = claims.get(Constants.USER_ID_CLAIM_NAME, String.class);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.INVALID_TOKEN_ERROR);
        }

        User user = userRepository.findByIdAndRefreshToken(UUID.fromString(userId), refreshToken)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getRole());
        userRepository.updateRefreshToken(user.getId(), jwtTokenDto.getRefreshToken());

        return jwtTokenDto;
    }

    @Override
    @Transactional
    public void withdrawal(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        userRepository.delete(user);
    }
}
