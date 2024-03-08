package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

@Getter
public class JwtTokenDto extends SelfValidating<JwtTokenDto> {
    @NotNull(message = "accessToken must not be null")
    private final String accessToken;

    @NotNull(message = "refreshToken must not be null")
    private final String refreshToken;

    @NotNull(message = "expiresInAccessToken must not be null")
    private final Integer expiresInRefreshToken;

    @Builder
    public JwtTokenDto(String accessToken, String refreshToken, Integer expiresInRefreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInRefreshToken = expiresInRefreshToken;
        validateSelf();
    }
}
