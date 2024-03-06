package org.mailbox.blossom.dto.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EGender {

    MAN("man"),
    WOMAN("woman"),

    ;

    private final String name;


}
