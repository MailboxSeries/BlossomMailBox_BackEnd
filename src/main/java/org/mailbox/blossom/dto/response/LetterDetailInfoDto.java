package org.mailbox.blossom.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

@Getter
public class LetterDetailInfoDto extends SelfValidating<LetterDetailInfoDto> {

    private final SendLetter sendLetter;
    private final ReplyLetter replyLetter;

    @Builder
    public LetterDetailInfoDto(SendLetter sendLetter, ReplyLetter replyLetter) {
        this.sendLetter = sendLetter;
        this.replyLetter = replyLetter;
        validateSelf();
    }

    @Getter
    public static class SendLetter extends SelfValidating<SendLetter> {
        private final String image;
        private final String content;

        @Builder
        public SendLetter(String image, String content) {
            this.image = image;
            this.content = content;
            validateSelf();

        }

        public static SendLetter of(String image, String content) {
            return SendLetter.builder()
                    .image(image)
                    .content(content)
                    .build();
        }
    }

    @Getter
    public static class ReplyLetter extends SelfValidating<ReplyLetter> {
        private final String image;
        private final String content;
        private final String nickname;

        @Builder
        public ReplyLetter(String image, String content, String nickname) {
            this.image = image;
            this.content = content;
            this.nickname = nickname;
            validateSelf();
        }

        public static ReplyLetter of(String image, String content, String nickname) {
            return ReplyLetter.builder()
                    .image(image)
                    .content(content)
                    .nickname(nickname)
                    .build();
        }
    }

    public static LetterDetailInfoDto of(SendLetter sendLetter, ReplyLetter replyLetter) {
        return LetterDetailInfoDto.builder()
                .sendLetter(sendLetter)
                .replyLetter(replyLetter)
                .build();
    }
}
