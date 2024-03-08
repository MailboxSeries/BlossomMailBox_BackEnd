package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.JwtTokenDto;

@UseCase
public interface ReissueJWTUseCase {
    JwtTokenDto reissueJWT(String refreshToken);
}
