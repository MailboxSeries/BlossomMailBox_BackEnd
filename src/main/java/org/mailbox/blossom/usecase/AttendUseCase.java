package org.mailbox.blossom.usecase;

import org.mailbox.blossom.annotation.UseCase;
import org.mailbox.blossom.dto.response.AttendanceResultDto;

@UseCase
public interface AttendUseCase {

    AttendanceResultDto attend(String userId);
}
