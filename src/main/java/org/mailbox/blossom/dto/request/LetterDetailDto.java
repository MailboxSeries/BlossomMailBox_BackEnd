package org.mailbox.blossom.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LetterDetailDto(
        Integer id,

        String receiverId,

        @Size(min = 1, max = 10, message = "Sender name should be less than 10 characters")
        String sender,
        @Size(min = 1, max = 200, message = "Letter content should be less than 200 characters")
        String content
) {
}
