package org.mailbox.blossom.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EStatus {
    DISABLED("disable"),
    ACTIVE("active"),
    INACTIVE("inactive"),

    ;

    private final String status;

    public static EStatus fromName(String status) {
        return EStatus.valueOf(status.toUpperCase());
    }

}
