package org.mailbox.blossom.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.mailbox.blossom.dto.common.SelfValidating;

@Getter
public class AttendanceStatusDto extends SelfValidating<AttendanceStatusDto> {

    @NotNull(message = "attendanceCompleted must not be null")
    private final Boolean attendanceCompleted;

    @Builder
    public AttendanceStatusDto(Boolean attendanceCompleted) {
        this.attendanceCompleted = attendanceCompleted;
        validateSelf();
    }

    public static AttendanceStatusDto of(Boolean attendanceCompleted) {
        return AttendanceStatusDto.builder()
                .attendanceCompleted(attendanceCompleted)
                .build();
    }
}
