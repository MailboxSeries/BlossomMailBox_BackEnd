package org.mailbox.blossom.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

@Getter
public class EncodedUserIDDto extends SelfValidating<EncodedUserIDDto> {
    private final String myId;

    @Builder
    public EncodedUserIDDto(String myId) {
        this.myId = myId;
        validateSelf();
    }
}
