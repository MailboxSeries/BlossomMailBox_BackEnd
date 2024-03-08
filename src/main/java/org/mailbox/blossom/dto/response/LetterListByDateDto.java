package org.mailbox.blossom.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

import java.util.List;
@Getter
public class LetterListByDateDto extends SelfValidating<LetterListByDateDto> {

    private final List<LetterByDate> letters;

    @Builder
    public LetterListByDateDto(List<LetterByDate> letters) {
        this.letters = letters;
        validateSelf();
    }

    public static LetterListByDateDto of(List<LetterByDate> letters) {
        return LetterListByDateDto.builder()
                .letters(letters)
                .build();
    }

    @Getter
    public static class LetterByDate extends SelfValidating<LetterByDate>{
        private final int id;
        private final Boolean reply;
        private final String sender;

        @Builder
        public LetterByDate(int id, Boolean reply, String sender) {
            this.id = id;
            this.reply = reply;
            this.sender = sender;

            validateSelf();
        }

        public static LetterByDate of(int id, Boolean reply, String sender) {
            return LetterByDate.builder()
                    .id(id)
                    .reply(reply)
                    .sender(sender)
                    .build();
        }
    }

}