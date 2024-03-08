package org.mailbox.blossom.dto.request;

import lombok.Builder;

@Builder
public record SkinInfoDto(
        String sex,
        Integer top,
        Integer bottom,
        Integer hair,
        Integer face,
        Integer animal,
        Integer rightStore,
        Integer leftStore

) {
}
