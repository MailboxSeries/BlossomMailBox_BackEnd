package org.mailbox.blossom.service;

import lombok.RequiredArgsConstructor;
import org.mailbox.blossom.domain.User;
import org.mailbox.blossom.dto.response.LetterStatusListDto;
import org.mailbox.blossom.dto.type.ErrorCode;
import org.mailbox.blossom.exception.CommonException;
import org.mailbox.blossom.repository.LetterRepository;
import org.mailbox.blossom.repository.UserRepository;
import org.mailbox.blossom.usecase.ReadLetterUseCase;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mailbox.blossom.constant.Constants.*;

@Service
@RequiredArgsConstructor
public class LetterService implements ReadLetterUseCase {
    private final UserRepository userRepository;
    private final LetterRepository letterRepository;

    @Override
    public LetterStatusListDto readLetters(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        LocalDate userCreatedAt = user.getCreatedAt();
        LocalDate today = LocalDate.now();

        List<LetterStatusListDto.LetterStatus> statusList = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            LocalDate currentDate = START_DATE.plusDays(i);
            String status;
            if (currentDate.isBefore(userCreatedAt)) {
                status = LETTER_DISABLE_STATUS;
            } else if ((currentDate.isAfter(userCreatedAt) || currentDate.equals(userCreatedAt)) && (currentDate.isBefore(today) || currentDate.equals(today))) {
                status = LETTER_ACTIVE_STATUS;
            } else if (currentDate.isAfter(today)) {
                status = LETTER_INACTIVE_STATUS;
            } else {
                status = LETTER_INACTIVE_STATUS;
            }
            statusList.add(LetterStatusListDto.LetterStatus.of(status));
        }

        return LetterStatusListDto.of(statusList);
    }
}
