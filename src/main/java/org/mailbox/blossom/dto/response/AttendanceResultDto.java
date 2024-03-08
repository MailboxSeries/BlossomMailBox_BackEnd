package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

@Getter
public class AttendanceResultDto extends SelfValidating<AttendanceResultDto> {

    @NotNull(message = "getCat must not be null")
    private final Boolean getCat;
    private final Integer catID;

    @Builder
    public AttendanceResultDto(Boolean getCat, Integer catID) {
        this.getCat = getCat;
        this.catID = catID;
        validateSelf();
    }

    public static AttendanceResultDto of(Boolean getCat, Integer catID) {
        return AttendanceResultDto.builder()
                .getCat(getCat)
                .catID(catID)
                .build();
    }
}
