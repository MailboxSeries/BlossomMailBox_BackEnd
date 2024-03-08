package org.mailbox.blossom.dto.request;

import lombok.Builder;

@Builder
public record SkinStatusInfoDto(
        String type,
        Integer index
) {
}
