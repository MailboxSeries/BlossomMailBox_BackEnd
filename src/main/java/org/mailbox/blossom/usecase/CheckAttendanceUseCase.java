package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.AttendanceStatusDto;

@UseCase
public interface CheckAttendanceUseCase {
    AttendanceStatusDto checkAttendance(String userId);
}
