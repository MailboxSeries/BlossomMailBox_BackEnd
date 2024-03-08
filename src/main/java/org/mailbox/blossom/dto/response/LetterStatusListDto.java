package org.mailbox.blossom.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

import java.util.List;

@Getter
public class LetterStatusListDto extends SelfValidating<LetterStatusListDto> {

    private final List<LetterStatus> cherryBlossomStatus;

    @Builder
    public LetterStatusListDto(List<LetterStatus> cherryBlossomStatus) {
        this.cherryBlossomStatus = cherryBlossomStatus;
        validateSelf();
    }

    public static LetterStatusListDto of(List<LetterStatus> cherryBlossomStatus) {
        return LetterStatusListDto.builder()
                .cherryBlossomStatus(cherryBlossomStatus)
                .build();
    }

    @Getter
    public static class LetterStatus extends SelfValidating<LetterStatus>{
        private final String status;

        @Builder
        public LetterStatus(String status) {
            this.status = status;
            validateSelf();
        }

        public static LetterStatus of(String status) {
            return LetterStatus.builder()
                    .status(status)
                    .build();
        }
    }
}


