package org.mailbox.blossom.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.AttendanceStatusDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.ItemRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.CheckAttendanceUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AttendanceService implements CheckAttendanceUseCase {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public AttendanceStatusDto checkAttendance(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        return AttendanceStatusDto.of(itemRepository.existsByUserIdAndCreatedAtDate(user.getId().toString(), LocalDate.now()));
    }
}
